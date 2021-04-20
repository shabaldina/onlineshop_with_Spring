package de.esi.onlinestore.controller;

import de.esi.onlinestore.domain.Customer;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.repository.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller zur Verwaltung von Kunden {@link Customer}.
 */
@RestController
@RequestMapping("/api")
public class CustomerController {

	private final Logger log = LoggerFactory.getLogger(CustomerController.class);

	private final CustomerRepository customerRepository;

	public CustomerController(CustomerRepository customerRepository){

		this.customerRepository = customerRepository;
	}

	//Get list of all customers
	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAllCustomers(){
		log.info("REST request to get all customers");
		List<Customer> result = customerRepository.findAll();
		return ResponseEntity.ok().body(result);
	}

	//Create new customer
	@PostMapping("/customers")
	public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) throws BadRequestException, URISyntaxException {
		log.info("REST request to create customer : {}", customer);
		if (customer.getId() != null){
			throw new BadRequestException("New customer can not be added - id exists");
		}
		Customer result = customerRepository.save(customer);
		return ResponseEntity.created(new URI("/api/customers/" + result.getId())).body(result);
	}

	//Get customer by id
	@GetMapping("/customers/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable Long id) throws ResourceNotFoundException {
		log.info("REST request to get customer : {}", id);
		if(!customerRepository.existsById(id)){
			throw new ResourceNotFoundException("id not found");
		}
		Optional<Customer> customer = customerRepository.findById(id);
		Customer result = customer.get();
		return ResponseEntity.ok().body(result);

	}

	//Overwrite customer
	@PutMapping("/customers")
	public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer)
			throws BadRequestException, ResourceNotFoundException {
		log.info("REST request to update customer : {}", customer);
		if (customer.getId() == null) {
			throw new BadRequestException("Invalid id");
		}
		if(!customerRepository.existsById(customer.getId())){
			throw new ResourceNotFoundException("id not found");
		}
		Customer result = customerRepository.save(customer);
		return ResponseEntity.ok().body(result);
	}

	//Overwrite customer by id
	@PutMapping("/customers/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer)
			throws BadRequestException, ResourceNotFoundException {
		log.info("REST request to update customer : {}", customer);
		if (customer.getId() == null) {
			throw new BadRequestException("Invalid id");
		}
		if(!customerRepository.existsById(id)){
			throw new ResourceNotFoundException("id not found");
		}
		Customer result = customerRepository.save(customer);
		return ResponseEntity.ok().body(result);
	}

	//Delete customer by id
	@DeleteMapping("/customers/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) throws ResourceNotFoundException {
		log.info("REST request to delete customer : {}", id);
		if(!customerRepository.existsById(id)){
			throw new ResourceNotFoundException("id not found");
		}
		customerRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}


