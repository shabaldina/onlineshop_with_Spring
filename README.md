# Online shop with Springboot

REST API for simple onlineshop with Springboot.

The API is intended to support activities in the context of online commerce, e.g. create, modify or delete orders. It's possible to register customers or modify data of existing customers. Furthermore, the API supports the definition of products and the assignment of products to product categories.

No GUI availiable for time beeing.

### Domains:
- Customer
- Product
- ProductOrder
- ProductCategory 
- OrderItem

### REST API:
#### Calls for customers:
- GET on /api/customers 
- GET on /api/customers/{id}
- PUT on /api/customers
- PUT on /api/customers/{id}
- POST on /api/customers/{id}
- DELETE on /api/customers/{id}

#### Calls for products:
- GET on /api/products 
- GET on /api/products/{id}
- PUT on /api/products 
- PUT on /api/products/{id} 
- POST on /api/products/{id} 
- DELETE on /api/products/{id} 

#### Calls for product category (product-categories), product order (product-order), oder item (order-item) work same way
