package com.log4j;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jApplication {

	static final Logger logger = Logger.getLogger(Log4jApplication.class);
	 
    public static void main(String[] args)
    {
        // PropertiesConfigurator is used to configure logger from properties file
        PropertyConfigurator.configure("log4j.properties");
 
        // Log in console
        logger.debug("Log4j console appender configuration is successful !!");
    }
}
