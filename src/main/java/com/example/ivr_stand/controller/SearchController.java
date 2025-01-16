package com.example.ivr_stand.controller;
import com.example.ivr_stand.model.Category;
import com.example.ivr_stand.model.SearchRequest;
import com.example.ivr_stand.model.SearchResponse;
import com.example.ivr_stand.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Controller
@RequestMapping("/api")
public class SearchController {

    @Autowired
    private CategoryRepo categoryRepo;

    private final RestTemplate restTemplate;

    @Value("${fastapi.url}")
    private String fastApiUrl;  // Установите URL вашего FastAPI сервера в application.properties

    public SearchController() {
        this.restTemplate = new RestTemplate();
    }

    @PostMapping("/search")
    public String searchService(@ModelAttribute SearchRequest request, Model model) {
        String url = fastApiUrl + "/search";
        String searchText = request.getQuery();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        SearchRequest requestForApi = new SearchRequest();
        requestForApi.setQuery(searchText);

        HttpEntity<SearchRequest> entity = new HttpEntity<>(requestForApi, headers);

        try {
            // Отправка POST-запроса к FastAPI
            ResponseEntity<SearchResponse> response = restTemplate.postForEntity(url, entity, SearchResponse.class);
            Optional<Category> category = categoryRepo.findById(1);
            model.addAttribute("imgsize", ServiceController.imgsize);
            model.addAttribute("category", category.get());
            model.addAttribute("services", response.getBody().getResults());
            return "services_template";
//            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
          return "error_template";
        }
    }
}