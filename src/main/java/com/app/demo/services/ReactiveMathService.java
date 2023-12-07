package com.app.demo.services;

import com.app.demo.models.dto.MultiplyRequestDTO;
import com.app.demo.models.dto.Response;
import com.app.demo.util.SleepUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@Slf4j
public class ReactiveMathService {
    public Mono<Response> findSquare(int input) {
        return Mono.fromSupplier(() -> input * input)
                .map(Response::new);
    }


    public Flux<Response> multiplicationTable(int input) {
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
                //.doOnNext(i -> SleepUtil.sleepSeconds(1))
                .doOnNext(i -> log.info("reactive math-service processing: {}", i))
                .map(i -> new Response(i * input));
    }

    public Mono<Response> multiply(Mono<MultiplyRequestDTO> multiplyRequestDTOMono) {
        return multiplyRequestDTOMono
                .map(multiplyRequestDTO -> multiplyRequestDTO.getFirst() * multiplyRequestDTO.getSecond())
                .map(Response::new);
    }
}
