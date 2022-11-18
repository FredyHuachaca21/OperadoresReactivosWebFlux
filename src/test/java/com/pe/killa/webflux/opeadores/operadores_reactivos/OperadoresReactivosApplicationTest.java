package com.pe.killa.webflux.opeadores.operadores_reactivos;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OperadoresReactivosApplicationTest {

    OperadoresReactivosApplication operadores = new OperadoresReactivosApplication();


    @Test
    void testCrearFlux() {

        //GIVEN
        List<String> nombres = Arrays.asList("Fredy", "Edgar");
        //WHEN
        Flux<String> nombre = Flux.fromIterable(nombres);
        //THEN
        StepVerifier.create(nombre)
                .expectNext("Fredy", "Edgar")
                .expectNextCount(2)
                .expectComplete();


    }
}