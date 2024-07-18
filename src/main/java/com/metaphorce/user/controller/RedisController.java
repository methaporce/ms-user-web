package com.metaphorce.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.metaphorce.user.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @PostMapping("/save")
    public ResponseEntity<String> saveToRedis(@RequestParam String key, @RequestBody Object value) throws JsonProcessingException, JsonProcessingException {
        redisService.saveToRedis(key, value);
        return ResponseEntity.ok("Saved to Redis");
    }

    @GetMapping("/get")
    public ResponseEntity<Object> getFromRedis(@RequestParam String key) throws IOException, IOException {
        Object value = redisService.getFromRedis(key);
        if (value != null) {
            return ResponseEntity.ok(value);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFromRedis(@RequestParam String key) {
        redisService.deleteFromRedis(key);
        return ResponseEntity.ok("Deleted from Redis");
    }
}