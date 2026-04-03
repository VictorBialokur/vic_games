package br.com.victor.vicgamesconsole;

import br.com.victor.vicgamesconsole.client.ClientHttpConfiguration;
import br.com.victor.vicgamesconsole.main.Main;
import br.com.victor.vicgamesconsole.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VicGamesConsoleApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(VicGamesConsoleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main();
		main.executaPrograma();
	}
}
