package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repository.ClientRepository;
import com.mindhub.homebanking.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository) {
		return args -> {

			// Crear cliente Melba
			Client melba = new Client("Melba", "Smith", "melba@mindhub.com");
			clientRepository.save(melba);

			// Crear cuenta 1 para Melba
			Account account1 = new Account("VIN001", LocalDate.now(), 5000, melba);
			accountRepository.save(account1);

			// Crear cuenta 2 para Melba
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500, melba);
			accountRepository.save(account2);




			// Crear otros clientes y cuentas aquí si es necesarioo
			Client otroCliente = new Client("Marco", "Virinni", "estudiante@mindhub.com");
			clientRepository.save(otroCliente);

			// Crear cuentas para el otro cliente
			Account otroAccount1 = new Account("345678", LocalDate.now(), 3000, otroCliente);
			accountRepository.save(otroAccount1);

			Account otroAccount2 = new Account("567890", LocalDate.now(), 4500, otroCliente);
			accountRepository.save(otroAccount2);
		};
	}
}
