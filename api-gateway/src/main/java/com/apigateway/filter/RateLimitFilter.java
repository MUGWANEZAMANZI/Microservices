package com.apigateway.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Order(2)
public class RateLimitFilter implements Filter {

    private static final int MAX_REQUESTS = 5;
    private static final long WINDOW_MS = 60_000; // 1 minute

    // Key: client IP → [request count, window start timestamp]
    private final ConcurrentHashMap<String, long[]> requestCounts = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Only rate-limit POST requests to order endpoints
        String path = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();
        boolean isOrderPlacement = "POST".equalsIgnoreCase(method) && path.startsWith("/api/orders");

        if (!isOrderPlacement) {
            chain.doFilter(request, response);
            return;
        }

        String clientIp = httpRequest.getRemoteAddr();
        long now = Instant.now().toEpochMilli();

        // Reset window if expired, otherwise increment
        requestCounts.compute(clientIp, (ip, entry) -> {
            if (entry == null || now - entry[1] > WINDOW_MS) {
                return new long[]{1, now};
            }
            entry[0]++;
            return entry;
        });

        long[] entry = requestCounts.get(clientIp);

        if (entry[0] > MAX_REQUESTS) {
            httpResponse.setStatus(429);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write(
                    "{\"error\":\"Too Many Requests\",\"message\":\"Order placement limit is "
                            + MAX_REQUESTS + " requests per minute\"}"
            );
            return;
        }

        chain.doFilter(request, response);
    }
}
