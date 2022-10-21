package com.skdlsco.donelib.global.log;


import com.skdlsco.donelib.global.log.aop.annotation.TraceExclude;
import com.skdlsco.donelib.global.log.tracelog.ErrorType;
import com.skdlsco.donelib.global.log.tracelog.TraceLogManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@TraceExclude
@Component
@Slf4j
@RequiredArgsConstructor
public class RequestLogFilter extends OncePerRequestFilter {
    final private TraceLogManager traceLogManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Exception exception = null;
        try {
            String requestInfo = buildRequestInfo(request);
            log.info(requestInfo);
            traceLogManager.begin(requestInfo);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {

            setErrorTypeByResponseStatus(response, exception);

            if (exception == null)
                traceLogManager.end(buildResponseInfo(request, response, null, traceLogManager.getResponseTime()));
            else {
                response.getStatus();
                traceLogManager.exception(buildResponseInfo(request, response, exception, traceLogManager.getResponseTime()), exception);
            }
        }
    }

    private void setErrorTypeByResponseStatus(HttpServletResponse response, Exception e) {
        if (response.getStatus() >= 400 && response.getStatus() <= 499)
            traceLogManager.setErrorType(ErrorType.USER);
        else if (response.getStatus() >= 500 && response.getStatus() <= 599)
            traceLogManager.setErrorType(ErrorType.APP);
        else
            traceLogManager.setErrorType(ErrorType.NONE);
    }

    private static String buildResponseInfo(HttpServletRequest request, HttpServletResponse response, Exception exception, long responseTime) {
        String host = request.getRemoteHost();
        String method = request.getMethod();
        String requestUri = buildRequestUrl(request);

        return "[RES] host=" + host
                + ", method=" + method
                + ", url=" + requestUri
                + ", status=" + response.getStatus()
                + ", time=" + responseTime + "ms"
                + ", ex=" + exception;
    }

    private static String buildRequestInfo(HttpServletRequest request) {
        String host = request.getRemoteHost();
        String method = request.getMethod();
        String requestUri = buildRequestUrl(request);

        return "[REQ] host=" + host + ", method=" + method + ", url=" + requestUri;
    }

    private static String buildRequestUrl(HttpServletRequest request) {
        String requestUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString();
        String query = request.getQueryString();
        String queryString = "";
        if (query != null)
            queryString = "?" + query;
        return requestUrl + queryString;
    }
}
