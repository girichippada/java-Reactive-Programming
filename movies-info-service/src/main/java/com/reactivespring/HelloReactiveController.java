package com.reactivespring;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/hello")
public class HelloReactiveController {

    @GetMapping("/flux")
    public Flux<Integer> helloFlux() {
        return Flux.just(1, 2, 3)
                .log();
    }

    @GetMapping("/mono")
    public Flux<String> helloMono() {
        return Flux.just("Hello, I am  Mono")
                .log();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> helloStream() {
        return Flux.interval(Duration.ofSeconds(1))
                .log();
    }
}
