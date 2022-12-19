package com.hackathon.hnservice;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SleepyRestClient {
    private String url = "http://localhost:8081/sleep";
    private RestTemplate restTemplate = new RestTemplate();

    public String callSleepy(){
        return restTemplate.getForObject(url, String.class);
    }
}