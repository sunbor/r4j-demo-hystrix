package com.example.demo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Lazy
@RestController
public class ConnectionController {

	static int port = 8082;
	private final CloseableHttpClient httpClient = HttpClients.createDefault();
	Logger logger = Logger.getLogger(ConnectionController.class);

	@RequestMapping("/**")
	public String connection(HttpServletRequest req, HttpServletResponse resp,
			@RequestParam(value = "lastName", required = false) String lastName) throws Exception {
//		String test = new DemoHystrixCommand(Dispatcher(req, resp, lastName)).execute();
		String test = new DemoHystrixCommand("hi its me again", req, resp, lastName).execute();
		return test;
	}

}
