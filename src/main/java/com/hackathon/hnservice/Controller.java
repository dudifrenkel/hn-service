package com.hackathon.hnservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
public class Controller {

    final long DEFAULT_ITERATIONS = 500;
    final long DEFAULT_SLEEP = 100;
    private final SleepyRestClient sleepyRestClient;


    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public Controller(SleepyRestClient sleepyRestClient) {
        this.sleepyRestClient = sleepyRestClient;
    }

    @GetMapping("/testAndSleep")
    public String test(@RequestParam(required = false) Long iterations, @RequestParam(required = false) Long sleep) throws InterruptedException {

        iterations = iterations == null ? DEFAULT_ITERATIONS : iterations;
        sleep = sleep == null ? DEFAULT_SLEEP : sleep;

        logger.debug(String.format("New request: %s, %d%n", Thread.currentThread(), Thread.currentThread().getId()));
        try {
            InputStream inputStream = new ClassPathResource("file.json").getInputStream();
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

            logger.debug("start processing");
            for (int i = 0; i < iterations; i++) {
                sha256.digest(inputStream.readAllBytes());
            }
            logger.debug("end processing");
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        logger.debug(String.format("start sleep for: %d%n", sleep));
        Thread.sleep(sleep);
        logger.debug("end sleep");

        logger.debug(String.format("End request: %d\n\n", Thread.currentThread().getId()));
        return String.format("Iterations number: %d - done with thread.id %d", iterations, Thread.currentThread().getId());
    }

    @GetMapping("/test")
    public String test(@RequestParam(required = false) Long iterations) {
        iterations = iterations == null ? DEFAULT_ITERATIONS : iterations;

        System.out.printf("New request: %d%n", Thread.currentThread().getId());
        try {
            InputStream is = Files.newInputStream(Paths.get("/Users/dudif/IdeaProjects/hn-service/src/main/resources/file.json"));
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

            for (int i = 0; i < iterations; i++) {
                sha256.digest(is.readAllBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        System.out.println("call sleepy");
        String res = sleepyRestClient.callSleepy();
        System.out.printf("end call sleepy, res:%s%n", res);

        System.out.printf("End request: %d\n\n", Thread.currentThread().getId());
        return String.format("Iterations number: %d - done with thread.id %d", iterations, Thread.currentThread().getId());
    }
}