package ozdemir0ozdemir.mayaecommercebackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ozdemir0ozdemir.mayaecommercebackend.dao.ProductCategoryRepository;
import ozdemir0ozdemir.mayaecommercebackend.dao.ProductRepository;
import ozdemir0ozdemir.mayaecommercebackend.entity.Product;
import ozdemir0ozdemir.mayaecommercebackend.entity.ProductCategory;
import ozdemir0ozdemir.mayaecommercebackend.request.CreateProductRequest;
import ozdemir0ozdemir.mayaecommercebackend.request.UpdateProductRequest;

import java.util.Optional;


// Todo: implement a service
// Todo: create custom exceptions
// Todo: implement a global exception handler
@RestController
@RequestMapping("/products")
record ProductController(ProductRepository productRepository,
                         ProductCategoryRepository categoryRepository) {


    @GetMapping
    ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/{productId}")
    ResponseEntity<?> getProductById(@PathVariable Long productId) {
        return productRepository.findById(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<?> createNewProduct(@RequestBody CreateProductRequest request) {
        Product product = Product.of(request);

        ProductCategory productCategory = categoryRepository
                .getReferenceById(request.productCategoryId());

        product.setProductCategory(productCategory);

        Product savedProduct = productRepository.save(product);

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{productId}")
                        .buildAndExpand(savedProduct.getId())
                        .toUri())
                .body(savedProduct);
    }

    @PutMapping("/{productId}")
    ResponseEntity<?> updateExistingProduct(@PathVariable Long productId,
                                            @RequestBody UpdateProductRequest request) {

        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Product product = optionalProduct.get();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setUnitPrice(request.unitPrice());
        product.setImageUrl(request.imageUrl());
        product.setUnitsInStock(request.unitsInStock());

        Product savedProduct = productRepository.save(product);

        return ResponseEntity.ok(savedProduct);
    }

    @DeleteMapping("/{productId}")
    ResponseEntity<?> deleteExistingProductById(@PathVariable Long productId) {
        productRepository.deleteById(productId);

        return ResponseEntity.ok().build();
    }
}
