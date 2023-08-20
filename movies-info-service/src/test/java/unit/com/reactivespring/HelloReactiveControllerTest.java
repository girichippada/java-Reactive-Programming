package com.reactivespring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@WebFluxTest(controllers = HelloReactiveController.class)
@AutoConfigureWebTestClient
class HelloReactiveControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void helloFlux() {
        webTestClient
                .get()
                .uri("/hello/flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Integer.class)
                .hasSize(3);
    }

    @Test
    void helloFluxApproach2() {
        Flux<Integer> flux = webTestClient
                .get()
                .uri("/hello/flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(flux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void helloFluxApproach3() {
        webTestClient
                .get()
                .uri("/hello/flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Integer.class)
                .consumeWith(listEntityExchangeResult -> {
                    List<Integer> responseBody = listEntityExchangeResult.getResponseBody();
                    assert Objects.requireNonNull(responseBody).size() == 3;
                    assert List.of(1, 2, 3).equals(responseBody);
                });
    }

    @Test
    void helloMono() {
        webTestClient
                .get()
                .uri("/hello/mono")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .consumeWith(stringEntityExchangeResult -> {
                    String responseBody = stringEntityExchangeResult.getResponseBody();

                    assert "Hello, I am  Mono".equals(responseBody);
                });
    }

    @Test
    void helloStream() {
        Flux<Long> streamingResponseBodyFlux = webTestClient
                .get()
                .uri("/hello/stream")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(Long.class)
                .getResponseBody();

        StepVerifier.create(streamingResponseBodyFlux)
                .expectNext(0L, 1L, 2L, 3L, 4L, 5L)
                .thenCancel()
                .verify();
    }
}