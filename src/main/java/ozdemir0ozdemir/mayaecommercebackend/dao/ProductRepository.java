package ozdemir0ozdemir.mayaecommercebackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ozdemir0ozdemir.mayaecommercebackend.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
