package com.app.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {
    private RequestHandler requestHandler;

    public RouterConfig(final RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("router/square/{input}", requestHandler::squareHandler)
                .GET("/router/table/{input}", requestHandler::tableHandler)
                .GET("/router/table/{input}/stream", requestHandler::tableStreamHandler)
                .build();
    }
}
