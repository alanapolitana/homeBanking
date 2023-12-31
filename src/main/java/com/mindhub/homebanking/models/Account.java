package com.mindhub.homebanking.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private Long id;
    private String number;
    private LocalDate creationDate;

    private Double balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    // Constructor sin parámetros requerido por JPA
    public Account() {
    }

    // Constructor principal para creación de cuenta
    public Account(String number, LocalDate now, Double balance, Client client) {
        this.number = number;
        this.creationDate = LocalDate.now();
        this.balance = balance;
        this.client = client;
    }

    public Long getId() {
        return id;
    }
    public String getNumber() {
        return number;
    }
    public LocalDate getCreationDate() {return creationDate;}
    public Double getBalance() {
        return balance;
    }

    public Client getClient() {
        return client;
    }
    public Set<Transaction> getTransactions() {
        return transactions;
    }





    public void setNumber(String number) {
        this.number = number;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setAccount(this);
    }
}
