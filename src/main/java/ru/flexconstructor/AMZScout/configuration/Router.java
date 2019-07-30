package ru.flexconstructor.AMZScout.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.RequestPredicates;
import ru.flexconstructor.AMZScout.controller.ThrottlerFilter;
import ru.flexconstructor.AMZScout.controller.ExampleController;
import ru.flexconstructor.AMZScout.service.ThrottlingService;

/**
 * HTTP router.
 */
@Configuration
@RequiredArgsConstructor
public class Router {

    /**
     * {@link ThrottlingService} instance.
     */
    private final ThrottlingService throttlingService;

    /**
     * Routes http requests.
     *
     * @param controller {@link ExampleController} instance.
     *
     * @return {@link RouterFunction<ServerResponse>}
     */
    @Bean
    public RouterFunction<ServerResponse> route(ExampleController controller){
        return RouterFunctions.route(RequestPredicates.GET("/my_api")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),controller::handle)
                .filter(new ThrottlerFilter(throttlingService));

    }

}
