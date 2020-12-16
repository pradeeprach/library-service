package com.microservices.library.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "endpoints")
@Getter
@Setter
public class EndpointsConfiguration {

	private String bookEndpoint;
	
	private String userEndpoint;
}
