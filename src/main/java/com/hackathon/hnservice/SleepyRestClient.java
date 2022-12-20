package com.hackathon.hnservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SleepyRestClient {
    @Value("${sleepy.url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    public String callSleepy() {
        try{
            return restTemplate.getForObject(url, String.class);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "sleepy is not available", e);
        }
    }
}