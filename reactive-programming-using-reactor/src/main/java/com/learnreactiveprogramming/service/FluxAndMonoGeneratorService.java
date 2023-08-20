package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {

    final int nameFilterlength = 3;
    Function<Flux<String>, Flux<String>> transformToUpperCaseAndFilterByLength
            = name -> name.map(String::toUpperCase)
            .filter(s -> s.length() <= nameFilterlength);

    Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("Giri", "Sai", "Suma"))
                .log();
    }

    Flux<String> namesFlux_map(int nameLength) {
        return Flux.fromIterable(List.of("Giri", "Sai", "Suma"))
                .map(String::toUpperCase)
                .filter(name -> name.length() <= nameLength)
                .map(name -> name.length() + "-" + name)
                .log();
    }

    Flux<String> namesFlux_transform() {
        return Flux.fromIterable(List.of("Giri", "Sai", "Suma"))
                .transform(transformToUpperCaseAndFilterByLength)
                .log();
    }

    Flux<String> namesFlux_flatmap(int nameLength) {
        return Flux.fromIterable(List.of("Giri", "Sai", "Suma"))
                .map(String::toUpperCase)
                .filter(name -> name.length() <= nameLength)
                .flatMap(this::stringToStringCharFlux)
                //.delayElements(getRandomDuration())
                .log();
    }

    Flux<String> namesFlux_flatmapDefault(int nameLength) {
        return Flux.fromIterable(List.of("Giri", "Sai", "Suma"))
                .map(String::toUpperCase)
                .filter(name -> name.length() >= nameLength)
                .flatMap(this::stringToStringCharFlux)
                .defaultIfEmpty("Default")
                .log();
    }

    Flux<String> namesFlux_flatmapSwitch(int nameLength) {
        return Flux.fromIterable(List.of("Giri", "Sai", "Suma"))
                .map(String::toUpperCase)
                .filter(name -> name.length() >= nameLength)
                .switchIfEmpty(Flux.just("default").transform(name -> name.map(String::toUpperCase)))
                .flatMap(this::stringToStringCharFlux)
                .log();
    }

    Duration getRandomDuration() {
        int duration = 1000;
        var random = new Random().nextInt(duration);
        return Duration.ofMillis(duration);
    }

    Flux<String> namesFlux_flatmapAsync(int nameLength) {
        return Flux.fromIterable(List.of("Giri", "Sai", "Suma"))
                .map(String::toUpperCase)
                .filter(name -> name.length() <= nameLength)
                .flatMap(this::stringToStringCharFluxRandom)
                //.delayElements(getRandomDuration())
                .log();
    }

    Flux<String> namesFlux_concatmap(int nameLength) {
        return Flux.fromIterable(List.of("Giri", "Sai", "Suma"))
                .map(String::toUpperCase)
                .filter(name -> name.length() <= nameLength)
                .concatMap(this::stringToStringCharFluxRandom)
                //.delayElements(getRandomDuration())
                .log();
    }

    Flux<String> namesFlux_flatmapSequencial(int nameLength) {
        return Flux.fromIterable(List.of("Giri", "Sai", "Suma"))
                .map(String::toUpperCase)
                .filter(name -> name.length() <= nameLength)
                .flatMapSequential(this::stringToStringCharFluxRandom)
                //.delayElements(getRandomDuration())
                .log();
    }

    Flux<String> stringToStringCharFlux(String s) {
        return Flux.fromArray(s.split(""));
    }

    Flux<String> stringToStringCharFluxRandom(String s) {
         /*return Flux.fromArray(s.split(""))
                 .delayElements(getRandomDuration());*/
        String[] charArray = s.split("");
        int delay = 1000;
        var random = new Random(delay);
        return Flux.fromArray(charArray)
                .delayElements(Duration.ofMillis(delay));
    }

    Flux<String> namesFlux_map_immutability() {
        var stringFlux = Flux.fromIterable(List.of("giri", "sai", "suma"));
        stringFlux.map(String::toUpperCase);
        return stringFlux;
    }

    Mono<String> nameMono() {
        return Mono.just("Giri")
                .log();
    }

    Flux<String> namesMono_flatMapMany() {
        return Mono.just("Giri")
                .map(String::toUpperCase)
                .flatMapMany(this::stringToStringCharFlux)  //For converting mono to flux
                .log();
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();

        service.namesFlux()
                .subscribe(name -> System.out.println("flux name: " + name));

        service.nameMono()
                .subscribe(name -> System.out.println("mono name: " + name));
    }
}
