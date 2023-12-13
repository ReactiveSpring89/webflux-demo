package com.app.demo;

import com.app.demo.models.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec01SingleResponseTest extends BaseTest {
    @Autowired
    private WebClient webClient;

    @Test
    public void blockTest() {
       Response response = this.webClient
                .get()
                .uri("reactiveMath/square/{input}", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .block();

        System.out.println(response);

    }


    @Test
    public void stepVerifierTest() {
        Mono<Response> responseMono = this.webClient
                .get()
                .uri("reactiveMath/square/{input}", 5)
                .retrieve()
                .bodyToMono(Response.class);

        StepVerifier.create(responseMono)
                .expectNextMatches(r -> r.getOutout() == 25)
                .verifyComplete();

    }

}
