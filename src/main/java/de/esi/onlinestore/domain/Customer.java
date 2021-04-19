package de.esi.onlinestore.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import de.esi.onlinestore.domain.enumeration.Gender;
import de.esi.onlinestore.exceptions.BadRequestException;
import org.springframework.http.ResponseEntity;

/**
 * Die Klasse beschreibt Kunden.
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "customer")
    private Set<ProductOrder> orders = new HashSet<>();

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Set<ProductOrder> getOrders() {
        return orders;
    }

    public void setOrders(Set<ProductOrder> orders) {
        this.orders = orders;
    }

    public Customer addOrder(ProductOrder productOrder){
        Customer customer = productOrder.getCustomer();
        Set<ProductOrder> customerOrders = customer.getOrders();
        if (customerOrders == null) {
            customerOrders = new HashSet<>();
        }
        customerOrders.add(productOrder);
        customer.setOrders(customerOrders);
        return customer;
    }

    public Customer removeOder(ProductOrder productOrder){
        Customer customer = productOrder.getCustomer();
        Set<ProductOrder> customerOrders = customer.getOrders();
        if (customerOrders == null) {
            customerOrders = new HashSet<>();
        }
        customerOrders.remove(productOrder);
        customer.setOrders(customerOrders);
        return customer;
    }

    @Override
    public String toString(){
        return "Customer{" +
                "id=" + getId() +
                ", first name=" + getFirstName() +
                ", last name=" + getLastName() +
                ", email=" + getEmail() +
                ", customer orders:" + getOrders() +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Customer))
            return false;
        Customer customer = (Customer) o;
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 41;
    }
}
