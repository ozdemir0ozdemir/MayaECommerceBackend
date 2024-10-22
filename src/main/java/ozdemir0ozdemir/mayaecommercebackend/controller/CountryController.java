package ozdemir0ozdemir.mayaecommercebackend.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ozdemir0ozdemir.mayaecommercebackend.dao.CountryRepository;
import ozdemir0ozdemir.mayaecommercebackend.entity.Country;

@RestController
@RequestMapping("/countries")
@CrossOrigin(origins = {"${frontend.url}"})
public record CountryController(CountryRepository repository) {

    @GetMapping
    ResponseEntity<Page<Country>> getAllCountries(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                  @RequestParam(name = "size", defaultValue = "20") Integer size) {

       return ResponseEntity.ok(this.repository.findAll(PageRequest.of(Math.max(page - 1, 0), size)));
    }

}
