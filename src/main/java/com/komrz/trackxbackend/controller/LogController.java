package com.komrz.trackxbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {
	
	private static final Logger LOG = LoggerFactory.getLogger(LogController.class);
	 
    @RequestMapping("/")
    @ResponseBody
    String home() {
    	LOG.trace("This is a TRACE message.");
    	LOG.debug("This is a DEBUG message.");
    	LOG.info("This is an INFO message.");
    	LOG.warn("This is a WARN message.");
    	LOG.error("You guessed it, an ERROR message.");
//        LOG.info("Logger Started...");
        return "Welcome to the era of Komrz TrackX.";
    }

}
