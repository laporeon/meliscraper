package com.laporeon.meliscraper.config.ratelimit;

import com.laporeon.meliscraper.dtos.ErrorResponseDTO;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class RateLimitFilter implements Filter {

    private final ObjectMapper objectMapper;
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private static final int MAX_REQUESTS = 30;
    private static final Duration REFILL_DURATION = Duration.ofMinutes(1);

    @Override
    public void doFilter (
            ServletRequest request, ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String clientIp = getClientIp(httpRequest);
        Bucket bucket = buckets.computeIfAbsent(clientIp, k -> createBucket());

        log.info("Rate limit check for IP: {}, tokens available: {}/{}",
                 clientIp,
                 bucket.getAvailableTokens(),
                 MAX_REQUESTS);

        if (!bucket.tryConsume(1)) {
            log.warn("Rate limit EXCEEDED for IP: {}", clientIp);

            writeResponse(httpResponse, 429, "Too many requests. Please try again later.");
            return;
        }

        chain.doFilter(request, response);
    }

    private Bucket createBucket () {
        Bandwidth limit = Bandwidth.classic(MAX_REQUESTS, Refill.intervally(MAX_REQUESTS, REFILL_DURATION));
        return Bucket.builder()
                     .addLimit(limit)
                     .build();
    }

    private String getClientIp (HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");

        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }

        return request.getRemoteAddr();
    }

    private void writeResponse (HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store");

        objectMapper.writeValue(response.getWriter(), new ErrorResponseDTO(status, message, Instant.now()));
    }
}
