package com.puntacana.discovery_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * TODO: Servidor de descubrimiento Eureka.
 *
 * - Centraliza el registro de microservicios.
 * - Permite que otros se descubran entre sí por nombre lógico.
 * - No guarda datos en MySQL, usa memoria.
 */
@EnableEurekaServer
@SpringBootApplication
public class DiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServiceApplication.class, args);
	}

}
