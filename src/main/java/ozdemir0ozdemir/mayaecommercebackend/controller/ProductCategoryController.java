package ozdemir0ozdemir.mayaecommercebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ozdemir0ozdemir.mayaecommercebackend.dao.ProductCategoryRepository;
import ozdemir0ozdemir.mayaecommercebackend.entity.ProductCategory;
import ozdemir0ozdemir.mayaecommercebackend.request.CreateProductCategoryRequest;
import ozdemir0ozdemir.mayaecommercebackend.request.UpdateProductCategoryRequest;

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
        return ResponseEntity.ok(categoryRepository.save(ProductCategory.of(request)));
    }

    @PutMapping("/{productCategoryId}")
    ResponseEntity<?> updateProductCategory(@PathVariable Long productCategoryId,
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
