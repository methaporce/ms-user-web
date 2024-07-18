package com.metaphorce.user.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;

@Service
public class RateLimiterService {

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;


    @CircuitBreaker(name = "executeTaskCircuitBreaker", fallbackMethod = "fallbackExecuteTask")
    public boolean isAllowed(String role, Long userId) {

        String key = "rate_limit:" + userId + ":" + role;
        int limit = getLimit(role);

        Integer currentCount = redisTemplate.opsForValue().get(key);

        if (currentCount == null) {

            redisTemplate.opsForValue().set(key, 1, Duration.ofMinutes(1));

            return true;

        } else if (currentCount < limit) {

            redisTemplate.opsForValue().increment(key);

            return true;

        } else {

            return false;
        }
    }

    public int getNumberOfRequests(String role, Long userId) {
        String key = "rate_limit:" + userId + ":" + role;

        return redisTemplate.opsForValue().get(key) == null ? 0 : redisTemplate.opsForValue().get(key);
    }

    public int getLimit(String role) {
        return role.equalsIgnoreCase("premium") ? 10 : 5;
    }
}