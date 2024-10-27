package com.example.ivr_stand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class SearchController {

    private final RestTemplate restTemplate;

    @Value("${fastapi.url}")
    private String fastApiUrl;  // Установите URL вашего FastAPI сервера в application.properties

    public SearchController() {
        this.restTemplate = new RestTemplate();
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchService(@RequestBody SearchRequest request) {
        String url = fastApiUrl + "/search";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SearchRequest> entity = new HttpEntity<>(request, headers);

        try {
            // Отправка POST-запроса к FastAPI
            ResponseEntity<SearchResponse> response = restTemplate.postForEntity(url, entity, SearchResponse.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ошибка при отправке запроса: " + e.getMessage());
        }
    }
}