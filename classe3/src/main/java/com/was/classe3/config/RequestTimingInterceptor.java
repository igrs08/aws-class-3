package com.was.classe3.config;

import com.was.classe3.model.RequestLog;
import com.was.classe3.repository.RequestLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;
import java.time.ZoneId;

@Component
public class RequestTimingInterceptor
        implements HandlerInterceptor {

    private final RequestLogRepository repository;

    public RequestTimingInterceptor(
            RequestLogRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {

        request.setAttribute(
                "startTime",
                System.currentTimeMillis()
        );

        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) {

        Long startTime =
                (Long) request.getAttribute("startTime");

        Long endTime =
                System.currentTimeMillis();

        RequestLog log = new RequestLog();

        log.setEndpoint(
                request.getRequestURI()
        );

        log.setMethod(
                request.getMethod()
        );

        log.setStartTime(
                Instant.ofEpochMilli(startTime)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
        );

        log.setEndTime(
                Instant.ofEpochMilli(endTime)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
        );

        log.setDurationMs(
                endTime - startTime
        );

        log.setStatusCode(
                response.getStatus()
        );

        repository.save(log);
    }
}