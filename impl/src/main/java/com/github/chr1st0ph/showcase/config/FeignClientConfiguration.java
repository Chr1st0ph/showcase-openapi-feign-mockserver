package com.github.chr1st0ph.showcase.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.chr1st0ph.showcase.ApiClient;
import com.github.chr1st0ph.showcase.api.CoolStuffApi;

@Configuration
public class FeignClientConfiguration {

	@Bean
	public CoolStuffApi buildCoolStuffApi(@Value("${cool.stuff.url}") String baseUrl) {
		return new ApiClient()
			.setBasePath(baseUrl)
			.buildClient(CoolStuffApi.class);
	}
}
