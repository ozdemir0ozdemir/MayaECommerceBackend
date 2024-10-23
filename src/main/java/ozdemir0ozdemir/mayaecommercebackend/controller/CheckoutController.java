package ozdemir0ozdemir.mayaecommercebackend.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ozdemir0ozdemir.mayaecommercebackend.dto.Purchase;
import ozdemir0ozdemir.mayaecommercebackend.dto.PurchaseResponse;
import ozdemir0ozdemir.mayaecommercebackend.service.CheckoutService;

@RestController
@RequestMapping("/checkout")
@CrossOrigin(origins = {"${frontend.url}"})
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {
        PurchaseResponse response = this.checkoutService.placeOrder(purchase);
        return response;
    }
}
