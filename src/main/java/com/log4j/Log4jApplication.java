package com.log4j;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

public class Log4jApplication {

	private static Logger logger = Logger.getLogger(Log4jApplication.class);

	public static void main(String[] args) throws InterruptedException {
        PropertyConfigurator.configure("F:\\eclipse - last version\\workshop\\MavenTestProject\\src\\main\\resources\\log4j.xml");
		for (long i = 1; i <= Long.MAX_VALUE; i++) {
			logger.info("This is the " + i + " time I say 'Hello World'.");
			//Thread.sleep(100);
		}
	}
}
