package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repository.ClientRepository;
import com.mindhub.homebanking.repository.AccountRepository;
import com.mindhub.homebanking.repository.TransactionRepository;
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
	public CommandLineRunner initData(
			ClientRepository clientRepository,
			AccountRepository accountRepository,
			TransactionRepository transactionRepository
	) {
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

			// Crear transacciones para la cuenta 1 de Melba
			Transaction transaction1MelbaAccount1 = new Transaction(
					TransactionType.CREDIT,
					1000,
					"Depósito ",
					LocalDate.now(),
					account1
			);
			transactionRepository.save(transaction1MelbaAccount1);

			Transaction transaction2MelbaAccount1 = new Transaction(
					TransactionType.DEBIT,
					-200,
					"Compra en tienda",
					LocalDate.now().plusDays(1),
					account1
			);
			transactionRepository.save(transaction2MelbaAccount1);

			// Crear transacciones para la cuenta 2 de Melba
			Transaction transaction1MelbaAccount2 = new Transaction(
					TransactionType.CREDIT,
					2000,
					"Transferencia recibida",
					LocalDate.now(),
					account2
			);
			transactionRepository.save(transaction1MelbaAccount2);

			Transaction transaction2MelbaAccount2 = new Transaction(
					TransactionType.DEBIT,
					-500,
					"Pago de factura",
					LocalDate.now().plusDays(1),
					account2
			);
			transactionRepository.save(transaction2MelbaAccount2);















			// Crear otro cliente Marco
			Client marco = new Client("Marco", "Virinni", "estudiante@mindhub.com");
			clientRepository.save(marco);

			// Crear cuenta 1 para Marco
			Account account3 = new Account("345678", LocalDate.now(), 3000, marco);
			accountRepository.save(account3);

			// Crear cuenta 2 para Marco
			Account account4 = new Account("567890", LocalDate.now(), 4500, marco);
			accountRepository.save(account4);

			// Crear transacciones para la cuenta 1 de Marco
			Transaction transaction1MarcoAccount3 = new Transaction(
					TransactionType.CREDIT,
					800,
					"Depósito",
					LocalDate.now(),
					account3
			);
			transactionRepository.save(transaction1MarcoAccount3);

			Transaction transaction2MarcoAccount3 = new Transaction(
					TransactionType.DEBIT,
					-150,
					"Compra en línea",
					LocalDate.now().plusDays(1),
					account3
			);
			transactionRepository.save(transaction2MarcoAccount3);

			// Crear transacciones para la cuenta 2 de Marco
			Transaction transaction1MarcoAccount4 = new Transaction(
					TransactionType.CREDIT,
					1200,
					"Transferencia recibida",
					LocalDate.now(),
					account4
			);
			transactionRepository.save(transaction1MarcoAccount4);

			Transaction transaction2MarcoAccount4 = new Transaction(
					TransactionType.DEBIT,
					-300,
					"Pago de factura",
					LocalDate.now().plusDays(1),
					account4
			);
			transactionRepository.save(transaction2MarcoAccount4);
		};
	}
}
