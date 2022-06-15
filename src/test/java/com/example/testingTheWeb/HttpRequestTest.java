package com.example.testingTheWeb;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void greetingShouldReturnDefaultMessage() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
				String.class)).contains("Hello, World");
	}

	@Test
	public void canAdd(){
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1&b=2",
				String.class)).isEqualTo("3.0");
	}
	@Test
	public void canAddWithMissingValue() {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1", String.class))
				.isEqualTo("1.0");
	}

	@Test
	public void canAddWithEmptyValue() {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1&b=", String.class))
				.isEqualTo("1.0");
	}

	@Test
	public void canAddWithFractions() {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1.5&b=2", String.class))
				.isEqualTo("3.5");
	}

	@Test
	public void canAddWithInvalidNumber() {
		assertThat(this.restTemplate.getForEntity("http://localhost:" + port + "/add?a=1&b=X", String.class)
				.getStatusCode().is4xxClientError()).isTrue();
	}
	@Test
	public void canAddNegativeNumbers() {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1&b=-2", String.class))
				.isEqualTo("-1.0");
	}
}