package ozdemir0ozdemir.mayaecommercebackend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import ozdemir0ozdemir.mayaecommercebackend.TestcontainersConfiguration;
import ozdemir0ozdemir.mayaecommercebackend.dao.ProductRepository;
import ozdemir0ozdemir.mayaecommercebackend.entity.Product;
import ozdemir0ozdemir.mayaecommercebackend.request.CreateProductRequest;
import ozdemir0ozdemir.mayaecommercebackend.request.UpdateProductRequest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
@Sql("/test-products.sql")
@AutoConfigureMockMvc
class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void should_getAllProducts() throws Exception {
        MvcResult result = mvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String json = result.getResponse().getContentAsString();

        List<Product> resultlist = mapper.readValue(json, List.class);

        assertNotNull(resultlist);
        assertFalse(resultlist.isEmpty());
    }

    @Test
    void should_getProductById() throws Exception {
        final Long productId = 1L;

        MvcResult mvcResult = mvc.perform(get("/products/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Product resultProduct =
                mapper.readValue(mvcResult.getResponse().getContentAsString(), Product.class);

        assertNotNull(resultProduct);
        assertEquals(productId, resultProduct.getId());
    }

    @Test
    void should_not_getProductById() throws Exception {
        final Long productId = 10L;

        mvc.perform(get("/products/{productId}", productId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(Matchers.blankString()))
                .andReturn();
    }

    @Test
    void should_createNewProduct() throws Exception {
        CreateProductRequest request =
                new CreateProductRequest("MYBOOK-001",
                        "Steve Jobs",
                        "Biography",
                        BigDecimal.valueOf(10.8),
                        "https://i.dr.com.tr/cache/500x400-0/originals/0000000375241-1.jpg",
                        25,
                        1L);

        MvcResult mvcResult = mvc
                .perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(request))
                ).andExpect(status().isCreated())
                .andReturn();


        Product product = mapper.readValue(mvcResult.getResponse().getContentAsString(), Product.class);

        assertTrue(mvcResult.getResponse()
                .getHeader("Location")
                .contains("/products/" + product.getId().intValue())
        );

        assertEquals(request.sku(), product.getSku());
        assertEquals(request.name(), product.getName());
        assertEquals(request.description(), product.getDescription());
        assertEquals(request.unitPrice(), product.getUnitPrice());
        assertEquals(request.imageUrl(), product.getImageUrl());
        assertEquals(request.unitsInStock(), product.getUnitsInStock());
        assertEquals(request.productCategoryId(), product.getProductCategory().getProductCategoryId());
    }

    @Test
    void should_updateExistingProductById() throws Exception {
        final Long productId = 1L;
        final UpdateProductRequest request =
                new UpdateProductRequest("The Black Obelisk",
                        "Novel",
                        BigDecimal.TEN,
                        "https://m.media-amazon.com/images/I/612MBG8pjbL._AC_UF1000,1000_QL80_.jpg",
                        4);

        RequestBuilder putRequest = put("/products/{productId}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request));

        MvcResult result = mvc.perform(putRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Product product = mapper.readValue(result.getResponse().getContentAsString(), Product.class);

        assertEquals(request.name(), product.getName());
        assertEquals(request.description(), product.getDescription());
        assertEquals(request.unitPrice(), product.getUnitPrice());
        assertEquals(request.imageUrl(), product.getImageUrl());
        assertEquals(request.unitsInStock(), product.getUnitsInStock());
    }

    @Test
    void should_deleteExistingProductById() throws Exception {
        final Long productId = 1L;

        RequestBuilder deleteRequest = delete("/products/{productId}", productId);

        mvc.perform(deleteRequest)
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.emptyString()));

        Product product = productRepository.findById(productId)
                .orElse(null);

        assertNull(product);
    }
}