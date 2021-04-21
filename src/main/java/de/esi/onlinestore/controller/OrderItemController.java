package de.esi.onlinestore.controller;

import de.esi.onlinestore.domain.OrderItem;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.repository.OrderItemRepository;


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
 * REST Controller zur von Bestellpositionen {@link OrderItem}.
 */
@RestController
@RequestMapping("/api")
public class OrderItemController {
	private final Logger log = LoggerFactory.getLogger(OrderItemController.class);

	private final OrderItemRepository itemRepository;

	public OrderItemController(OrderItemRepository itemService) {

		this.itemRepository = itemService;
	}

	//Get all order items
	@GetMapping("/order-items")
	public ResponseEntity<List<OrderItem>> getAllOrderItems() {
		log.info("REST request to get all order items");
		List<OrderItem> result = itemRepository.findAll();
		return ResponseEntity.ok()
				.body(result);
	}

	//Create new order item
	@PostMapping("/order-items")
	public ResponseEntity<OrderItem> createOrderItem(@Valid @RequestBody OrderItem item) throws URISyntaxException, BadRequestException {
		log.info("REST request to save order item : {}", item);
		if ( item.getId() != null) {
			throw new BadRequestException("A new item cannot be added, ID exists");
		}
		OrderItem result = itemRepository.save(item);
		return ResponseEntity.created(new URI("/api/order-items/" + result.getId()))
				.body(result);
	}

	//Get order item by id
	@GetMapping("/order-items/{id}")
	public ResponseEntity<OrderItem> getOrderItem(@PathVariable Long id) throws ResourceNotFoundException {
		log.info("REST request to get ordrer item : {}", id);
		if(!itemRepository.existsById(id)){
			throw new ResourceNotFoundException("id not found");
		}
		Optional<OrderItem> item = itemRepository.findById(id);
		OrderItem result = item.get();
		return ResponseEntity.ok()
				.body(result);
	}

	//Overwrite order item
	@PutMapping("/order-items")
	public ResponseEntity<OrderItem> updateOrderItem(@Valid @RequestBody OrderItem item) throws BadRequestException, ResourceNotFoundException {
		log.info("REST request to update item : {}", item);
		if (item.getId() == null) {
			throw new BadRequestException("Invalid id");
		}
		if(!itemRepository.existsById(item.getId())){
			throw new ResourceNotFoundException("id not found");
		}
		OrderItem result = itemRepository.save(item);
		return ResponseEntity.ok().body(result);

	}

	//Overwrite order item by id
	@PutMapping("/order-items/{id}")
	public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id, @Valid @RequestBody OrderItem item) throws BadRequestException, ResourceNotFoundException {
		log.info("REST request to update item : {}", item);
		if (item.getId() == null) {
			throw new BadRequestException("Invalid id");
		}
		if(!itemRepository.existsById(id)){
			throw new ResourceNotFoundException("id not found");
		}
		OrderItem result = itemRepository.save(item);
		return ResponseEntity.ok()
				.body(result);
	}

	//Delete order item by id
	@DeleteMapping("/order-items/{id}")
	public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) throws ResourceNotFoundException {
		log.info("REST request to delete order item : {}", id);
		if(!itemRepository.existsById(id)){
			throw new ResourceNotFoundException("id not found");
		}
		itemRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
