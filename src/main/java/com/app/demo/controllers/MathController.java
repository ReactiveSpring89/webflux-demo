package com.app.demo.controllers;

import com.app.demo.models.dto.Response;
import com.app.demo.services.MathService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/math")
public class MathController {
    private MathService mathService;

    public MathController(MathService mathService) {
        this.mathService = mathService;
    }

    @GetMapping("/square/{number}")
    public Response findSquare(@PathVariable int number) {
        return mathService.findSquare(number);
    }

    @GetMapping("/table/{number}")
    public List<Response> findMultiplicationTable(@PathVariable int number) {
        return mathService.multiplicationTable(number);
    }
}
