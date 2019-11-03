package com.github.chr1st0ph.showcase;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.chr1st0ph.showcase.annotation.MockServerSpringBootTest;
import com.github.chr1st0ph.showcase.api.CoolStuffApi;
import com.github.chr1st0ph.showcase.model.CoolStuff;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;

@MockServerSpringBootTest
class ApplicationTests {

  @Autowired private ClientAndServer mockServer;

  @Autowired private CoolStuffApi client;

  @Autowired private ObjectMapper objectMapper;

  @BeforeEach
  public void configureTest() throws JsonProcessingException {
    mockServer
        .when(HttpRequest.request().withMethod("GET").withPath("/coolStuffs"))
        .respond(
            HttpResponse.response().withBody(objectMapper.writeValueAsBytes(buildExpectedResponse())));
    mockServer
	    .when(HttpRequest.request().withMethod("GET").withPath("/coolStuffs/1111"))
	    .respond(
	        HttpResponse.response().withBody(objectMapper.writeValueAsBytes(buildCoolStuff())));
    mockServer
        .when(HttpRequest.request().withMethod("POST").withPath("/coolStuffs"))
        .respond(
            HttpResponse.response().withBody(objectMapper.writeValueAsBytes(buildCoolStuff())));
  }

  @Test
  void testGet() {
    List<CoolStuff> response = client.getCoolStuff();
    assertThat(response).isEqualTo(buildExpectedResponse());
  }

  @Test
  void testGetById() {
    CoolStuff response = client.getCoolStuffById(1111L);
    assertThat(response).isEqualTo(buildCoolStuff());
  }

  @Test
  void testPost() {
    CoolStuff response = client.postCoolStuff(buildCoolStuff());
    assertThat(response).isEqualTo(buildCoolStuff());
  }

  protected List<CoolStuff> buildExpectedResponse() {
    CoolStuff cool1 = buildCoolStuff();

    CoolStuff cool2 = new CoolStuff();
    cool2.setId(2222L);
    cool2.setName("cool-name 2");
    cool2.setTag("cool-2");

    return Arrays.asList(cool1);
  }

  private CoolStuff buildCoolStuff() {
    CoolStuff cool1 = new CoolStuff();
    cool1.setId(1111L);
    cool1.setName("cool-name 1");
    cool1.setTag("cool-1");
    return cool1;
  }
}
