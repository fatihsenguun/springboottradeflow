package com.fatihsengun.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import io.github.bucket4j.Bucket;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitingService implements HandlerInterceptor {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    private Bucket resolveBucket(String ip) {
        return cache.computeIfAbsent(ip, k -> createNewBucket());
    }

    private Bucket createNewBucket() {
        return Bucket.builder()
                .addLimit(limit -> limit.capacity(20).refillGreedy(20, Duration.ofMinutes(1)))
                .build();
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = request.getRemoteAddr();
        Bucket tokenBucket = resolveBucket(ip);

        if (tokenBucket.tryConsume(1)) {
            return true;
        }
        response.setStatus(429);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        // Write and flush the response
        response.getWriter().write("Too many requests. Please try again in a minute.");
        response.getWriter().flush();
        return false;
    }


}
