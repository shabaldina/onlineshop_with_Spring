package de.esi.onlinestore.controller;

import de.esi.onlinestore.domain.ProductOrder;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.repository.ProductOrderRepository;

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
 * REST Controller zur Handhabung von Bestellungen {@link ProductOrder}.
 */
@RestController
@RequestMapping("/api")
public class ProductOrderController {

	private final Logger log = LoggerFactory.getLogger(ProductOrderController.class);

	private final ProductOrderRepository productOrderRepository;

	public ProductOrderController(ProductOrderRepository productOrderService) {

		this.productOrderRepository = productOrderService;
	}


	//Get list of all product orders
	@GetMapping("/orders")
	public ResponseEntity<List<ProductOrder>> getAllProductOrders() {
		log.info("REST request to get all orders");
		List<ProductOrder> result = productOrderRepository.findAll();
		return ResponseEntity.ok().body(result);
	}

	//Create new order
	@PostMapping("/orders")
	public ResponseEntity<ProductOrder> createProductOrder(@Valid @RequestBody ProductOrder productOrder) throws URISyntaxException, BadRequestException {
		log.info("REST request to save order : {}", productOrder);
		if ( productOrder.getId() != null) {
			throw new BadRequestException("A new order cannot be added, ID exists");
		}
		ProductOrder result = productOrderRepository.save(productOrder);
		return ResponseEntity.created(new URI("/api/orders/" + result.getId()))
				.body(result);
	}

	//Get order by id
	@GetMapping("/orders/{id}")
	public ResponseEntity<ProductOrder> getProductOrder(@PathVariable Long id) throws ResourceNotFoundException {
		log.info("REST request to get order : {}", id);
		if(!productOrderRepository.existsById(id)){
			throw new ResourceNotFoundException("id not found");
		}
		Optional<ProductOrder> order = productOrderRepository.findById(id);
		ProductOrder result = order.get();
		return ResponseEntity.ok()
				.body(result);
	}

	//Overwrite order
	@PutMapping("/orders")
	public ResponseEntity<ProductOrder> updateProductOrder(@Valid @RequestBody ProductOrder productOrder)
			throws BadRequestException, ResourceNotFoundException {
		log.info("REST request to update order : {}", productOrder);
		if (productOrder.getId() == null) {
			throw new BadRequestException("Invalid id");
		}
		if(!productOrderRepository.existsById(productOrder.getId())){
			throw new ResourceNotFoundException("id not found");
		}
		ProductOrder result = productOrderRepository.save(productOrder);
		return ResponseEntity.ok()
				.body(result);
	}

	//Overwrite order by id
	@PutMapping("/orders/{id}")
	public ResponseEntity<ProductOrder> updateProductOrder(@PathVariable Long id, @Valid @RequestBody ProductOrder productOrder)
			throws BadRequestException, ResourceNotFoundException {
		log.info("REST request to update order : {}", productOrder);
		if (productOrder.getId() == null) {
			throw new BadRequestException("Invalid id");
		}
		if(!productOrderRepository.existsById(id)){
			throw new ResourceNotFoundException("id not found");
		}
		ProductOrder result = productOrderRepository.save(productOrder);
		return ResponseEntity.ok()
				.body(result);
	}

	//Delete order by id
	@DeleteMapping("/orders/{id}")
	public ResponseEntity<Void> deleteProductOrder(@PathVariable Long id) throws ResourceNotFoundException {
		log.info("REST request to delete category : {}", id);
		if(!productOrderRepository.existsById(id)){
			throw new ResourceNotFoundException("id not found");
		}
		productOrderRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}


