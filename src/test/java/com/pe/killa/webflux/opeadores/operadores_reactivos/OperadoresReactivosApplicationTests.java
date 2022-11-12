package com.pe.killa.webflux.opeadores.operadores_reactivos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class OperadoresReactivosApplicationTests {

	private static List<String> nombres =
			Arrays.asList("Fredy", "Edgar");


	@Test
	void testMonoDataInt() {
			Mono<Integer> monoNum =	Mono.just(10);
			monoNum.subscribe(x -> System.out.println("mono: " + x));

		StepVerifier.create(monoNum)
				.expectNext(10)
				.expectComplete()
				.verify();

	}
	@Test
	void testCrearMonoFecha(){
		Mono<LocalDate> monoFecha =	Mono.just(LocalDate.now());
		monoFecha.subscribe(System.out::println);

		StepVerifier.create(monoFecha)
				.expectNext(LocalDate.now())
				.expectComplete()
				.verify();
	}

	@Test
	void testMonoLista(){
		Mono<List<String>> monoLista = Mono.just(nombres);
		monoLista.subscribe(System.out::println);


		StepVerifier.create(monoLista)
				//.expectSubscription()
				.expectNext(Arrays.asList("Fredy", "Edgar"))
				//.expectNext( new ArrayList<>(Arrays.asList("Fredy", "Edgar")))
				//.expectNextCount(0)
				.expectComplete()
				.verify();
	}



}
