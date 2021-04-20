package de.esi.onlinestore.controller;

import de.esi.onlinestore.domain.ProductCategory;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.repository.ProductCategoryRepository;

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
 * REST Controller zur Verwaltung von Produktkategorien {@link ProductCategory}.
 */
@RestController
@RequestMapping("/api")
public class ProductCategoryController {

	private final Logger log = LoggerFactory.getLogger(ProductCategoryController.class);

	private final ProductCategoryRepository productCategoryRepository;

	public ProductCategoryController(ProductCategoryRepository productCategoryRepository) {

		this.productCategoryRepository = productCategoryRepository;
	}

	//Get list with all products categories
	@GetMapping("/product-categories")
	public ResponseEntity<List<ProductCategory>> getAllProductCategories() {
		log.info("REST request to get all categories of products");
		List<ProductCategory> result = productCategoryRepository.findAll();
		return ResponseEntity.ok()
				.body(result);
	}

	//Create new product category
	@PostMapping("/product-categories")
	public ResponseEntity<ProductCategory> createProductCategory(@Valid @RequestBody ProductCategory productCategory) throws URISyntaxException, BadRequestException {
		log.info("REST request to save category : {}", productCategory);
		if ( productCategory.getId() != null) {
			throw new BadRequestException("A new category cannot be added, ID exists");
		}
		ProductCategory result = productCategoryRepository.save(productCategory);
		return ResponseEntity.created(new URI("/api/product-categories/" + result.getId()))
				.body(result);
	}

	//Get product category by id
	@GetMapping("/product-categories/{id}")
	public ResponseEntity<ProductCategory> getProductCategory(@PathVariable Long id) throws ResourceNotFoundException {
		log.info("REST request to get category of product : {}", id);
		if(!productCategoryRepository.existsById(id)){
			throw new ResourceNotFoundException("id not found");
		}
		Optional<ProductCategory> productCategory = productCategoryRepository.findById(id);
		ProductCategory result = productCategory.get();
		return ResponseEntity.ok()
				.body(result);
	}

	//Overwrite product category
	@PutMapping("/product-categories")
	public ResponseEntity<ProductCategory> updateProductCategory(@Valid @RequestBody ProductCategory productCategory)
			throws BadRequestException, ResourceNotFoundException {
		log.info("REST request to update category : {}", productCategory);
		if (productCategory.getId() == null) {
			throw new BadRequestException("Invalid id");
		}
		if(!productCategoryRepository.existsById(productCategory.getId())){
			throw new ResourceNotFoundException("id not found");
		}
		ProductCategory result = productCategoryRepository.save(productCategory);
		return ResponseEntity.ok()
				.body(result);
	}

	//Overwrite product category by id
	@PutMapping("/product-categories/{id}")
	public ResponseEntity<ProductCategory> updateProductCategory(@PathVariable Long id, @Valid @RequestBody ProductCategory productCategory)
			throws BadRequestException, ResourceNotFoundException {
		log.info("REST request to update category : {}", productCategory);
		if (productCategory.getId() == null) {
			throw new BadRequestException("Invalid id");
		}
		if(!productCategoryRepository.existsById(id)){
			throw new ResourceNotFoundException("id not found");
		}
		ProductCategory result = productCategoryRepository.save(productCategory);
		return ResponseEntity.ok()
				.body(result);
	}


	//Delete product category
	@DeleteMapping("/product-categories/{id}")
	public ResponseEntity<Void> deleteProductCategory(@PathVariable Long id) throws ResourceNotFoundException {
		log.info("REST request to delete category : {}", id);
		if(!productCategoryRepository.existsById(id)){
			throw new ResourceNotFoundException("id not found");
		}
		productCategoryRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
