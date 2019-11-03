package com.github.chr1st0ph.showcase.config;

import org.mockserver.integration.ClientAndServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.github.chr1st0ph.showcase.ApiClient;
import com.github.chr1st0ph.showcase.api.CoolStuffApi;

@Configuration
@Profile("mock-server")
public class TestFeignClientConfiguration {
  @Bean
  @Primary
  public CoolStuffApi buildTestCoolStuffApi(ClientAndServer clientAndServer) {
    return new ApiClient()
        .setBasePath(String.format("http://localhost:%d/", clientAndServer.getLocalPort()))
        .buildClient(CoolStuffApi.class);
  }

  @Bean(destroyMethod = "stop")
  public ClientAndServer buildClientAndServer() {
    return ClientAndServer.startClientAndServer();
  }
}
