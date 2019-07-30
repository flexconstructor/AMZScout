package ru.flexconstructor.AMZScout.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * HTTP controller.
 */
@Component
public class ExampleController {

    /**
     *  Returns status 200 and empty string.
     *
     * @param request {@link ServerRequest} instance.
     * @return        {@link Mono<ServerResponse>}
     */
    public Mono<ServerResponse> handle(ServerRequest request){
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject(""));
    }
}
