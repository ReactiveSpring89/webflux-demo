package com.app.demo.services;

import com.app.demo.models.dto.Response;
import com.app.demo.util.SleepUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
@Slf4j
public class MathService {
    public Response findSquare(int input) {
        return new Response(input * input);
    }

    public List<Response> multiplicationTable(int input) {
        return IntStream.rangeClosed(1, 10)
                .peek(i -> SleepUtil.sleepSeconds(1))
                .peek(i -> log.info("math-service processing: {}", i))
                .mapToObj(i -> new Response(i * input))
                .toList();
    }
}
