package com.example.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;


@Configuration
@ConfigurationProperties(prefix = "hystrix")
@RestController
@Component
public class CommandConfig {
	
	@RequestMapping(value="test")
	public String configTest() {
		return "circuit breaker request volume threshold: " + this.getCommandProp().getCircuitBreakerRequestVolumeThreshold();
	}
//	private String test = "c";
//	
//	@RequestMapping(value="test")
//	public String connection() {
//		return test;
//	}
//
//	public String getTest() {
//		return test;
//	}
//
//	public void setTest(String test) {
//		this.test = test;
//	}
//	
//	HystrixCommandProperties prop;
	
	//command properties
	private ExecutionIsolationStrategy executionIsolationStrategy = ExecutionIsolationStrategy.SEMAPHORE;
	private int executionTimeoutInMilliseconds;
	private boolean executionTimeoutEnabled;
	private boolean executionIsolationThreadInterruptOnTimeout;
	private boolean executionIsolationThreatInterruptOnFutureCancel;
	private int executionIsolationSemaphoreMaxConcurrentRequests;
	
	private int fallbackIsolationSemaphoreMaxConcurrentRequests;
	private boolean fallbackEnabled;
	
	private boolean circuitBreakerEnabled;
	private int circuitBreakerRequestVolumeThreshold;
	private int circuitBreakerSleepWindowInMilliseconds;
	private int circuitBreakerErrorThresholdPercentage;
	private boolean circuitBreakerForceOpen;
	private boolean circuitBreakerForceClosed;
	
	private int metricsRollingStatisticalWindowInMilliseconds;
	private int metricsRollingStatisticalWindowBuckets;
	private boolean metricsRollingPercentileEnabled;
	private int metricsRollingPercentileWindowInMilliseconds;
	private int metricsRollingPercentileWindowBuckets;
	private int metricsRollingPercentileBucketSize;
	private int metricsHealthSnapshotIntervalInMilliseconds;
	
	private boolean requestCacheEnabled;
	private boolean requestLogEnabled;
	
	private int maxRequestsInBatch;
	private int timerDelayInMillisconds;
	private boolean collapserRequestCacheEnabled;

	public static HystrixCommandProperties.Setter hcp = HystrixCommandProperties.Setter()
		.withCircuitBreakerRequestVolumeThreshold(20);

	
	@Bean
	@ConfigurationProperties(prefix = "hystrix")
	public HystrixCommandProperties.Setter getCommandProp() {
		HystrixCommandProperties.Setter hcpSetter = HystrixCommandProperties.Setter()
				.withExecutionIsolationStrategy(executionIsolationStrategy)
				.withExecutionTimeoutInMilliseconds(executionTimeoutInMilliseconds)
				.withExecutionTimeoutEnabled(executionTimeoutEnabled)
				.withExecutionIsolationThreadInterruptOnTimeout(executionIsolationThreadInterruptOnTimeout)
				.withExecutionIsolationThreadInterruptOnFutureCancel(executionIsolationThreatInterruptOnFutureCancel)
				.withExecutionIsolationSemaphoreMaxConcurrentRequests(executionIsolationSemaphoreMaxConcurrentRequests)
				.withFallbackIsolationSemaphoreMaxConcurrentRequests(fallbackIsolationSemaphoreMaxConcurrentRequests)
				.withFallbackEnabled(fallbackEnabled)
				.withCircuitBreakerEnabled(circuitBreakerEnabled)
				.withCircuitBreakerRequestVolumeThreshold(circuitBreakerRequestVolumeThreshold)
				.withCircuitBreakerSleepWindowInMilliseconds(circuitBreakerSleepWindowInMilliseconds)
				.withCircuitBreakerErrorThresholdPercentage(circuitBreakerErrorThresholdPercentage)
				.withCircuitBreakerForceOpen(circuitBreakerForceOpen)
				.withCircuitBreakerForceClosed(circuitBreakerForceClosed)
				.withMetricsRollingStatisticalWindowInMilliseconds(metricsRollingStatisticalWindowInMilliseconds)
				.withMetricsRollingStatisticalWindowBuckets(metricsRollingStatisticalWindowBuckets)
				.withMetricsRollingPercentileEnabled(metricsRollingPercentileEnabled)
				.withMetricsRollingPercentileWindowInMilliseconds(metricsRollingPercentileWindowInMilliseconds)
				.withMetricsRollingPercentileWindowBuckets(metricsRollingPercentileWindowBuckets)
				.withMetricsRollingPercentileBucketSize(metricsRollingPercentileBucketSize)
				.withMetricsHealthSnapshotIntervalInMilliseconds(metricsHealthSnapshotIntervalInMilliseconds)
				.withRequestCacheEnabled(requestCacheEnabled)
				.withRequestLogEnabled(requestLogEnabled)
				;
		return hcpSetter;
	}

