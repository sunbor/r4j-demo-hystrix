package com.example.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

@Lazy
@RestController
public class ConnectionController {

	static int port = 8082;
	private final CloseableHttpClient httpClient = HttpClients.createDefault();
	Logger logger = Logger.getLogger(ConnectionController.class);

	@Lazy
	@Autowired
	private HystrixCommandProperties.Setter hcpSetter;
	
	@Lazy
	@Autowired
	private HystrixThreadPoolProperties.Setter htppSetter;
	
	@RequestMapping("/**")
	public String connection(HttpServletRequest req, HttpServletResponse resp,
			@RequestParam(value = "lastName", required = false) String lastName) throws Exception {
//		String test = new DemoHystrixCommand(Dispatcher(req, resp, lastName)).execute();
//		String test = new DemoHystrixCommand("hi its me again", req, resp, lastName).execute();
		String test = new DemoHystrixCommand("demo hystrix command", req, resp, lastName, hcpSetter, htppSetter).execute();
		return test;
	}

}
