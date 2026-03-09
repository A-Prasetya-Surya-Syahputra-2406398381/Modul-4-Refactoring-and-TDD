package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private List<Product> products;

    private final String ORDER_ID = "13652556-012a-4c07-b546-54eb1396d79b";
    private final String AUTHOR = "Safira Sudrajat";
    private final long ORDER_TIME = 1708560000L;
    private final String STATUS_SUCCESS = "SUCCESS";
    private final String STATUS_CANCELLED = "CANCELLED";
    private final String INVALID_STATUS = "MEOW";

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);

        Product product2 = new Product();
        product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620155");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);

        this.products.add(product1);
        this.products.add(product2);
    }

    @Test
    void testCreateOrderEmptyProduct() {
        this.products.clear();

        assertThrows(IllegalArgumentException.class, () -> {
            Order order = new Order(ORDER_ID, this.products, ORDER_TIME, AUTHOR);
        });
    }

    @Test
    void testCreateOrderDefaultStatus() {
        Order order = new Order(ORDER_ID, this.products, ORDER_TIME, AUTHOR);

        assertSame(this.products, order.getProducts());
        assertEquals(2, order.getProducts().size());
        assertEquals("Sampo Cap Bambang", order.getProducts().get(0).getProductName());
        assertEquals("Sabun Cap Usep", order.getProducts().get(1).getProductName());

        assertEquals(ORDER_ID, order.getId());
        assertEquals(ORDER_TIME, order.getOrderTime());
        assertEquals(AUTHOR, order.getAuthor());
        assertEquals("WAITING_PAYMENT", order.getStatus());
    }

    @Test
    void testCreateOrderSuccessStatus() {
        Order order = new Order(ORDER_ID, this.products, ORDER_TIME, AUTHOR, STATUS_SUCCESS);
        assertEquals(STATUS_SUCCESS, order.getStatus());
    }

    @Test
    void testCreateOrderInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            Order order = new Order(ORDER_ID, this.products, ORDER_TIME, AUTHOR, INVALID_STATUS);
        });
    }

    @Test
    void testSetStatusToCancelled() {
        Order order = new Order(ORDER_ID, this.products, ORDER_TIME, AUTHOR);
        order.setStatus(STATUS_CANCELLED);
        assertEquals(STATUS_CANCELLED, order.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        Order order = new Order(ORDER_ID, this.products, ORDER_TIME, AUTHOR);
        assertThrows(IllegalArgumentException.class, () -> order.setStatus(INVALID_STATUS));
    }
}