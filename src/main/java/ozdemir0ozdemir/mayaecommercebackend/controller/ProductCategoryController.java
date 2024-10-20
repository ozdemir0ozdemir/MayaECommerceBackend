package ozdemir0ozdemir.mayaecommercebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ozdemir0ozdemir.mayaecommercebackend.dao.ProductCategoryRepository;
import ozdemir0ozdemir.mayaecommercebackend.entity.ProductCategory;
import ozdemir0ozdemir.mayaecommercebackend.request.CreateProductCategoryRequest;
import ozdemir0ozdemir.mayaecommercebackend.request.UpdateProductCategoryRequest;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/products/categories")
record ProductCategoryController(ProductCategoryRepository categoryRepository) {
    
    @GetMapping
    ResponseEntity<?> getAllProductCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }
    
    @GetMapping("/{productCategoryId}")
    ResponseEntity<?> getProductCategoryById(@PathVariable Long productCategoryId) {
        return categoryRepository.findById(productCategoryId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<?> createNewProductCategory(@RequestBody CreateProductCategoryRequest request) {

        ProductCategory savedCategory = categoryRepository.save(ProductCategory.of(request));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{productCategoryId}")
                .buildAndExpand(savedCategory.getProductCategoryId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(savedCategory);
    }

    @PutMapping("/{productCategoryId}")
    ResponseEntity<?> updateProductCategoryById(@PathVariable Long productCategoryId,
                                            @RequestBody UpdateProductCategoryRequest request) {

        Optional<ProductCategory> optionalProductCategory =
                categoryRepository.findById(productCategoryId);

        if(optionalProductCategory.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        ProductCategory pc = optionalProductCategory.get();
        pc.setCategoryName(request.name());

        return ResponseEntity.ok(categoryRepository.save(pc));
    }

    @DeleteMapping("/{productCategoryId}")
    ResponseEntity<?> deleteProductCategoryById(@PathVariable Long productCategoryId) {
        categoryRepository.deleteById(productCategoryId);

        return ResponseEntity.ok().build();
    }
}
