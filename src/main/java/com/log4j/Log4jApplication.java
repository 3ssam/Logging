package com.log4j;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

public class Log4jApplication {

	private static Logger logger = Logger.getLogger(Log4jApplication.class);

	public static void main(String[] args) throws InterruptedException {
        PropertyConfigurator.configure("F:\\eclipse - last version\\workshop\\MavenTestProject\\src\\main\\resources\\log4j.properties");
        
        logger.debug("Sample debug message");
        logger.info("Sample info message");
        logger.error("Sample error message");
        logger.fatal("Sample fatal message");
	}
}
