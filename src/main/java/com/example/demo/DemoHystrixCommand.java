package com.example.demo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.annotation.PostConstruct;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

public class DemoHystrixCommand extends HystrixCommand<String> {

	private final String name;
	Logger logger = Logger.getLogger(DemoHystrixCommand.class);
	
	static int port = 8082;
	private final CloseableHttpClient httpClient = HttpClients.createDefault();
	
	HttpServletRequest req;
	HttpServletResponse resp;
	String lastName;
	
	HystrixCommandProperties.Setter hcpSetter;
	
	HystrixThreadPoolProperties.Setter htppSetter;
	
	public DemoHystrixCommand(String name, HttpServletRequest req, HttpServletResponse resp, String lastName,
			HystrixCommandProperties.Setter hcpSetter, HystrixThreadPoolProperties.Setter htppSetter) {
		
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("demo key"))
				.andCommandPropertiesDefaults(hcpSetter
						)
				.andThreadPoolPropertiesDefaults(htppSetter
						));
		
		this.name = name;
		this.req = req;
		this.resp = resp;
		this.lastName = lastName;
		this.hcpSetter = hcpSetter;
		this.htppSetter = htppSetter;
	}
	
	@Override
	protected String run() throws ConnectException {
		
		//logger.trace("request volume threshold: " + hcpSetter.getCircuitBreakerRequestVolumeThreshold());

		
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
