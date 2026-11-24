package com.apigateway.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter implements Filter {

    ConcurrentHashMap<String, Integer> rateLimiterMap = new ConcurrentHashMap<>();


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(request instanceof HttpServletRequest)
        {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String clientIp = httpRequest.getRemoteAddr();
            rateLimiterMap.put(clientIp, rateLimiterMap.getOrDefault(clientIp, 0) + 1);

        }
    }
}