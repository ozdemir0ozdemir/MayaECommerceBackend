package ozdemir0ozdemir.mayaecommercebackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ozdemir0ozdemir.mayaecommercebackend.entity.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
