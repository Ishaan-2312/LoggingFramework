package com.IshaanBansal.LoggingFramework.metrics;


import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "service_metrics")
public class ServiceMetricsDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_name", nullable = false)
    private String serviceName;

    @Column(name = "window_start", nullable = false)
    private LocalDateTime windowStart;

    @Column(name = "window_end", nullable = false)
    private LocalDateTime windowEnd;

    @Column(name = "total_logs", nullable = false)
    private Integer totalLogs;

    @Column(name = "error_count", nullable = false)
    private Integer errorCount;

    @Column(name = "warn_count", nullable = false)
    private Integer warnCount;

    @Column(name = "avg_latency_ms")
    private Double avgLatencyMs;

    @Column(name = "p95_latency_ms")
    private Double p95LatencyMs;

    @Column(name = "max_latency_ms")
    private Double maxLatencyMs;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "ServiceMetricsDO{" +
                "avgLatencyMs=" + avgLatencyMs +
//                ", id=" + id +
                ", serviceName='" + serviceName + '\'' +
                ", windowStart=" + windowStart +
                ", windowEnd=" + windowEnd +
                ", totalLogs=" + totalLogs +
                ", errorCount=" + errorCount +
                ", warnCount=" + warnCount +
                ", p95LatencyMs=" + p95LatencyMs +
                ", maxLatencyMs=" + maxLatencyMs +
                ", createdAt=" + createdAt +
                '}';
    }

    public Double getAvgLatencyMs() {
        return avgLatencyMs;
    }

    public void setAvgLatencyMs(Double avgLatencyMs) {
        this.avgLatencyMs = avgLatencyMs;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMaxLatencyMs() {
        return maxLatencyMs;
    }

    public void setMaxLatencyMs(Double maxLatencyMs) {
        this.maxLatencyMs = maxLatencyMs;
    }

    public Double getP95LatencyMs() {
        return p95LatencyMs;
    }

    public void setP95LatencyMs(Double p95LatencyMs) {
        this.p95LatencyMs = p95LatencyMs;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getTotalLogs() {
        return totalLogs;
    }

    public void setTotalLogs(Integer totalLogs) {
        this.totalLogs = totalLogs;
    }

    public Integer getWarnCount() {
        return warnCount;
    }

    public void setWarnCount(Integer warnCount) {
        this.warnCount = warnCount;
    }

    public LocalDateTime getWindowEnd() {
        return windowEnd;
    }

    public void setWindowEnd(LocalDateTime windowEnd) {
        this.windowEnd = windowEnd;
    }

    public LocalDateTime getWindowStart() {
        return windowStart;
    }

    public void setWindowStart(LocalDateTime windowStart) {
        this.windowStart = windowStart;
    }
}
