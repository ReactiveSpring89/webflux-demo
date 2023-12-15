package com.app.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.net.URI;

public class Lecture07QueryParamsTest extends BaseTest {
    @Autowired
    private WebClient webClient;

    private String queryString = "http://localhost:9092/jobs/search?count={count}&page={page}";

    @Test
    public void queryParamsTest() {
       URI uri = UriComponentsBuilder.fromUriString(queryString)
                .build(10, 20);

       Flux<Integer> responseFlux = this.webClient.get()
               .uri(uri)
               .retrieve()
               .bodyToFlux(Integer.class)
               .doOnNext(System.out::println);

        StepVerifier.create(responseFlux)
                .expectNextCount(2)
                .verifyComplete();
    }
}
