package com.was.classe3.model;

public class RequestMetric {

    private String requestId;

    private String city;

    private Long startTime;

    private Long endTime;

    private Long durationMs;

    public Long getStartTime() {
        return startTime;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getCity() {
        return city;
    }

    public Long getEndTime() {
        return endTime;
    }

    public Long getDurationMs() {
        return durationMs;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public void setDurationMs(Long durationMs) {
        this.durationMs = durationMs;
    }
}