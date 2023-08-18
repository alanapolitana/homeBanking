package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.models.ClientLoan;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
/*
@JsonIgnoreProperties("clientLoans")
*/
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private Set<Account> accounts = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private Set<ClientLoan> clientLoans = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "client")
    private Set<Card> cards = new HashSet<>();

    public Client() {
    }

    public Client(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /*GettersAndSetters*/
    public Long getId() {
        return id;
    }

/*    public void setId(Long id) {
        this.id = id;
    }*/

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }


    public Set<ClientLoan> getLoans() {
        return clientLoans;
    }

    public void setLoans(Set<ClientLoan> loans) {
        this.clientLoans = loans;
    }

    public Set<Loan> getClientLoans() {
        return clientLoans.stream().map(ClientLoan::getLoan).collect(Collectors.toSet());
    }
    public Set<Card> getCards() {
        return cards;
    }
    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }


/*Metodos de Acceso personalizados: están diseñados para realizar operaciones más específicas, como agregar elementos a colecciones o establecer relaciones.
Te permiten acceder y manipular el estado de la instancia de una manera controlada.
Son parte integral de la encapsulación en la programación orientada a objetos y ayudan a mantener un diseño coherente y controlado en tus clases.
*/

    public void addAccount(Account account) {
        accounts.add(account);
        account.setClient(this);
    }
    public void  addCard(Card card){
        card.setClient(this);
        cards.add(card);
    }
    public void addClientLoan(ClientLoan clientLoan) {
        clientLoans.add(clientLoan);
        clientLoan.setClient(this);
    }

}
