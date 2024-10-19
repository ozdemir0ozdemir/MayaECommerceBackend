package ozdemir0ozdemir.mayaecommercebackend.request;

import java.math.BigDecimal;

public record UpdateProductRequest(String name,
                                   String description,
                                   BigDecimal unitPrice,
                                   String imageUrl,
                                   int unitsInStock) {
}
