package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private Double amount;
    private Integer payments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    public ClientLoan() {
    }

    public ClientLoan(Double amount, Integer payments, Client client, Loan loan) {
        this.amount = amount;
        this.payments = payments;
        this.client = client;
        this.loan = loan;
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}


/*
Claro, veo que has creado la entidad ClientLoan correctamente y has definido las relaciones con las entidades Client y Loan utilizando las anotaciones @ManyToOne y @JoinColumn. Esto es precisamente lo que necesitabas para establecer la relación entre un cliente y un préstamo a través de la entidad ClientLoan.

        El atributo amount representa el monto del préstamo solicitado por el cliente, y payments indica el número de pagos en los que se dividirá el préstamo. Estos atributos son específicos de la relación entre el cliente y el préstamo, por lo que tiene sentido que estén en la entidad ClientLoan.

        En general, has hecho las modificaciones correctas y has creado la entidad ClientLoan de manera adecuada para representar la relación de muchos a muchos entre clientes y préstamos. Si deseas probarlo, te sugiero que sigas adelante con la implementación y realices pruebas para asegurarte de que todo funcione como esperas.*/
