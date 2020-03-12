package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class DemoHystrixCommand extends HystrixCommand<String> {

	private final String name;
	Logger logger = Logger.getLogger(DemoHystrixCommand.class);
	
	public DemoHystrixCommand(String name) {
		super(HystrixCommandGroupKey.Factory.asKey("demo key"));
		this.name = name;
	}
	
	@Override
	protected String run() throws ConnectException {
		
		String connectResult = "default value";
		try {
			connectResult = makeConnection();
		} catch (ConnectException e) {
			throw new ConnectException();
			//e.printStackTrace();
		}
		
		return "command name: " + name + " | connection output: " + connectResult;
	}
	
    @Override
    protected String getFallback() {
        return "Hello Failure " + name + "!";
    }
	
	// accesses the other application
	private String makeConnection() throws ConnectException {

		String inputLine = "accessProducer did not work";
		try {URL url = new URL("http://localhost:8082/test");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			inputLine = content.toString();
			in.close();

		} catch (MalformedURLException e) {
			logger.error("url format error while trying to connect to producer");
			e.printStackTrace();
		}
		catch (ConnectException e) {
			logger.error("failed to connect to producer");
			throw e;
			// e.printStackTrace();
		} catch (IOException e) {
			logger.error("ioexception while trying to connect to producer");
			e.printStackTrace();
		}

		//logger.trace("producer output: " + inputLine);
		return inputLine;
	}
}
