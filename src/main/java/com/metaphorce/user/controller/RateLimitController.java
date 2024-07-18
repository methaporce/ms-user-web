package com.metaphorce.user.controller;

import com.metaphorce.databaseLib.dto.RateLimitRequest;
import com.metaphorce.databaseLib.dto.RateLimitResponse;
import com.metaphorce.user.service.ExecuteTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ratelimit")
public class RateLimitController {

    @Autowired
    private ExecuteTaskService executeTaskService;

    @PostMapping("users/{userId}/execute-task")
    public String executeTask(@PathVariable Long userId) {

        return executeTaskService.executeTask(userId);
    }

    @PostMapping("admin/rate-limit-information")
    public RateLimitResponse rateLimitInformation(@RequestBody RateLimitRequest request) {

        return executeTaskService.getRateLimitInformation(request.getUserId());
    }

}