	public ExecutionIsolationStrategy getExecutionIsolationStrategy() {
		return executionIsolationStrategy;
	}

	public void setExecutionIsolationStrategy(ExecutionIsolationStrategy executionIsolationStrategy) {
		this.executionIsolationStrategy = executionIsolationStrategy;
	}

	public int getExecutionTimeoutInMilliseconds() {
		return executionTimeoutInMilliseconds;
	}

	public void setExecutionTimeoutInMilliseconds(int executionTimeoutInMilliseconds) {
		this.executionTimeoutInMilliseconds = executionTimeoutInMilliseconds;
	}

	public boolean isExecutionTimeoutEnabled() {
		return executionTimeoutEnabled;
	}

	public void setExecutionTimeoutEnabled(boolean executionTimeoutEnabled) {
		this.executionTimeoutEnabled = executionTimeoutEnabled;
	}

	public boolean isExecutionIsolationThreadInterruptOnTimeout() {
		return executionIsolationThreadInterruptOnTimeout;
	}

	public void setExecutionIsoloationThreadInterruptOnTimeout(boolean executionIsolationThreadInterruptOnTimeout) {
		this.executionIsolationThreadInterruptOnTimeout = executionIsolationThreadInterruptOnTimeout;
	}

	public boolean isExecutionIsolationThreatInterruptOnFutureCancel() {
		return executionIsolationThreatInterruptOnFutureCancel;
	}

	public void setExecutionIsolationThreatInterruptOnFutureCancel(
			boolean executionIsolationThreatInterruptOnFutureCancel) {
		this.executionIsolationThreatInterruptOnFutureCancel = executionIsolationThreatInterruptOnFutureCancel;
	}

	public int getExecutionIsolationSemaphoreMaxConcurrentRequests() {
		return executionIsolationSemaphoreMaxConcurrentRequests;
	}

	public void setExecutionIsolationSemaphoreMaxConcurrentRequests(int executionIsolationSemaphoreMaxConcurrentRequests) {
		this.executionIsolationSemaphoreMaxConcurrentRequests = executionIsolationSemaphoreMaxConcurrentRequests;
	}

	public int getFallbackIsolationSemaphoreMaxConcurrentRequests() {
		return fallbackIsolationSemaphoreMaxConcurrentRequests;
	}

	public void setFallbackIsolationSemaphoreMaxConcurrentRequests(int fallbackIsolationSemaphoreMaxConcurrentRequests) {
		this.fallbackIsolationSemaphoreMaxConcurrentRequests = fallbackIsolationSemaphoreMaxConcurrentRequests;
	}

	public boolean isFallbackEnabled() {
		return fallbackEnabled;
	}

	public void setFallbackEnabled(boolean fallbackEnabled) {
		this.fallbackEnabled = fallbackEnabled;
	}

	public boolean isCircuitBreakerEnabled() {
		return circuitBreakerEnabled;
	}

	public void setCircuitBreakerEnabled(boolean circuitBreakerEnabled) {
		this.circuitBreakerEnabled = circuitBreakerEnabled;
	}

	public int getCircuitBreakerRequestVolumeThreshold() {
		return circuitBreakerRequestVolumeThreshold;
	}

	public void setCircuitBreakerRequestVolumeThreshold(int circuitBreakerRequestVolumeThreshold) {
		this.circuitBreakerRequestVolumeThreshold = circuitBreakerRequestVolumeThreshold;
	}

	public int getCircuitBreakerSleepWindowInMilliseconds() {
		return circuitBreakerSleepWindowInMilliseconds;
	}

