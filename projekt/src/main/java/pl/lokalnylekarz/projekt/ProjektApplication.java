package pl.lokalnylekarz.projekt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EntityScan("pl.lokalnylekarz.projekt.model")
//@ComponentScan(basePackages = {"pl.lokalnylekarz.projekt.database.seeder"})
//@EnableJpaRepositories("pl.lokalnylekarz.projekt.repository")
public class ProjektApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjektApplication.class, args);
	}

}
