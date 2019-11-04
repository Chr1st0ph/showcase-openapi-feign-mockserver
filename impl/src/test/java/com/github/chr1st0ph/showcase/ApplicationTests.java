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

	@Autowired
	private ClientAndServer mockServer;

	@Autowired
	private CoolStuffApi client;

	@Autowired
	private ObjectMapper objectMapper;

	private CoolStuff coolStuff = buildCoolStuff(1111L);

	@BeforeEach
	public void configureTest() throws JsonProcessingException {
		mockServer.when(HttpRequest.request().withMethod("GET").withPath("/coolStuffs"))
				.respond(HttpResponse.response().withBody(objectMapper.writeValueAsBytes(buildExpectedResponse())));
		mockServer.when(HttpRequest.request().withMethod("GET").withPath("/coolStuffs/1111"))
				.respond(HttpResponse.response().withBody(objectMapper.writeValueAsBytes(coolStuff)));
		mockServer.when(HttpRequest.request().withMethod("POST").withPath("/coolStuffs"))
				.respond(HttpResponse.response().withBody(objectMapper.writeValueAsBytes(coolStuff)));
	}

	@Test
	void testGet() {
		List<CoolStuff> response = client.getCoolStuff();
		assertThat(response).isEqualTo(buildExpectedResponse());
	}

	@Test
	void testGetById() {
		CoolStuff response = client.getCoolStuffById(1111L);
		assertThat(response).isEqualTo(coolStuff);
	}

	@Test
	void testPost() {
		CoolStuff response = client.postCoolStuff(coolStuff);
		assertThat(response).isEqualTo(coolStuff);
	}

	protected List<CoolStuff> buildExpectedResponse() {
		return Arrays.asList(coolStuff, buildCoolStuff(2222L));
	}

	private CoolStuff buildCoolStuff(long id) {
		CoolStuff result = new CoolStuff();
		result.setId(id);
		result.setName(String.format("cool-name %d", id));
		result.setTag(String.format("cool-tag %d", id));
		return result;
	}
}
