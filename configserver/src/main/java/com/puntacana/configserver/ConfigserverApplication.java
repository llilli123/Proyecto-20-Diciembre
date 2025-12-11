package com.puntacana.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * TODO: Servicio de Configuración centralizada para todos los microservicios.
 *
 * - Expone la configuración a través de HTTP.
 * - Apunta a un repositorio (nativo o Git) donde viven los yml de cada servicio.
 * - No tiene lógica de negocio ni base de datos.
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigserverApplication.class, args);
	}

}
