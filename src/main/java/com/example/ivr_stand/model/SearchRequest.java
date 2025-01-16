package com.example.ivr_stand.model;

public class SearchRequest {
    private String query;

    public SearchRequest(String query) {
        this.query = query;
    }

    public SearchRequest() {
    }

    // Геттер и сеттер
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}