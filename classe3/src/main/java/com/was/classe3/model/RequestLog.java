//package com.was.classe3.model;
//
//import jakarta.persistence.*;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "request_log")
//public class RequestLog {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String endpoint;
//
//    private String method;
//
//    private LocalDateTime startTime;
//
//    private LocalDateTime endTime;
//
//    private Long durationMs;
//
//    private Integer statusCode;
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getEndpoint() {
//        return endpoint;
//    }
//
//    public String getMethod() {
//        return method;
//    }
//
//    public LocalDateTime getStartTime() {
//        return startTime;
//    }
//
//    public LocalDateTime getEndTime() {
//        return endTime;
//    }
//
//    public Long getDurationMs() {
//        return durationMs;
//    }
//
//    public Integer getStatusCode() {
//        return statusCode;
//    }
//
//    public void setEndpoint(String endpoint) {
//        this.endpoint = endpoint;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setMethod(String method) {
//        this.method = method;
//    }
//
//    public void setStartTime(LocalDateTime startTime) {
//        this.startTime = startTime;
//    }
//
//    public void setEndTime(LocalDateTime endTime) {
//        this.endTime = endTime;
//    }
//
//    public void setDurationMs(Long durationMs) {
//        this.durationMs = durationMs;
//    }
//
//    public void setStatusCode(Integer statusCode) {
//        this.statusCode = statusCode;
//    }
//}