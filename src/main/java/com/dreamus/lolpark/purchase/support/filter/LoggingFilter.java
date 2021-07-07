package com.dreamus.lolpark.purchase.support.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper cachingResponseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper);

        String method = request.getMethod();
        Map<String, String> headers = headers(request);

        log.info("REQUEST ===> URI : {}, HEADERS : {}, METHOD : {}, BODY : {}  |||  RESPONSE ===> STATUS : {}, BODY : {}", request.getRequestURI(),
            headers,
            method,
            new String(cachingRequestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8),
            cachingResponseWrapper.getStatus(),
            new String(cachingResponseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8)
        );

        cachingResponseWrapper.copyBodyToResponse();
    }

    private Map<String, String> headers(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();

        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            headers.put(key, request.getHeader(key));
        }

        return headers;
    }
}
