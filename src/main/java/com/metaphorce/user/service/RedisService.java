package com.metaphorce.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import java.time.Duration;
import java.util.Map;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void saveToRedis(String key, Object value) throws JsonProcessingException {
        String jsonValue = objectMapper.writeValueAsString(value);
        redisTemplate.opsForValue().set(key, jsonValue, Duration.ofMinutes(1) );
    }

    public Map<String, Object> getFromRedis(String  key) throws JsonProcessingException {
        String jsonValue = redisTemplate.opsForValue().get(key);
        return objectMapper.readValue(jsonValue, Map.class);
    }


    public void deleteFromRedis(String key) {
        redisTemplate.delete(key);
    }
}