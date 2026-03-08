package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {

    private static final String FULL_PRODUCT_ID = "eb558e9f-1c39-460e-8860-71af6af63bd6";
    private static final String DELETE_ID = "id-akan-dihapus";
    
    @InjectMocks
    ProductRepository productRepository;
    @BeforeEach
    void setUp(){
    }
    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId(FULL_PRODUCT_ID);
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId(FULL_PRODUCT_ID);
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProductSuccess() {
        Product product = new Product();
        product.setProductId(FULL_PRODUCT_ID);
        product.setProductName("Sampo Lama");
        product.setProductQuantity(100);

        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(FULL_PRODUCT_ID);
        updatedProduct.setProductName("Sampo Baru");
        updatedProduct.setProductQuantity(50);

        Product result = productRepository.update(updatedProduct);

        assertNotNull(result);
        assertEquals("Sampo Baru", result.getProductName());
        assertEquals(50, result.getProductQuantity());

        Product savedProduct = productRepository.findById(FULL_PRODUCT_ID);

        assertEquals("Sampo Baru", savedProduct.getProductName());
    }


    @Test
    void testEditProductFail() {
        Product product = new Product();
        product.setProductId("id-asli");
        product.setProductName("Barang Asli");
        productRepository.create(product);

        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("id-palsu");
        nonExistentProduct.setProductName("Hantu");

        assertThrows(IllegalArgumentException.class, () -> {
            productRepository.update(nonExistentProduct);
        });
    }


    @Test
    void testDeleteProductSuccess() {
        Product product = new Product();
        product.setProductId(DELETE_ID);
        product.setProductName("Barang Sementara");

        productRepository.create(product);

        assertNotNull(productRepository.findById(DELETE_ID));

        productRepository.delete(DELETE_ID);

        Product result = productRepository.findById(DELETE_ID);
        assertNull(result);

        Iterator<Product> iterator = productRepository.findAll();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testDeleteProductFail() {
        Product product = new Product();
        product.setProductId("id-aman");
        product.setProductName("Barang Aman");
        productRepository.create(product);


        assertThrows(IllegalArgumentException.class, () -> {
            productRepository.delete("id-tidak-ada");
        });

        Product result = productRepository.findById("id-aman");
        assertNotNull(result);
        assertEquals("Barang Aman", result.getProductName());
    }

    @Test
    void testCreateWithNullId() {
        Product product = new Product();
        product.setProductName("Product No ID");
        product.setProductQuantity(10);

        Product createdProduct = productRepository.create(product);

        assertNotNull(createdProduct.getProductId());
        assertFalse(createdProduct.getProductId().isEmpty());
    }

    @Test
    void testCreateNullProduct() {
        assertThrows(RuntimeException.class, () -> {
            productRepository.create(null);
        });
    }

    @Test
    void testFindByIdNotFound() {
        Product result = productRepository.findById("non-existent-id");
        assertNull(result);
    }

    @Test
    void testUpdateWithEmptyList() {
        Product product = new Product();
        product.setProductId("any-id");

        assertThrows(IllegalArgumentException.class, () -> {
            productRepository.update(product);
        });
    }

    @Test
    void testDeleteEmptyList() {
        assertThrows(IllegalArgumentException.class, () -> {
            productRepository.delete("any-id");
        });
    }

    @Test
    void testUpdateWithNullId() {
        Product product = new Product();
        product.setProductId("id-1");
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(null);

        assertThrows(Exception.class, () -> {
            productRepository.update(updatedProduct);
        });
    }

    @Test
    void testFindByIdProductNotFoundIteratedThroughAll() {
        Product product1 = new Product();
        product1.setProductId("id-1");
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("id-2");
        productRepository.create(product2);

        Product result = productRepository.findById("id-tidak-ada");

        assertNull(result);
    }

    @Test
    void testFindByIdEmptyList() {
        Product result = productRepository.findById("sembarang-id");
        assertNull(result);
    }
}
