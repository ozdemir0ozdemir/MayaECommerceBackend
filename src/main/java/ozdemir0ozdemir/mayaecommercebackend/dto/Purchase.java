package ozdemir0ozdemir.mayaecommercebackend.dto;

import lombok.Data;
import ozdemir0ozdemir.mayaecommercebackend.entity.Address;
import ozdemir0ozdemir.mayaecommercebackend.entity.Customer;
import ozdemir0ozdemir.mayaecommercebackend.entity.Order;
import ozdemir0ozdemir.mayaecommercebackend.entity.OrderItem;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address billingAddress;
    private Address shippingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
