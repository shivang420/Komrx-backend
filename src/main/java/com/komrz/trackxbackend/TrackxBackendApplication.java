package com.komrz.trackxbackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * @author souravbhattacharjee
 * @author shivang gupta
 *
 */
@SpringBootApplication
@ServletComponentScan
public class TrackxBackendApplication {
	
	private static final Logger LOG=LoggerFactory.getLogger(TrackxBackendApplication.class);
	
    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

	public static void main(String[] args) {
		SpringApplication.run(TrackxBackendApplication.class, args);
		LOG.info("Application Started...");
	}
}
