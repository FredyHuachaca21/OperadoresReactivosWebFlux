package com.pe.killa.webflux.opeadores.operadores_reactivos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class OperadoresReactivosApplicationTests {

	private static List<String> nombres = Arrays.asList("Fredy", "Gianella", "Frank", "Alex", "Marck", "Aracely", "Blanca", "juan");


	@Test
	void testMonoDataInt() {
			Mono<Integer> monoNum =	Mono.just(10);
			monoNum.subscribe(x -> System.out.println("mono: " + x));

		StepVerifier.create(monoNum)
				.expectNext(10)
				.expectComplete()
				.verify();

	}

}
