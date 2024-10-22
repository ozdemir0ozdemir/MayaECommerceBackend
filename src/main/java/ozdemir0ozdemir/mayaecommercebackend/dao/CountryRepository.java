package ozdemir0ozdemir.mayaecommercebackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ozdemir0ozdemir.mayaecommercebackend.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
