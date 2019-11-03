package com.github.chr1st0ph.showcase.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cool.stuff")
@Getter
@Setter
public class CoolStuffProperties {

	private String url;
}
