package ozdemir0ozdemir.mayaecommercebackend.request;

import java.math.BigDecimal;

public record CreateProductRequest(String sku,
                                   String name,
                                   String description,
                                   BigDecimal unitPrice,
                                   String imageUrl,
                                   int unitsInStock,
                                   Long productCategoryId) {
}
