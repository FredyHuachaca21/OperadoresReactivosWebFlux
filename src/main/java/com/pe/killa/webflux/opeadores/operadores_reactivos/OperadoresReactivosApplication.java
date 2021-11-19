package com.pe.killa.webflux.opeadores.operadores_reactivos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class OperadoresReactivosApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(OperadoresReactivosApplication.class);
	private static List<String> nombres = Arrays.asList("Fredy", "Gianella", "Frank", "Alex", "Marck", "Aracely", "Blanca");

	public static void main(String[] args) {
		SpringApplication.run(OperadoresReactivosApplication.class, args);
	}

	public void crearMono(){
	Mono<Integer> monoNum =	Mono.just(10);
	monoNum.subscribe(x -> log.info("Número: " + x));
	}

	public void crearFlux(){
		/* Método fromIterable funciona para Flux */
		Flux<String> fluxNombres = Flux.fromIterable(nombres);
		fluxNombres.subscribe(System.out::println);
	}

	public void fluxAmono(){
		/*Proceso inverso para transformar de Flux a Mono*/
		/*OJO la PIOJO -> DE MONO A FLUX NO SE PUEDE REALIZAR LA TRANSFORMACIÓN*/
		Flux<String> fluxNombres = Flux.fromIterable(nombres);
		fluxNombres.collectList().subscribe(System.out::println);
		/*Se emite todo el elemento de la lista como un solo bloque*/
	}

	public void op_doOnNext(){
		Flux<String> fluxNombres = Flux.fromIterable(nombres);
		/*Valida o emite el proceso por cada elemento*/
		fluxNombres.doOnNext(System.out::println)
				.subscribe();
	}

	public void op_map(){
		/*Operador map transforma el flujo de elemento*/
		Flux<String> fluxNombres = Flux.fromIterable(nombres);
		fluxNombres.map(String::toUpperCase)
				.map(x -> "Nombres: " + x)
				.subscribe(System.out::println);
	}

	public void op_flatMap(){
		/*Sirve para transformar elementos*/
		/*diferencia con map, el es sintaxis de RETORNO de variable*/
		/*tiene que ser explícito para indicar el tipo de retorno Ejm: Mono.just(xxxxx)*/
		Mono.just("Fredy")
				.flatMap(x -> Mono.just(21))
				.subscribe(System.out::println);

		/*Mono.just("Fredy")
				.flatMap(x -> Mono.just(10))
				.subscribe(f -> log.info(f.toString()));*/

	}

	@Override
	public void run(String... args) throws Exception {
		op_flatMap();
	}
}
