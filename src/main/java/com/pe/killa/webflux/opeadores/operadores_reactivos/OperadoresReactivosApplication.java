package com.pe.killa.webflux.opeadores.operadores_reactivos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class OperadoresReactivosApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(OperadoresReactivosApplication.class);
	private static List<String> nombres = Arrays.asList("Fredy", "Gianella", "Frank", "Alex", "Marck", "Aracely", "Blanca", "juan");

	public static void main(String[] args) {
		SpringApplication.run(OperadoresReactivosApplication.class, args);
	}

	public void crearMono(){
	Mono<Integer> monoNum =	Mono.just(10);
	//monoNum.subscribe(System.out::println);
		monoNum.subscribe(x -> System.out.println("mono: " + x));
		monoNum.subscribe(x -> System.out.println(x));
		monoNum.subscribe(System.out::println);
		monoNum.subscribe(x -> log.info("mono: " + x));
	}

	public void crearMonoFecha(){
	Mono<LocalDate> monoNum =	Mono.just(LocalDate.now());
	monoNum.subscribe(System.out::println);
	monoNum.subscribe(date -> log.info("La fecha actual es: " + date));
	}
	public void monoLista(){
	Mono<List<String>> monoNum = Mono.just(nombres);
	monoNum.subscribe(System.out::println);
	}

	public void crearFlux(){
		/* Método fromIterable funciona para Flux */
		Flux<String> fluxNombres = Flux.fromIterable(nombres);
		fluxNombres.subscribe(System.out::println);
	}

	public void fluxAmono(){
		/*Proceso inverso para transformar de Flux a Mono*/
		/*OJO al PIOJO -> DE MONO A FLUX NO SE PUEDE REALIZAR LA TRANSFORMACIÓN*/
		Flux<String> fluxNombres = Flux.fromIterable(nombres);
		fluxNombres.collectList().subscribe(System.out::println);
		/*Se emite todo el elemento de la lista como un solo bloque*/
	}

	public void op_doOnNext(){
		Flux<String> fluxNombres = Flux.fromIterable(nombres);
		/*Valida o emite el proceso por cada elemento*/
		fluxNombres.doOnNext(System.out::println)
				.subscribe(System.out::println);
	}

	public void op_map(){
		/*Operador map transforma el flujo de elemento*/
		Flux<String> fluxNombres = Flux.fromIterable(nombres);
		fluxNombres
				.doOnNext(System.out::println)
				.map(String::toUpperCase)
				.doOnNext(System.out::println)
				.map(x -> "Nombres: " + x)
				.subscribe(System.out::println);
	}

	public void op_flatMap(){
		/*Sirve para transformar elementos*/
		/*diferencia con map, el es sintaxis de RETORNO de variable*/
		/*tiene que ser explícito para indicar el tipo de retorno Ejm: Mono.just(xxxxx)*/
		Mono.just("Fredy")
				.flatMap(x -> Mono.just(x.length()))
				.subscribe(System.out::println);

//		Mono.just("Fredy")
//				.flatMap(x -> Mono.just(x.length()))
//				.subscribe(System.out::println);

		Mono.just("Fredy")
				.flatMap(x -> Mono.just(10))
				.subscribe(f -> log.info(f.toString()));

	}

	public void op_range(){

		/*Operador de rango*/
		Flux<Integer> fluxRango = Flux.range(0, 10);
		fluxRango
				.doOnNext(y -> log.info("Antes: " + y))
			.map(x -> x +1)
				.subscribe(x -> log.info("Después: " + x));
	}

	public void op_delayElements() throws InterruptedException {
		Flux.range(0, 10)
				.delayElements(Duration.ofSeconds(1))
				.doOnNext(System.out::println)
				.subscribe();

		Thread.sleep(6000);
	}

	public void op_zipWith(){
		List<String> platos =
				Arrays.asList("Ceviche", "Ají de gallina", "Arroz con pollo", "Causa",
						"Tallarines Rojos", "Estofoado de polllo", "Lomo saltado");
		Flux<String> fluxNombres = Flux.fromIterable(nombres);
		Flux<String> fxPlatos = Flux.fromIterable(platos);

		fluxNombres
				.zipWith(fxPlatos, (n, p) -> String.format("Flux1: %s, Flux2: %s", n, p))
				.map(x -> x.toString())
				.subscribe(x -> log.info(x));
	}

	public void op_merge(){
		List<String> platos =
				Arrays.asList("Ceviche", "Ají de gallina", "Arroz con pollo", "Causa",
						"Tallarines Rojos", "Estofoado de polllo", "Lomo saltado");
		Flux<String> fluxNombres = Flux.fromIterable(nombres);
		Flux<String> fxPlatos = Flux.fromIterable(platos);

		Flux.merge(fluxNombres, fxPlatos)
				.map(x -> "Elementos: " + x)
				.subscribe(System.out::println);

		/*Flux<String> fxCombinado = fluxNombres.mergeWith(fxPlatos);
		fxCombinado.subscribe(System.out::println);*/
	}

	public void op_filter(){
		Flux<String> fluxNombres = Flux.fromIterable(nombres);
	fluxNombres.filter(x -> x.startsWith("F")).subscribe(System.out::println);
	}

	public void op_takeLast(){
		/*Operación que obtiene x cantidad de los últimos elementos de un flujo de datos*/
		Flux<String> fluxNombres = Flux.fromIterable(nombres);
		fluxNombres.takeLast(3).subscribe(System.out::println);
	}

	public void op_take(){
		/*Operación que obtiene x cantidad de los primeros elementos de un flujo de datos*/
		Flux<String> fluxNombres = Flux.fromIterable(nombres);
		fluxNombres.take(2).subscribe(System.out::println);
	}

	public void op_defaultIfEmpty(){
		/*Devuelve un mensaje por defecto si la Lista no contiene elementos*/
		nombres = new ArrayList<>();
		Flux<String> fluxNombres = Flux.fromIterable(nombres);
		fluxNombres.defaultIfEmpty("No hay elementos en la consulta")
				.subscribe(log::info);
	}

	public void op_onErrorReturn(){
		Flux<String> fluxNombres = Flux.fromIterable(nombres);

		fluxNombres.doOnNext(x ->{
			throw new ArithmeticException("Error provocado");
		})
				/*Error capturado y tratado como corresponde*/
				.onErrorMap(x -> new Exception("Error capturado"))
				/*Captura de error convencional*/
				.onErrorReturn("Ocurrió un error!")
				.subscribe(log::info);
	}

	public void op_retry(){
		Flux<String> fluxNombres = Flux.fromIterable(nombres);

		fluxNombres.doOnNext(n -> {
			log.info("Intentando....");
			throw new ArithmeticException("Error provocado");
		})
				.retry(3)
				.onErrorReturn("Error!")
				.subscribe(log::info);
	}

	@Override
	public void run(String... args) throws Exception {
		//Insert method to evaluate
		//op_retry();
	}
}
