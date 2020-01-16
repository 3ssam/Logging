package com.auc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

public class Log4jPropertiesConfigurationExample {
	public static Logger logger = Logger.getLogger(Log4jPropertiesConfigurationExample.class);
	public static void main(String[] args)
    {

		int x = 5;
		System.out.println(x);
        //PropertiesConfigurator is used to configure logger from properties file
		DOMConfigurator.configure("F:\\eclipse - last version\\workshop\\CommentMangementService\\src\\main\\resources\\log4j.xml");
 
        //Log in console in and log file
        logger.debug("Log4j console appender configuration is successful !!");
        try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
