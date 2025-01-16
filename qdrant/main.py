import os
from dotenv import load_dotenv
from contextlib import asynccontextmanager
import asyncpg
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from qdrant_client.http.models import Distance, VectorParams
from qdrant_client.http import models as rest
from qdrant_client import QdrantClient
from transformers import AutoTokenizer, AutoModel
import torch
import torch.nn.functional as F


load_dotenv()

@asynccontextmanager
async def lifespan(app: FastAPI):
    global db_pool
    db_pool = await asyncpg.create_pool(
        host=DB_HOST,
        port=DB_PORT,
        database=DB_NAME,
        user=DB_USER,
        password=DB_PASS
    )
    create_qdrant_collection()
    await upload_vectors_to_qdrant()
    yield
    await db_pool.close()


# Инициализация FastAPI
app = FastAPI(lifespan=lifespan)


# Настройки базы данных PostgreSQL
DB_HOST = os.getenv("DB_HOST")
DB_PORT = os.getenv("DB_PORT")
DB_NAME = os.getenv("DB_NAME")
DB_USER = os.getenv("DB_USER")
DB_PASS = os.getenv("DB_PASS")

# Настройки Qdrant
QDRANT_HOST = os.getenv("QDRANT_HOST")
QDRANT_PORT = os.getenv("QDRANT_PORT")
COLLECTION_NAME = os.getenv("COLLECTION_NAME")

# Инициализация Qdrant клиента (синхронно)
qdrant_client = QdrantClient(host=QDRANT_HOST, port=QDRANT_PORT)

# Загрузка модели и токенизатора (синхронно, т.к. Hugging Face не поддерживает async)
tokenizer = AutoTokenizer.from_pretrained("intfloat/multilingual-e5-large")
model = AutoModel.from_pretrained("intfloat/multilingual-e5-large")

# Пул подключения к базе данных PostgreSQL
db_pool = None

# Создание коллекции в Qdrant, если её нет
def create_qdrant_collection():
    collections = qdrant_client.get_collections()
    if COLLECTION_NAME not in [collection.name for collection in collections.collections]:
        qdrant_client.create_collection(
            collection_name=COLLECTION_NAME,
            vectors_config=VectorParams(size=1024, distance=Distance.COSINE)
        )

# Модель данных для API-запроса
class SearchRequest(BaseModel):
    query: str

# Функция для создания вектора из текста
def embed_text(text):
    inputs = tokenizer(text, return_tensors='pt', padding=True, truncation=True)
    with torch.no_grad():
        embedding = model(**inputs).last_hidden_state.mean(dim=1)
    normalized_embedding = F.normalize(embedding, p=2, dim=1)
    return normalized_embedding.squeeze(0).tolist()

# Заполнение Qdrant векторами из базы данных
async def upload_vectors_to_qdrant():
    async with db_pool.acquire() as conn:
        print(DB_HOST, DB_NAME)
        services = await conn.fetch("SELECT id, name FROM services")
        for service in services:
            service_id = service["id"]
            keywords = service["name"]
            vector = embed_text(keywords)
            qdrant_client.upsert(
                collection_name=COLLECTION_NAME,
                points=[
                    rest.PointStruct(
                        id=service_id, 
                        vector=vector, 
                        payload={"key_words": keywords}
                    )
                ]
            )

# Эндпоинт для поиска услуги по тексту
@app.post("/search")
async def search_service(request: SearchRequest):
    query = request.query
    query_vector = embed_text(query)  # Создание вектора из запроса

    # Поиск в Qdrant
    search_result = qdrant_client.search(
        collection_name=COLLECTION_NAME,
        query_vector=query_vector,
        score_threshold=0.83,
        limit=4
    )
    
    # Получение найденных ID и их информации из PostgreSQL
    service_ids = [result.id for result in search_result]
    print([result.score for result in search_result])
    async with db_pool.acquire() as conn:
        rows = await conn.fetch(
            "SELECT id, name, description FROM services WHERE id = ANY($1)", 
            service_ids
        )
        
        if not rows:
            raise HTTPException(status_code=404, detail="Services not found")

        # Формируем ответ с результатами
        results = [{"id": row["id"], "name": row["name"], "description": row["description"]} for row in rows]
    
    return {"results": results}
