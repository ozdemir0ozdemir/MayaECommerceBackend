package ozdemir0ozdemir.mayaecommercebackend.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ozdemir0ozdemir.mayaecommercebackend.entity.State;

import java.util.List;

public interface StateRepository extends JpaRepository<State, Integer> {

    Page<State> findByCountryCodeIgnoringCase(String countryCode, PageRequest pageRequest);
}
