package com.app.demo.controllers;

import com.app.demo.exception.InputValidationException;
import com.app.demo.models.dto.Response;
import com.app.demo.services.ReactiveMathService;
import lombok.extern.slf4j.Slf4j;
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
}
