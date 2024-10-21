package ozdemir0ozdemir.mayaecommercebackend.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ozdemir0ozdemir.mayaecommercebackend.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("from Product p where p.productCategory.productCategoryId = :productCategoryId")
    Page<Product> findByProductCategoryId(Long productCategoryId, Pageable pageable);
}
