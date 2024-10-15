# Используйте официальный образ OpenJDK
FROM openjdk:17-jdk-slim


# Установите рабочий каталог
WORKDIR /app

# Скопируйте jar файл вашего приложения в контейнер
COPY target/IVR_stand-0.0.1-SNAPSHOT.jar app.jar

# Запустите приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
