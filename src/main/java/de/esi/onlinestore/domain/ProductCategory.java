package de.esi.onlinestore.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Die Klasse beschreibt Produktkategorien
 */
@Entity
@Table(name = "product_category")
public class ProductCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "productCategory")
    private Set<Product> products = new HashSet<>();

    //Getter and setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    //add product to category
    public ProductCategory addProduct(Product product){
        ProductCategory category = product.getProductCategory();
        Set<Product> products = category.getProducts();
        if (products == null) {
            products = new HashSet<>();
        }
        products.add(product);
        category.setProducts(products);
        return category;
    }

    //remove product from category
    public ProductCategory removeProduct(Product product){
        ProductCategory category = product.getProductCategory();
        Set<Product> products = category.getProducts();
        if (products == null) {
            products = new HashSet<>();
        }
        products.remove(product);
        category.setProducts(products);
        return category;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductCategory)) {
            return false;
        }
        return id != null && id.equals(((ProductCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", description='" + getDescription() + "'" +
                "}";
    }

}
