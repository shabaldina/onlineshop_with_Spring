package de.esi.onlinestore.domain;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import de.esi.onlinestore.domain.enumeration.OrderStatus;

/**
 * Die Klasse beschreibt Bestellungen
 */
@Entity
@Table(name = "product_order")
public class ProductOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "placed_date")
    private Instant placedDate;

    @NotNull
    @Column(name = "status")
    private OrderStatus status;

    @NotNull
    @Column(name="ordre_code")
    private String code;

    @OneToMany(mappedBy = "productOrder")
    private Set<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name="productOrder_id", referencedColumnName="id" )
    private Customer customer;

    //Getter and Setter for all variables in the class
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Instant getPlacedDate() {
        return placedDate;
    }

    public void setPlacedDate(Instant placedDate) {
        this.placedDate = placedDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    //add item to product order
    public ProductOrder addOrderItem(OrderItem orderItem){
        ProductOrder order = orderItem.getOrder();
        Set<OrderItem> items = order.getOrderItems();
        if (items == null) {
            items = new HashSet<>();
        }
        items.add(orderItem);
        order.setOrderItems(items);
        return order;
    }

    //remove item from product order
    public ProductOrder removeOrderItem(OrderItem orderItem){
        ProductOrder order = orderItem.getOrder();
        Set<OrderItem> items = order.getOrderItems();
        if (items == null) {
            items = new HashSet<>();
        }
        items.remove(orderItem);
        order.setOrderItems(items);
        return order;
    }

    @Override
    public String toString(){
        return "Product order {" +
                "id=" + getId() +
                ", date='" + getPlacedDate() + "'" +
                ", status='" + getStatus() + "'" +
                ", code= " + getCode() + "'" +
                ", customer=" + getCustomer() + "'" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ProductOrder))
            return false;
        ProductOrder that = (ProductOrder) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


