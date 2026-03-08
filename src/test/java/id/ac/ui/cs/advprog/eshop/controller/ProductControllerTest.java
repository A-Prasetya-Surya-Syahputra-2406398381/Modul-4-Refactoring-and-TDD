package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    private static final String PRODUCT_LIST_URL = "/product/list";
    private static final String PRODUCT_ID = "eb558e9f";
    private static final String FULL_PRODUCT_ID = "eb558e9f-1c39-460e-8860-71af6af63bd6";

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void testCreateProductPost() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("productId", FULL_PRODUCT_ID)
                        .param("productName", "Sampo Cap Bambang")
                        .param("productQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(PRODUCT_LIST_URL));

        verify(productService, times(1)).create(any(Product.class));
    }

    @Test
    void testProductListPage() throws Exception {
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> allProducts = Arrays.asList(product1, product2);

        when(productService.findAll()).thenReturn(allProducts);

        mockMvc.perform(get(PRODUCT_LIST_URL))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attribute("products", allProducts));
    }

    @Test
    void testEditProductPage() throws Exception {
        Product product = new Product();
        product.setProductId(PRODUCT_ID);

        when(productService.findById(PRODUCT_ID)).thenReturn(product);

        mockMvc.perform(get("/product/edit/" + PRODUCT_ID))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attribute("product", product));
    }

    @Test
    void testEditProductPost() throws Exception {
        mockMvc.perform(post("/product/edit")
                        .param("productId", PRODUCT_ID)
                        .param("productName", "Sampo Cap Usep")
                        .param("productQuantity", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(PRODUCT_LIST_URL));

        verify(productService, times(1)).update(any(Product.class));
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(get("/product/delete/" + PRODUCT_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(PRODUCT_LIST_URL));

        verify(productService, times(1)).delete(PRODUCT_ID);
    }
}