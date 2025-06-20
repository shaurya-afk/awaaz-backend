package com.project.whistleblower.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TinyUrlService {
    private static final String API = "https://tinyurl.com/api-create.php?url=";

    private final RestTemplate restTemplate;

    public TinyUrlService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getTinyUrl(String originalUrl){
        try {
            return restTemplate.getForObject(API + originalUrl, String.class);
        } catch (Exception e) {
            throw new RuntimeException("URL Shortening Failed: " + e.getMessage());
        }
    }
}
