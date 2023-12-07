package com.app.demo.controllers;

import com.app.demo.exception.InputValidationException;
import com.app.demo.models.dto.Response;
import com.app.demo.services.ReactiveMathService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("reactiveMath")
public class ReactiveMathValidationController {
    private ReactiveMathService reactiveMathService;

    public ReactiveMathValidationController(ReactiveMathService reactiveMathService) {
        this.reactiveMathService = reactiveMathService;
    }

    @GetMapping("/square/{number}/throw")
    public Mono<Response> findSquare(@PathVariable int number) {
        if(number < 10 || number > 20) {
            log.error("Invalid input");
            throw new InputValidationException(number);
        }
        return reactiveMathService.findSquare(number);
    }

    @GetMapping("/square/{number}/monoError")
    public Mono<Response> monoError(@PathVariable int number) {
        return Mono.just(number)
                .handle((input, sink) -> {
                    if (input >= 10 && input <= 20) {
                        sink.next(input);
                    } else {
                        sink.error(new InputValidationException(input));
                    }
                })
                .cast(Integer.class)
                .flatMap(input -> reactiveMathService.findSquare(input));

    }


    @GetMapping("/square/{number}/assignment")
    public Mono<ResponseEntity<Response>> assignment(@PathVariable int number) {
        return Mono.just(number)
                .filter(i -> i >= 10 && i <= 20)
                .flatMap(i -> reactiveMathService.findSquare(i))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());

    }
}
