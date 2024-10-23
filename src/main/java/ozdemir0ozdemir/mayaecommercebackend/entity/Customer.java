package ozdemir0ozdemir.mayaecommercebackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
public class Customer {

    @Id
    @SequenceGenerator(name = "customers_id_gen", sequenceName = "customers_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "customers_id_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "email")
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();

    public void addOrder(Order order){
        order.setCustomer(this);
        this.orders.add(order);
    }
}
