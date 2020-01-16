package com.log4j;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

public class Log4jApplication {

	static final Logger logger = Logger.getLogger(Log4jApplication.class);
	 
    public static void main(String[] args)
    {
        //DOMConfigurator is used to configure logger from xml configuration file
        DOMConfigurator.configure("F:\\eclipse - last version\\workshop\\MavenTestProject\\src\\main\\resources\\log4j.xml");
 
        //Log in console in and log file
        logger.debug("Log4j appender configuration is successful !!");
    }
}
