package com.app.demo.controllers;

import com.app.demo.models.dto.MultiplyRequestDTO;
import com.app.demo.models.dto.Response;
import com.app.demo.services.ReactiveMathService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@Slf4j
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

    @GetMapping(value = "/table/{number}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> findMultiplicationTableStream(@PathVariable int number) {
        return reactiveMathService.multiplicationTable(number);
    }

    @PostMapping("/multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDTO> multiplyRequestDTO,
                                   @RequestHeader Map<String, String> headers) {
        log.info("headers: {}", headers);
        return reactiveMathService.multiply(multiplyRequestDTO);
    }
}
