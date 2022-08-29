package com.skdlsco.donelib.global.log;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class RequestLogFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.info("addr={} method={} request-uri={}", request.getRemoteAddr(), request.getMethod(), ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString());
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        }
    }
}