	public void setCircuitBreakerSleepWindowInMilliseconds(int circuitBreakerSleepWindowInMilliseconds) {
		this.circuitBreakerSleepWindowInMilliseconds = circuitBreakerSleepWindowInMilliseconds;
	}

	public int getCircuitBreakerErrorThresholdPercentage() {
		return circuitBreakerErrorThresholdPercentage;
	}

	public void setCircuitBreakerErrorThresholdPercentage(int circuitBreakerErrorThresholdPercentage) {
		this.circuitBreakerErrorThresholdPercentage = circuitBreakerErrorThresholdPercentage;
	}

	public boolean isCircuitBreakerForceOpen() {
		return circuitBreakerForceOpen;
	}

	public void setCircuitBreakerForceOpen(boolean circuitBreakerForceOpen) {
		this.circuitBreakerForceOpen = circuitBreakerForceOpen;
	}

	public boolean isCircuitBreakerForceClosed() {
		return circuitBreakerForceClosed;
	}

	public void setCircuitBreakerForceClosed(boolean circuitBreakerForceClosed) {
		this.circuitBreakerForceClosed = circuitBreakerForceClosed;
	}

	public int getMetricsRollingStatisticalWindowInMilliseconds() {
		return metricsRollingStatisticalWindowInMilliseconds;
	}

	public void setMetricsRollingStatisticalWindowInMilliseconds(int metricsRollingStatisticalWindowInMilliseconds) {
		this.metricsRollingStatisticalWindowInMilliseconds = metricsRollingStatisticalWindowInMilliseconds;
	}

	public int getMetricsRollingStatisticalWindowBuckets() {
		return metricsRollingStatisticalWindowBuckets;
	}

	public void setMetricsRollingStatisticalWindowBuckets(int metricsRollingStatisticalWindowBuckets) {
		this.metricsRollingStatisticalWindowBuckets = metricsRollingStatisticalWindowBuckets;
	}

	public boolean isMetricsRollingPercentileEnabled() {
		return metricsRollingPercentileEnabled;
	}

	public void setMetricsRollingPercentileEnabled(boolean metricsRollingPercentileEnabled) {
		this.metricsRollingPercentileEnabled = metricsRollingPercentileEnabled;
	}

	public int getMetricsRollingPercentileWindowInMilliseconds() {
		return metricsRollingPercentileWindowInMilliseconds;
	}

	public void setMetricsRollingPercentileWindowInMilliseconds(int metricsRollingPercentileWindowInMilliseconds) {
		this.metricsRollingPercentileWindowInMilliseconds = metricsRollingPercentileWindowInMilliseconds;
	}

	public int getMetricsRollingPercentileWindowBuckets() {
		return metricsRollingPercentileWindowBuckets;
	}

	public void setMetricsRollingPercentileWindowBuckets(int metricsRollingPercentileWindowBuckets) {
		this.metricsRollingPercentileWindowBuckets = metricsRollingPercentileWindowBuckets;
	}

	public int getMetricsRollingPercentileBucketSize() {
		return metricsRollingPercentileBucketSize;
	}

	public void setMetricsRollingPercentileBucketSize(int metricsRollingPercentileBucketSize) {
		this.metricsRollingPercentileBucketSize = metricsRollingPercentileBucketSize;
	}

	public int getMetricsHealthSnapshotIntervalInMilliseconds() {
		return metricsHealthSnapshotIntervalInMilliseconds;
	}

	public void setMetricsHealthSnapshotIntervalInMilliseconds(int metricsHealthSnapshotIntervalInMilliseconds) {
		this.metricsHealthSnapshotIntervalInMilliseconds = metricsHealthSnapshotIntervalInMilliseconds;
	}

	public boolean isRequestCacheEnabled() {
		return requestCacheEnabled;
	}

	public void setRequestCacheEnabled(boolean requestCacheEnabled) {
		this.requestCacheEnabled = requestCacheEnabled;
	}

	public boolean isRequestLogEnabled() {
		return requestLogEnabled;
	}

	public void setRequestLogEnabled(boolean requestLogEnabled) {
		this.requestLogEnabled = requestLogEnabled;
	}


	
}
