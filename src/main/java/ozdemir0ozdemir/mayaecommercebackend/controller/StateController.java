package ozdemir0ozdemir.mayaecommercebackend.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ozdemir0ozdemir.mayaecommercebackend.dao.StateRepository;
import ozdemir0ozdemir.mayaecommercebackend.entity.State;

@RestController
@CrossOrigin(origins = {"${frontend.url}"})
public record StateController(StateRepository repository) {

    @GetMapping("/states")
    ResponseEntity<Page<State>> getAllStates(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                             @RequestParam(name = "size", defaultValue = "20") Integer size) {
        return ResponseEntity.ok(this.repository.findAll(PageRequest.of(Math.max(page - 1, 0), size)));
    }

    @GetMapping("/countries/{countryCode}/states")
    ResponseEntity<Page<State>> getAllStates(@PathVariable String countryCode,
                                             @RequestParam(name = "page", defaultValue = "1") Integer page,
                                             @RequestParam(name = "size", defaultValue = "20") Integer size) {

        return ResponseEntity.ok(this.repository.findByCountryCodeIgnoringCase(countryCode, PageRequest.of(Math.max(page - 1, 0), size)));
    }
}
