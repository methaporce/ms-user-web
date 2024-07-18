package com.metaphorce.user.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.metaphorce.databaseLib.dto.RateLimitRequest;
import com.metaphorce.databaseLib.dto.RateLimitResponse;
import com.metaphorce.databaseLib.dto.UserDto;
import com.metaphorce.user.service.RateLimiterService;
import com.metaphorce.user.service.RedisService;
import com.metaphorce.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratelimit")
public class RateLimitController {

    @Autowired
    private UserService userService;

    @Autowired
    private RateLimiterService rateLimiterService;

    @PostMapping("users/{userId}/execute-task")
    public ResponseEntity<Object> executeTask(@PathVariable Long userId) {

        UserDto user = userService.getUserById(userId);

        if (user == null) {
            return ResponseEntity.notFound().build() ;
        }

        if (!rateLimiterService.isAllowed(user.getRole(), userId)) {
            return ResponseEntity.status(429).build();
        }

        int count = rateLimiterService.getCurrentCount(user.getRole(), userId);

        String output = "Task number: " + count
                + " executed with role : " + user.getRole()
                + " for user name : " + user.getName() + " " + user.getLastname();

        return ResponseEntity.ok(output);
    }

    @PostMapping("admin/rate-limit-information")
    public RateLimitResponse rateLimitInformation(@RequestBody RateLimitRequest request) {

        UserDto user = userService.getUserById(request.getUserId());

        int numberOfRequests = rateLimiterService.getCurrentCount(user.getRole(), request.getUserId());

        return new RateLimitResponse(numberOfRequests);

    }

}
