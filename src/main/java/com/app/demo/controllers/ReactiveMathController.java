package com.app.demo.controllers;

import com.app.demo.models.dto.Response;
import com.app.demo.services.ReactiveMathService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/reactiveMath")
public class ReactiveMathController {
    private ReactiveMathService reactiveMathService;

    public ReactiveMathController(ReactiveMathService reactiveMathService) {
        this.reactiveMathService = reactiveMathService;
    }

    @GetMapping("/square/{number}")
    public Mono<Response> findSquare(@PathVariable int number) {
        return reactiveMathService.findSquare(number);
    }

    @GetMapping("/table/{number}")
    public Flux<Response> findMultiplicationTable(@PathVariable int number) {
        return reactiveMathService.multiplicationTable(number);
    }
}
