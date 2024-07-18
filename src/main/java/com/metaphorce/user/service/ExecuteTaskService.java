package com.metaphorce.user.service;

import com.metaphorce.databaseLib.dto.RateLimitResponse;
import com.metaphorce.databaseLib.dto.UserDto;
import com.metaphorce.user.exception.BadRequestUserException;
import com.metaphorce.user.exception.RateLimitExceededException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class ExecuteTaskService {

    @Autowired
    private UserService userService;

    @Autowired
    private RateLimiterService rateLimiterService;

    @CircuitBreaker(name = "executeTaskCircuitBreaker", fallbackMethod = "fallbackExecuteTask")
    public String executeTask(Long userId) {

        UserDto user = validateUser(userId);

        if (rateLimiterService.isAllowed(user.getRole(), userId)) {

            return "Task executed successfully for user: " + user.getName() + " " + user.getLastname();
        }

        throw new RateLimitExceededException(this.fallbackExecuteTask(userId, user.getRole(), new Throwable()));
    }

    public String fallbackExecuteTask(Long userId, String role, Throwable t) {
        return "Service is currently unavailable for user " + userId + " with role " + role;
    }

    @CircuitBreaker(name = "rateLimitInformationCircuitBreaker", fallbackMethod = "fallbackUserRateLimitInformation")
    public RateLimitResponse getRateLimitInformation(Long userId) {

        UserDto user = validateUser(userId);

        int numberOfRequests = rateLimiterService.getNumberOfRequests(user.getRole(), user.getId());

        if (numberOfRequests > 0) {

            return new RateLimitResponse(numberOfRequests);
        }

        return new RateLimitResponse(0);
    }

    public String fallbackUserRateLimitInformation() {
        return "Bad request for rate limit information";
    }

    public UserDto validateUser(Long userId) {

        UserDto user = userService.getUserById(userId);

        if (Objects.isNull(user)) {
            throw new BadRequestUserException(this.fallbackUserRateLimitInformation());
        }

        return user;
    }
}
