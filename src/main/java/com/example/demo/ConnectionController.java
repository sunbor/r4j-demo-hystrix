package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Lazy
@RestController
public class ConnectionController {
	
	Logger logger = Logger.getLogger(ConnectionController.class);

	@RequestMapping("/connect")
	public String connection() {
		String test = new DemoHystrixCommand("hi its me").execute();
		return test;
	}
	

}
