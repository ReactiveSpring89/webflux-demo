package com.app.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:9092")
                .filter(this::sessionToken)
                //.defaultHeaders(h -> h.setBasicAuth("username", "password"))
                .build();
    }

//    private Mono<ClientResponse> sessionToken(ClientRequest cr, ExchangeFunction function) {
//        System.out.println("Generating session token");
//        ClientRequest clientRequest = ClientRequest.from(cr)
//                .headers(h -> h.setBearerAuth("some-lengthy-jwt"))
//                .build();
//        return function.exchange(clientRequest);
//    }

    private Mono<ClientResponse> sessionToken(ClientRequest cr, ExchangeFunction function) {
        System.out.println("Generating session token");
        ClientRequest request = cr.attribute("auth")
                .map(v -> v.equals("basic") ? withBasicAuth(cr): withOAuth(cr))
                .orElse(cr);

        return function.exchange(request);
    }

    private ClientRequest withBasicAuth(ClientRequest request) {
        return ClientRequest.from(request)
                .headers(h -> h.setBasicAuth("username", "password"))
                .build();
    }

    private ClientRequest withOAuth(ClientRequest request) {
        return ClientRequest.from(request)
                .headers(h -> h.setBearerAuth("sometoken"))
                .build();
    }
}
