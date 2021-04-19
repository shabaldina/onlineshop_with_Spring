package de.esi.onlinestore.controller;

import de.esi.onlinestore.domain.Product;
import de.esi.onlinestore.domain.ProductCategory;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.repository.ProductCategoryRepository;
import de.esi.onlinestore.repository.ProductRepository;


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
 * REST Controller zur Verwaltung von Produkten {@link Product}.
 */
@RestController
@RequestMapping("/api")
public class ProductController {

	private final Logger log = LoggerFactory.getLogger(ProductController.class);

	private final ProductRepository productRepository;

	public ProductController(ProductRepository productService) {
		this.productRepository = productService;
	}

	//Get list of all products
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts() {
		log.info("REST request to get all Products");
		List<Product> result = productRepository.findAll();
		return ResponseEntity.ok()
				.body(result);
	}

	//create new product
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) throws URISyntaxException, BadRequestException {
		log.info("REST request to save Product : {}", product);
		if ( product.getId() == null) {
			throw new BadRequestException("A new product cannot be added, ID null");
		}
		Product result = productRepository.save(product);
		return ResponseEntity.created(new URI("/api/products/" + result.getId()))
				.body(result);
	}

	//get product by id
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable Long id) {
		log.info("REST request to get Product : {}", id);
		Optional<Product> product = productRepository.findById(id);
		Product result = product.get();
		return ResponseEntity.ok()
				.body(result);
	}

	//overwrite product by id
	@PutMapping("/products")
	public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product) throws URISyntaxException, BadRequestException {
		log.info("REST request to update Product : {}", product);
		if (product.getId() == null) {
			throw new BadRequestException("Invalid id");
		}
		Product result = productRepository.save(product);
		return ResponseEntity.ok()
				.body(result);
	}

	//delete product by id
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		log.info("REST request to delete Product : {}", id);
		productRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}








}


