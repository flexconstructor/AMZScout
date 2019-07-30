package ru.flexconstructor.AMZScout.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.flexconstructor.AMZScout.service.ThrottlingService;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;

/**
 * Implementation of {@link HandlerFilterFunction}.
 */
@RequiredArgsConstructor
public class ThrottlerFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    /**
     * {@link ThrottlingService} instance.
     */
    private final ThrottlingService throttlingService;

    /**
     * Filters all http requests by throttles check. It calls {@link HandlerFunction}
     * if throttler service returns true and returns 502 otherwise.
     *
     * @param serverRequest    {@link ServerRequest} instance.
     * @param handlerFunction  {@link HandlerFunction}
     * @return                 {@link Mono<ServerResponse>}
     */
    @Override
    public Mono<ServerResponse> filter(ServerRequest serverRequest,
                                       HandlerFunction<ServerResponse> handlerFunction) {
        String ip;
        try {
            ip = this.getIP(serverRequest);
        } catch (Throwable ex) {
            return ServerResponse.status(BAD_GATEWAY).build();
        }
        if (this.throttlingService.throttle(ip)) {
            return handlerFunction.handle(serverRequest);
        }
        return ServerResponse.status(BAD_GATEWAY).build();
    }

    // returns request remote IP.
    private String getIP(ServerRequest request) {
        return request.remoteAddress()
                .orElseThrow(IllegalArgumentException::new)
                .getHostName();
    }
}
