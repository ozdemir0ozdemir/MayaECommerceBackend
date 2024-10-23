package ozdemir0ozdemir.mayaecommercebackend.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ozdemir0ozdemir.mayaecommercebackend.dao.CustomerRepository;
import ozdemir0ozdemir.mayaecommercebackend.dao.ProductRepository;
import ozdemir0ozdemir.mayaecommercebackend.dto.Purchase;
import ozdemir0ozdemir.mayaecommercebackend.dto.PurchaseResponse;
import ozdemir0ozdemir.mayaecommercebackend.entity.Customer;
import ozdemir0ozdemir.mayaecommercebackend.entity.Order;
import ozdemir0ozdemir.mayaecommercebackend.entity.OrderItem;
import ozdemir0ozdemir.mayaecommercebackend.entity.Product;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CustomerRepository customerRepository;

    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        System.out.println("--------------------------------");
        System.out.println(purchase);
        System.out.println("********************************");

        // retrieve the order info from dto
        Order order = purchase.getOrder();

        // generate tracking number
        String orderTrackingNumber = UUID.randomUUID().toString();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::addOrderItem);

        // populate order with billingAddress and shippingAddress
        order.setBillignAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // order status
        order.setStatus("NEW_ORDER");

        // populate customer with order
        Customer customer = purchase.getCustomer();
        customer.addOrder(order);

        // save to database
        customerRepository.save(customer);

        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }
}
