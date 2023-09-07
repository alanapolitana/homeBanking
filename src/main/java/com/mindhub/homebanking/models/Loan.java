package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String name;
    private Double maxAmount;


    @ElementCollection
    @Column(name="payment")
    private List<Integer> payments = new ArrayList<>();


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "loan")
    private Set<ClientLoan> clientLoans = new HashSet<>();

    public Loan() {
    }

    public Loan(String name, Double maxAmount, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    @JsonIgnore
    public Set<ClientLoan> getClients() {
        return clientLoans;
    }

}


  /*  El método setClients() no se crea en este caso porque estamos manejando una relación uno a muchos entre la entidad Loan y la entidad ClientLoan. En este tipo de relación, el control de la asignación de los clientes a los préstamos se realiza a través de la creación de objetos ClientLoan, que actúan como registros intermedios que almacenan la información adicional como el monto y las cuotas.

        Cuando se trata de relaciones uno a muchos, como en este caso, la creación y actualización de los registros relacionados generalmente se realiza a través de los objetos que representan la entidad intermedia (ClientLoan en este caso). El objeto Loan simplemente tiene una colección de ClientLoan que representa los registros intermedios que asocian a los clientes con el préstamo.
        Cuando se trata de relaciones uno a muchos, como en este caso, la creación y actualización de los registros relacionados generalmente se realiza a través de los objetos que representan la entidad intermedia (ClientLoan en este caso). El objeto Loan simplemente tiene una colección de ClientLoan que representa los registros intermedios que asocian a los clientes con el préstamo.

        Por lo tanto, no se necesita un método setClients() en la clase Loan, ya que la asignación de clientes se maneja a través de los objetos ClientLoan y no directamente desde la entidad Loan.

        En resumen, en una relación uno a muchos, el control de la asignación y desasignación de registros relacionados se hace a través de los objetos de la entidad intermedia, y no es necesario proporcionar un método set directamente en la entidad principal para manejarlos.*/