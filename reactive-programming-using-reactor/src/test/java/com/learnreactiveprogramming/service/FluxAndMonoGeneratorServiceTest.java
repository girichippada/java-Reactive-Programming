package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();

    @Test
    void testNameFlux() {
        var nameFlux = service.namesFlux();

        StepVerifier
                .create(nameFlux)
                .expectNext("Giri")
                .expectNext("Sai")
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void testNameMono() {

        var nameMono = service.nameMono();

        StepVerifier
                .create(nameMono)
                //.expectNext("giri")
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void namesFlux_map() {
        int nameLength = 3;
        var namesFlux_map = service.namesFlux_map(nameLength);
        StepVerifier.create(namesFlux_map)
                //.expectNext("GIRI")   //fail
                .expectNext("3-SAI")
                //.expectNextCount(2)   //fail
                .verifyComplete();
    }

    @Test
    void namesFlux_map_immutability() {
        var namesFlux_map = service.namesFlux_map_immutability();
        StepVerifier.create(namesFlux_map)
                .expectNext("giri")
                .expectNext("sai")
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmap() {

        int nameLength = 3;
        var namesFlux_map = service.namesFlux_flatmap(nameLength);
        StepVerifier.create(namesFlux_map)
                .expectNext("S","A","I")
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmapAsync() {
        int nameLength = 3;
        var namesFlux_map = service.namesFlux_flatmapAsync(nameLength);
        StepVerifier.create(namesFlux_map)
                .expectNext("S","A","I")
                .verifyComplete();
    }

    @Test
    void namesFlux_concatmap() {
        int nameLength = 3;
        var namesFlux_map = service.namesFlux_concatmap(nameLength);
        StepVerifier.create(namesFlux_map)
                .expectNext("S","A","I")
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmapSequencial() {
        int nameLength = 3;
        var namesFlux_map = service.namesFlux_flatmapSequencial(nameLength);
        StepVerifier.create(namesFlux_map)
                .expectNext("S","A","I")
                .verifyComplete();
    }

    @Test
    void namesMono_flatMapMany() {

        var namesFlux_map = service.namesMono_flatMapMany();

        StepVerifier.create(namesFlux_map)
                .expectNext("G", "I", "R", "I")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform() {
        var namesFlux_map = service.namesFlux_transform();

        StepVerifier.create(namesFlux_map)
                .expectNext("SAI")
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmapDefault() {
        var namesFlux_map = service.namesFlux_flatmapDefault(7);

        StepVerifier.create(namesFlux_map)
                .expectNext("Default")
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmapSwitch() {
        var namesFlux_map = service.namesFlux_flatmapSwitch(6);

        StepVerifier.create(namesFlux_map)
                .expectNext("D", "E", "F", "A", "U", "L", "T")
                .verifyComplete();
    }
}