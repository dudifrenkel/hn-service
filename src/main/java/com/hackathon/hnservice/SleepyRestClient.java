package com.hackathon.hnservice;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SleepyRestClient {
    private final String url = "http://localhost:8081/sleep";
    private final RestTemplate restTemplate = new RestTemplate();

    public String callSleepy() {
        try{
            return restTemplate.getForObject(url, String.class);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "sleepy is not available", e);
        }
    }
}