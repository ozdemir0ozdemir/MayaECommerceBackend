package ozdemir0ozdemir.mayaecommercebackend.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ozdemir0ozdemir.mayaecommercebackend.dao.ProductCategoryRepository;
import ozdemir0ozdemir.mayaecommercebackend.dao.ProductRepository;
import ozdemir0ozdemir.mayaecommercebackend.entity.Product;
import ozdemir0ozdemir.mayaecommercebackend.entity.ProductCategory;
import ozdemir0ozdemir.mayaecommercebackend.request.CreateProductRequest;
import ozdemir0ozdemir.mayaecommercebackend.request.UpdateProductRequest;

import java.util.List;
import java.util.Optional;


// Todo: implement a service
// Todo: create custom exceptions
// Todo: implement a global exception handler
@RestController
@RequestMapping("/products")
@CrossOrigin(origins = {"${frontend.url}"})
record ProductController(ProductRepository productRepository,
                         ProductCategoryRepository categoryRepository) {


    @GetMapping
    ResponseEntity<List<Product>> getAllProducts(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                 @RequestParam(name = "size", defaultValue = "20") Integer size) {
        List<Product> products = productRepository
                .findAll(PageRequest.of(page - 1, size)).toList();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{productCategoryId}")
    ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable(name = "productCategoryId") Long productCategoryId,
                                                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                          @RequestParam(name = "size", defaultValue = "20") Integer size) {

        List<Product> products = productRepository
                .findByProductCategoryId(productCategoryId, PageRequest.of(page - 1, size)).toList();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    ResponseEntity<List<Product>> getProductsByNameContaining(@RequestParam(name = "name") String name,
                                                              @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                              @RequestParam(name = "size", defaultValue = "20") Integer size) {
        List<Product> products = productRepository
                .findByNameContaining(name, PageRequest.of(page - 1, size)).toList();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        return productRepository.findById(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<Product> createNewProduct(@RequestBody CreateProductRequest request) {
        Product product = Product.of(request);

        ProductCategory productCategory = categoryRepository
                .findById(request.productCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Product category is not found!"));

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
    ResponseEntity<Product> updateExistingProductById(@PathVariable Long productId,
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
