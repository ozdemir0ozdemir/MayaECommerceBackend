package ozdemir0ozdemir.mayaecommercebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ozdemir0ozdemir.mayaecommercebackend.request.CreateProductCategoryRequest;

import java.util.Set;

@Entity
@Table(name = "products_categories")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "products")
public class ProductCategory {

    @Id
    @Column(name = "product_category_id")
    @SequenceGenerator(name = "products_categories_id_gen", sequenceName = "products_categories_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "products_categories_id_gen", strategy = GenerationType.SEQUENCE)
    private Long productCategoryId;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @JsonIgnore
    @OneToMany(mappedBy = "productCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Product> products;

    public static ProductCategory of(CreateProductCategoryRequest request) {
        ProductCategory pc = new ProductCategory();
        pc.setCategoryName(request.categoryName());
        return pc;
    }

}
