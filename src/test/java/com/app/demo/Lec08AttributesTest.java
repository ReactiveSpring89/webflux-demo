package com.app.demo;

import com.app.demo.models.dto.MultiplyRequestDTO;
import com.app.demo.models.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec08AttributesTest extends  BaseTest {
    @Autowired
    private WebClient webClient;

    @Test
    public void headersTest() {
        Mono<Response> responseMono = this.webClient
                .post()
                .uri("/reactiveMath/multiply")
                .bodyValue(buildRequestDTO(5, 2))
                .attribute("auth", "oauth")
                //.headers(h -> h.setBasicAuth("username", "password"))
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    private MultiplyRequestDTO buildRequestDTO(int num1, int num2) {
        MultiplyRequestDTO dto = new MultiplyRequestDTO();
        dto.setFirst(num1);
        dto.setSecond(num2);
        return dto;
    }
}
