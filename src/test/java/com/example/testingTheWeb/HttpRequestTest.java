package com.example.testingTheWeb;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
    public void greetingTestService() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/greeting",
                String.class)).contains("Hello, World");
    }

    @Test
    public void canAdd() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1&b=2",
                String.class)).isEqualTo("3.0");
    }

    @ParameterizedTest
    @CsvSource({
            "1.0,   2.0,    3.0",
            "1.0,   1.0,    2.0",
            "1.0,  -2.0,   -1.0",
            "1.0,    '',    1.0",
            "1.0,   -1.0,   0.0"

    })
    void canAddParam(String a, String b, String expected) {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=" + a + "&b=" + b, String.class))
                .isEqualTo(expected);


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

    @Test
    public void canSubstractValidNumber() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/less?a=1&b=1", String.class))
                .isEqualTo("0.0");
    }

    @Test
    public void canSubstractInValidNumber() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/less?a=1&b=X", String.class))
                .contains(":400");
    }

    @Test
    public void canSubstractMissingValue() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/less?a=1", String.class))
                .isEqualTo("1.0");
    }

    @Test
    public void canSubstractEmptyValue() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/less?a=1&b=", String.class))
                .isEqualTo("1.0");
    }

    @Test
    public void canSubstractWithFractions() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/less?a=2.5&b=2", String.class))
                .isEqualTo("0.5");
    }

    @Test
    public void canMultiplyValidNumber() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/multi?a=2&b=2", String.class))
                .isEqualTo("4.0");
    }

    @Test
    public void canMultiplyInValidNumber() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/multi?a=1&b=X", String.class))
                .contains(":400");
    }

    @Test
    public void canMultiplyMissingValue() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/multi?a=1", String.class))
                .isEqualTo("0.0");
    }

    @Test
    public void canMultiplyEmptyValue() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/multi?a=1&b=", String.class))
                .isEqualTo("0.0");
    }

    @Test
    public void canMultiplyWithFractions() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/multi?a=2.5&b=2", String.class))
                .isEqualTo("5.0");
    }

    @Test
    public void canMultiplyWithZeros() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/multi?a=2.5&b=0", String.class))
                .isEqualTo("0.0");
    }

    @Test
    public void canDivideValidNumber() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/div?a=2&b=2", String.class))
                .isEqualTo("1.0");
    }

    @Test
    public void canDivideInValidNumber() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/div?a=1&b=X", String.class))
                .contains(":400");
    }

    @Test
    public void canDivideMissingValue() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/div?a=1", String.class))
                .contains("Infinity");
    }

    @Test
    public void canDivideEmptyValue() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/div?a=1&b=", String.class))
                .contains("Infinity");
    }

    @Test
    public void canDivideWithFractions() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/div?a=2.5&b=2", String.class))
                .isEqualTo("1.25");
    }

    @Test
    public void canDivideWithZero() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/div?a=2&b=0", String.class))
                .contains("Infinity");
    }

    @Test
    public void canDivideBetweenZeros() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/div?a=0&b=0", String.class))
                .contains("NaN");
    }

    @Test
    public void canDivideZeroWith() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/div?a=0&b=2", String.class))
                .isEqualTo("0.0");
    }
}