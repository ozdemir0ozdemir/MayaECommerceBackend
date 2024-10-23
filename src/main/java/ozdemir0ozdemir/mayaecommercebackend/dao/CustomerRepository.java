package ozdemir0ozdemir.mayaecommercebackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ozdemir0ozdemir.mayaecommercebackend.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {


}
