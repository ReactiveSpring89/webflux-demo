package com.app.demo.config;

import com.app.demo.exception.InputValidationException;
import com.app.demo.models.dto.InputValidationFailedResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
public class RouterConfig {
    private RequestHandler requestHandler;

    public RouterConfig(final RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> highLevelRouter() {
        return RouterFunctions.route()
                .path("router", this::serverResponseRouterFunction)
                .build();
    }

    private RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("square/{input}", requestHandler::squareHandler)
                .GET("table/{input}", requestHandler::tableHandler)
                .GET("table/{input}/stream", requestHandler::tableStreamHandler)
                .POST("multiply", requestHandler::multiply)
                .GET("square/{input}/validation", requestHandler::squareHandlerWithValidation)
                .onError(InputValidationException.class, exceptionHandler())
                .build();
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
        return (err, req) -> {
            InputValidationException exception = (InputValidationException) err;
            InputValidationFailedResponse res = new InputValidationFailedResponse();
            res.setInput(exception.getInput());
            res.setMessage(exception.getMessage());
            res.setErrorCode(exception.getErrorCode());
            return ServerResponse.badRequest().bodyValue(res);
        };
    }
}
