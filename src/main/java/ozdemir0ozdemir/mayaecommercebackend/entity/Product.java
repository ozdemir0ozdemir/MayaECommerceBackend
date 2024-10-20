package ozdemir0ozdemir.mayaecommercebackend.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ozdemir0ozdemir.mayaecommercebackend.request.CreateProductRequest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product implements Serializable {

    @Id
    @Column(name = "product_id")
    @SequenceGenerator(name = "products_id_gen", sequenceName = "products_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "products_id_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "sku", nullable = false)
    private String sku;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "units_in_stock", nullable = false)
    private int unitsInStock;

    @Column(name = "date_created", nullable = false)
    @CreationTimestamp
    private Date dateCreated;

    @Column(name = "last_updated")
    @UpdateTimestamp
    private Date lastUpdated;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_category_id", nullable = false)
    private ProductCategory productCategory;


    public static Product of(CreateProductRequest request) {
        Product product = new Product();
        product.setActive(false);
        product.setDescription(request.description());
        product.setName(request.name());
        product.setSku(request.sku());
        product.setImageUrl(request.imageUrl());
        product.setUnitPrice(request.unitPrice());
        product.setUnitsInStock(request.unitsInStock());

        return product;
    }

}
