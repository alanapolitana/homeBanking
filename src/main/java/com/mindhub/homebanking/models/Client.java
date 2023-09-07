package com.mindhub.homebanking.models;

import com.mindhub.homebanking.dtos.ClientDTO;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity

public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private String password;
    @Enumerated(EnumType.STRING)
    private RoleType role;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private Set<Account> accounts = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private Set<ClientLoan> clientLoans = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "client")
    private Set<Card> cards = new HashSet<>();

    public Client() {
    }
    public Client(String firstName, String lastName, String email, String password,RoleType role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role=role;
    }


    /*GettersAndSetters*/
    public Long getId() {
        return id;
    }

/*    public void setId(Long id) {
        this.id = id;
    }*/

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    //NAME GTY
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    //LASTNAME GYS
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    //EMAIL GYS
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //ACCOUNTS GYS
    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    //LOANS/ CLIENT-LOANS GYS

    public Set<ClientLoan> getLoans() {
        return clientLoans;
    }

    public void setLoans(Set<ClientLoan> loans) {
        this.clientLoans = loans;
    }

    public Set<Loan> getClientLoans() {
        return clientLoans.stream().map(ClientLoan::getLoan).collect(Collectors.toSet());
    }

    //CARDS GYS
    public Set<Card> getCards() {
        return cards;
    }
    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    //PASWORDS GYS

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
