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
import ozdemir0ozdemir.mayaecommercebackend.dao.ProductCategoryRepository;
import ozdemir0ozdemir.mayaecommercebackend.entity.ProductCategory;
import ozdemir0ozdemir.mayaecommercebackend.request.CreateProductCategoryRequest;
import ozdemir0ozdemir.mayaecommercebackend.request.UpdateProductCategoryRequest;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql("/test-products.sql")
class ProductCategoryControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    void should_getAllProductCategories() throws Exception {

        MvcResult result = mvc.perform(get("/products/categories"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ProductCategory[] categories =
                mapper.readValue(result.getResponse().getContentAsString(), ProductCategory[].class);

        assertTrue(categories.length > 0);
    }

    @Test
    void should_getProductCategoryById() throws Exception {
        final Long productCategoryId = 1L;

        MvcResult result = mvc
                .perform(get("/products/categories/{productCategoryId}", productCategoryId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String bodyJson = result.getResponse().getContentAsString();

        ProductCategory pc = mapper.readValue(bodyJson, ProductCategory.class);

        assertNotNull(pc);
        assertEquals(productCategoryId, pc.getProductCategoryId());

    }

    @Test
    void should_not_getProductCategoryById() throws Exception {
        final Long productCategoryId = 100L;

        mvc.perform(get("/products/categories/{productCategoryId}", productCategoryId))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_createNewProductCategory() throws Exception {
        CreateProductCategoryRequest request =
                new CreateProductCategoryRequest("Movies");

        RequestBuilder postRequest = post("/products/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request));

        MvcResult result = mvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String bodyJson = result.getResponse().getContentAsString();
        ProductCategory pc = mapper.readValue(bodyJson, ProductCategory.class);

        assertTrue(result.getResponse().getHeader("Location").contains("/products/categories/" + pc.getProductCategoryId()));
        assertEquals(request.categoryName(), pc.getCategoryName());
    }

    @Test
    void should_updateProductCategoryById() throws Exception {
        final Long productCategoryId = 1L;
        final UpdateProductCategoryRequest request =
                new UpdateProductCategoryRequest("Movies");

        RequestBuilder putRequest = put("/products/categories/{productCategoryId}", productCategoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request));

        MvcResult result = mvc.perform(putRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String bodyJson = result.getResponse().getContentAsString();

        ProductCategory pc = mapper.readValue(bodyJson, ProductCategory.class);
        assertEquals(productCategoryId, pc.getProductCategoryId());
        assertEquals(request.name(), pc.getCategoryName());
    }

    @Test
    void should_not_updateProductCategoryById() throws Exception {
        final Long productCategoryId = 10L;
        final UpdateProductCategoryRequest request =
                new UpdateProductCategoryRequest("Movies");

        RequestBuilder putRequest = put("/products/categories/{productCategoryId}", productCategoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request));

        mvc.perform(putRequest)
                .andExpect(status().isNotFound());
    }

    @Test
    void should_deleteProductCategoryById() throws Exception {
        final Long productCategoryId = 1L;

        RequestBuilder deleteRequest =
                delete("/products/categories/{productCategoryId}", productCategoryId);

        mvc.perform(deleteRequest)
                .andExpect(content().string(Matchers.blankString()))
                .andExpect(status().isOk());

        ProductCategory pc = productCategoryRepository.findById(productCategoryId)
                .orElse(null);

        assertNull(pc);
    }
}