package com.bakery.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String REQUEST_ID = "request_id";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        String requestId = UUID.randomUUID().toString();
        httpServletRequest.setAttribute(REQUEST_ID, requestId);
        logRequest(httpServletRequest, requestId);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void logRequest(HttpServletRequest request, String requestId) {
        if (request != null) {
            StringBuilder data = new StringBuilder();
            data.append("\n").append("LOGGING REQUEST-----------------------------------").append("\n")
                    .append("[REQUEST-ID]: ").append(requestId).append("\n")
                    .append("[PATH]: ").append(request.getRequestURI()).append("\n")
                    .append("[QUERIES]: ").append(request.getQueryString()).append("\n")
                    .append("[HEADERS]: ").append("\n");

            Enumeration headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = (String) headerNames.nextElement();
                String value = request.getHeader(key);
                data.append("--- ").append(key).append(": ").append(value).append("\n");
            }
            data.append("LOGGING REQUEST-----------------------------------\n");

            log.info(data.toString());
        }
    }
}
