package com.metaphorce.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void saveToRedis(String key, Object value) throws JsonProcessingException {
        String jsonValue = objectMapper.writeValueAsString(value);
        redisTemplate.opsForValue().set(key, jsonValue);
    }

    public <T> T getFromRedis(String key, Class<T> clazz) throws IOException {
        String jsonValue = redisTemplate.opsForValue().get(key);
        return jsonValue != null ? objectMapper.readValue(jsonValue, clazz) : null;
    }

    public void deleteFromRedis(String key) {
        redisTemplate.delete(key);
    }
}