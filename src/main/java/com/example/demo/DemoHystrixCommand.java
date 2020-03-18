package com.example.demo;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class DemoHystrixCommand extends HystrixCommand<String> {

	private final String name;
	Logger logger = Logger.getLogger(DemoHystrixCommand.class);
	
	static int port = 8082;
	private final CloseableHttpClient httpClient = HttpClients.createDefault();
	
	HttpServletRequest req;
	HttpServletResponse resp;
	String lastName;
	
	public DemoHystrixCommand(String name, HttpServletRequest req, HttpServletResponse resp, String lastName) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("demo key"))
				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
//						.withExecutionIsolationStrategy(ExecutionIsolationStrategy.THREAD)
//						.withExecutionTimeoutInMilliseconds(1000)
//						.withExecutionTimeoutEnabled(true)
//						.withExecutionIsolationThreadInterruptOnTimeout(true)
//						.withExecutionIsolationThreadInterruptOnFutureCancel(true)
//						.withExecutionIsolationSemaphoreMaxConcurrentRequests(10)
//						.withFallbackIsolationSemaphoreMaxConcurrentRequests(10)
//						.withFallbackEnabled(true)
//						.withCircuitBreakerEnabled(true)
//						.withCircuitBreakerRequestVolumeThreshold(10)
//						.withCircuitBreakerSleepWindowInMilliseconds(5000)
//						.withCircuitBreakerErrorThresholdPercentage(50)
//						.withCircuitBreakerForceOpen(false)
//						.withCircuitBreakerForceClosed(false)
////						.withMetricsRollingStatisticalWindowInMilliseconds(10000)
////						.withMetricsRollingStatisticalWindowBuckets(10)
////						.withMetricsRollingPercentileEnabled(true)
////						.withMetricsRollingPercentileWindowInMilliseconds(60000)
////						.withMetricsRollingPercentileWindowBuckets(6)
////						.withMetricsRollingPercentileBucketSize(100)
////						.withMetricsHealthSnapshotIntervalInMilliseconds(500)
////						.withRequestCacheEnabled(true)
////						.withRequestLogEnabled(true)
//						)
//				.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
//						.withCoreSize(10)
//						.withMaximumSize(10)
//						.withMaxQueueSize(-1)
//						.withQueueSizeRejectionThreshold(5)
//						.withKeepAliveTimeMinutes(1)
//						.withAllowMaximumSizeToDivergeFromCoreSize(false)
////						.withMetricsRollingStatisticalWindowInMilliseconds(10000)
////						.withMetricsRollingStatisticalWindowBuckets(10)
						));
		this.name = name;
		this.req = req;
		this.resp = resp;
		this.lastName = lastName;
	}
	
	@Override
	protected String run() throws ConnectException {
		
		String connectResult = "default value";
		logger.trace("inside hystrix run method");
		try {
			connectResult = Dispatcher(req, resp, lastName);
		} catch (ConnectException e) {
			logger.trace("dispatcher threw connect exception");
			throw new ConnectException();
			//e.printStackTrace();
		}
		
		return "command name: " + name + " | connection output: " + connectResult;
	}
	
    @Override
    protected String getFallback() {
    	logger.trace("inside fallback method");
    	port++;
    	String connectResult = "default fallback";
		try {
			connectResult = Dispatcher(req, resp, lastName);
		} catch (ConnectException e) {
			logger.trace("fallback is also broken");
			//e.printStackTrace();
		}
        return "Hello Failure " + name + "!";
    }
		
	private String Dispatcher(HttpServletRequest req, HttpServletResponse resp, String lastName)
			throws ConnectException {

		String result = null;
		if (req.getRequestURL().toString().contains(".png")) {
			result = graphics(req, resp, Integer.toString(port));
		} else {
			result = makeConnection(req, resp, Integer.toString(port), lastName);
		}
		if (result == null) {
			throw new ConnectException();
		}
		if (resp.getStatus() == 200)
			System.out.println("connected to port " + (port - 1));
		return result;
	}

	// accesses the other application
	private String makeConnection(HttpServletRequest req, HttpServletResponse resp, String port, String lastName) {
		System.out.println(port);

		HttpGet request = new HttpGet(req.getRequestURL().toString().replaceFirst("8081", port));
		if (lastName != null) {
			request = new HttpGet(req.getRequestURL().toString().replaceFirst("8081", port) + "?LastName=" + lastName);
		}

		try (CloseableHttpResponse response = httpClient.execute(request)) {

			// Get HttpResponse Status
			System.out.println(response.getStatusLine().toString());
			System.out.println(1);
			HttpEntity entity = response.getEntity();
			Header headers = entity.getContentType();
			System.out.println(headers);

			if (entity != null) {
				String result = EntityUtils.toString(entity);
				return result;
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return null;
	}

	private String graphics(HttpServletRequest req, HttpServletResponse resp, String port) {
		try {
			BufferedImage image = ImageIO.read(new URL(req.getRequestURL().toString().replaceFirst("8081", port)));
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(image, "png", os);
			InputStream is = new ByteArrayInputStream(os.toByteArray());

			resp.setContentType(MediaType.IMAGE_PNG_VALUE);
			IOUtils.copy(is, resp.getOutputStream());
			return "done";
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return null;
	}
}
