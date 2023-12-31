package com.app.demo.config;

import com.app.demo.exception.InputValidationException;
import com.app.demo.models.dto.InputValidationFailedResponse;
import com.app.demo.models.dto.MultiplyRequestDTO;
import com.app.demo.models.dto.Response;
import com.app.demo.services.ReactiveMathService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RequestHandler {
    private ReactiveMathService mathService;

    public RequestHandler(final ReactiveMathService mathService) {
        this.mathService = mathService;
    }

    public Mono<ServerResponse> squareHandler(ServerRequest serverRequest) {
       int input = Integer.parseInt(serverRequest.pathVariable("input"));
       Mono<Response> responseMono = mathService.findSquare(input);
       return ServerResponse.ok()
               .body(responseMono, Response.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<Response> responseFlux = mathService.multiplicationTable(input);
        return ServerResponse.ok()
                .body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> tableStreamHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<Response> responseFlux = mathService.multiplicationTable(input);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> multiply(ServerRequest serverRequest) {
        Mono<MultiplyRequestDTO> multiplyRequestDTO = serverRequest.bodyToMono(MultiplyRequestDTO.class);
        Mono<Response> responseMono = mathService.multiply(multiplyRequestDTO);
        return ServerResponse.ok()
                .body(responseMono, Response.class);
    }

    public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        if(input < 10 || input > 20) {
            return Mono.error(new InputValidationException(input));
        }
        Mono<Response> responseMono = mathService.findSquare(input);
        return ServerResponse.ok()
                .body(responseMono, Response.class);
    }
}
