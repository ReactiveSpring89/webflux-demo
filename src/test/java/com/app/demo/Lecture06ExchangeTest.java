package com.app.demo;

import com.app.demo.models.dto.InputValidationFailedResponse;
import com.app.demo.models.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lecture06ExchangeTest extends BaseTest {
    @Autowired
    private WebClient webClient;

    @Test
    public void badRequestTest() {
        Mono<Object> responseMono = this.webClient
                .get()
                .uri("/reactiveMath/square/{number}/throw", 5)
                .exchangeToMono(this::exchange)
                .doOnNext(System.out::println)
                .doOnError(err -> System.out.println(err.getMessage()));

        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();

    }

    private Mono<Object> exchange(ClientResponse cr) {
        if(cr.statusCode().value() == 400) {
            return cr.bodyToMono(InputValidationFailedResponse.class);
        } else {
            return cr.bodyToMono(Response.class);
        }
    }

}
