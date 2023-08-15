package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(
			ClientRepository clientRepository,
			AccountRepository accountRepository,
			TransactionRepository transactionRepository,
			LoanRepository loanRepository,
			ClientLoanRepository clientLoanRepository
	) {
		return args -> {

			// Crear cliente Melba
			Client melba = new Client("Melba", "Smith", "melba@mindhub.com");
			clientRepository.save(melba);

			// Crear cuenta 1 para Melba
			Account account1 = new Account("VIN001", LocalDate.now(), 5000.0, melba);
			accountRepository.save(account1);

			// Crear cuenta 2 para Melba
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500.0, melba);
			accountRepository.save(account2);

			// Crear transacciones para la cuenta 1 de Melba
			Transaction transaction1MelbaAccount1 = new Transaction(
					TransactionType.CREDIT,
					1000,
					"Depósito",
					LocalDateTime.now(),
					account1
			);
			transactionRepository.save(transaction1MelbaAccount1);

			Transaction transaction2MelbaAccount1 = new Transaction(
					TransactionType.DEBIT,
					-200,
					"Compra en tienda",
					LocalDateTime.now().plusDays(1),
					account1
			);
			transactionRepository.save(transaction2MelbaAccount1);

			// Crear transacciones para la cuenta 2 de Melba
			Transaction transaction1MelbaAccount2 = new Transaction(
					TransactionType.CREDIT,
					2000,
					"Transferencia recibida",
					LocalDateTime.now(),
					account2
			);
			transactionRepository.save(transaction1MelbaAccount2);

			Transaction transaction2MelbaAccount2 = new Transaction(
					TransactionType.DEBIT,
					-500,
					"Pago de factura",
					LocalDateTime.now().plusDays(1),
					account2
			);
			transactionRepository.save(transaction2MelbaAccount2);





			// Crear préstamos de prueba con Set
			Loan hipotecario = new Loan("Hipotecario", 500000.0, Set.of(12, 24, 36, 48, 60));
			loanRepository.save(hipotecario);

			Loan personal = new Loan("Personal", 100000.0, Set.of(6, 12, 24));
			loanRepository.save(personal);

			Loan automotriz = new Loan("Automotriz", 300000.0, Set.of(6, 12, 24, 36));
			loanRepository.save(automotriz);




			// Crear ClientLoan para Melba
			ClientLoan melbaHipotecario = new ClientLoan(400000.0, 60, melba, hipotecario);
			clientLoanRepository.save(melbaHipotecario);

			ClientLoan melbaPersonal = new ClientLoan(50000.0, 12, melba, personal);
			clientLoanRepository.save(melbaPersonal);






			// Crear otro cliente Marco
			Client marco = new Client("Marco", "Virinni", "estudiante@mindhub.com");
			clientRepository.save(marco);

			// Crear cuenta 1 para Marco
			Account account3 = new Account("VIN003", LocalDate.now(), 3000.0, marco);
			accountRepository.save(account3);

			// Crear cuenta 2 para Marco
			Account account4 = new Account("VIN004", LocalDate.now(), 4500.0, marco);
			accountRepository.save(account4);




			// Crear transacciones para la cuenta 1 de Marco
			Transaction transaction1MarcoAccount3 = new Transaction(
					TransactionType.CREDIT,
					800,
					"Depósito",
					LocalDateTime.now(),
					account3
			);
			transactionRepository.save(transaction1MarcoAccount3);

			Transaction transaction2MarcoAccount3 = new Transaction(
					TransactionType.DEBIT,
					-150,
					"Compra en línea",
					LocalDateTime.now().plusDays(1),
					account3
			);
			transactionRepository.save(transaction2MarcoAccount3);

			// Crear transacciones para la cuenta 2 de Marco
			Transaction transaction1MarcoAccount4 = new Transaction(
					TransactionType.CREDIT,
					1200,
					"Transferencia recibida",
					LocalDateTime.now(),
					account4
			);
			transactionRepository.save(transaction1MarcoAccount4);

			Transaction transaction2MarcoAccount4 = new Transaction(
					TransactionType.DEBIT,
					-300,
					"Pago de factura",
					LocalDateTime.now().plusDays(1),
					account4
			);
			transactionRepository.save(transaction2MarcoAccount4);



			// Crear ClientLoan para Marco
			ClientLoan marcoPersonal = new ClientLoan(100000.0, 24, marco, personal);
			clientLoanRepository.save(marcoPersonal);

			ClientLoan marcoAutomotriz = new ClientLoan(200000.0, 36, marco, automotriz);
			clientLoanRepository.save(marcoAutomotriz);

		};
	}
}
