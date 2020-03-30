package com.example.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties.Setter;

@Configuration
@ConfigurationProperties(prefix = "hystrix")
@RestController
public class ThreadPoolConfig {
	
	//HystrixThreadPoolProperties prop;
	
	//thread pool properties
	private int coreSize;
	private int maximumSize;
	private int maxQueueSize;
	private int queueSizeRejectionThreshold;
	private int keepAliveTimeMinutes;
	private boolean allowMaximumSizeToDivergeFromCoreSize;

	
	private int metricsRollingStatisticalWindowInMilliseconds;
	private int metricsRollingStatisticalWindowBuckets;
	
	@Bean
	@ConfigurationProperties(prefix = "hystrix")
	public Setter getThreadPoolProp() {
		return HystrixThreadPoolProperties.Setter()
				.withCoreSize(coreSize)
				.withMaximumSize(maximumSize)
				.withMaxQueueSize(maxQueueSize)
				.withQueueSizeRejectionThreshold(queueSizeRejectionThreshold)
				.withKeepAliveTimeMinutes(keepAliveTimeMinutes)
				.withAllowMaximumSizeToDivergeFromCoreSize(allowMaximumSizeToDivergeFromCoreSize)
				.withMetricsRollingStatisticalWindowInMilliseconds(metricsRollingStatisticalWindowInMilliseconds)
				.withMetricsRollingStatisticalWindowBuckets(metricsRollingStatisticalWindowBuckets)
				;
	}

	public int getCoreSize() {
		return coreSize;
	}

	public void setCoreSize(int coreSize) {
		this.coreSize = coreSize;
	}

	public int getMaximumSize() {
		return maximumSize;
	}

	public void setMaximumSize(int maximumSize) {
		this.maximumSize = maximumSize;
	}

	public int getMaxQueueSize() {
		return maxQueueSize;
	}

	public void setMaxQueueSize(int maxQueueSize) {
		this.maxQueueSize = maxQueueSize;
	}

	public int getQueueSizeRejectionThreshold() {
		return queueSizeRejectionThreshold;
	}

	public void setQueueSizeRejectionThreshold(int queueSizeRejectionThreshold) {
		this.queueSizeRejectionThreshold = queueSizeRejectionThreshold;
	}

	public int getKeepAliveTimeMinutes() {
		return keepAliveTimeMinutes;
	}

	public void setKeepAliveTimeMinutes(int keepAliveTimeMinutes) {
		this.keepAliveTimeMinutes = keepAliveTimeMinutes;
	}

	public boolean isAllowMaximumSizeToDivergeFromCoreSize() {
		return allowMaximumSizeToDivergeFromCoreSize;
	}

	public void setAllowMaximumSizeToDivergeFromCoreSize(boolean allowMaximumSizeToDivergeFromCoreSize) {
		this.allowMaximumSizeToDivergeFromCoreSize = allowMaximumSizeToDivergeFromCoreSize;
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

	
}
