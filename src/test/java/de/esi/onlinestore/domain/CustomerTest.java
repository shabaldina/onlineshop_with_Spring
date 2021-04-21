package de.esi.onlinestore.domain;
import static de.esi.onlinestore.domain.enumeration.OrderStatus.CANCELLED;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jdk.jfr.Description;

class CustomerTest {

	Customer customer;
	ProductOrder productOrder;

	@BeforeEach
	void setUp(){
		customer = new Customer();
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customer.setId(12l);
		customer.setEmail("john.doe@example.com");

		productOrder = new ProductOrder();
		productOrder.setCustomer(customer);
		productOrder.setStatus(CANCELLED);
		productOrder.setId(132l);

	}


	@Test
	@DisplayName("Customer add order")
	void addOrder() {
		customer.addOrder(productOrder);
		System.out.println("Orders: " + customer.getOrders());

		Assertions.assertEquals(12L, customer.getId());
		Assertions.assertEquals("John", customer.getFirstName());
		Assertions.assertFalse(customer.getOrders().isEmpty());
		Assertions.assertEquals(1, customer.getOrders().size());
		Assertions.assertEquals(132L, productOrder.getId());

	}

	@Test
	@DisplayName("Customer remove order")
	void removeOder() {
		customer.addOrder(productOrder);
		customer.removeOder(productOrder);
		System.out.println("Orders: " + customer.getOrders());

		Assertions.assertTrue(customer.getOrders().isEmpty());

	}
}