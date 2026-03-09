package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CodPaymentServiceImplTest {

    @InjectMocks
    CodPaymentServiceImpl codPaymentService;

    @Mock
    PaymentRepository paymentRepository;

    Order order;

    private final String METHOD_COD = "COD";
    private final String STATUS_SUCCESS = "SUCCESS";
    private final String STATUS_REJECTED = "REJECTED";
    private final String KEY_ADDRESS = "address";
    private final String KEY_DELIVERY_FEE = "deliveryFee";

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("prod-1");
        product.setProductName("Dummy Product");
        product.setProductQuantity(1);
        products.add(product);

        order = new Order("order-13652556", products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testAddPaymentCODValid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(KEY_ADDRESS, "Jalan Margonda Raya");
        paymentData.put(KEY_DELIVERY_FEE, "15000");

        when(paymentRepository.save(any(Payment.class))).thenAnswer((Answer<Payment>) invocation -> invocation.getArgument(0));

        Payment result = codPaymentService.addPayment(order, METHOD_COD, paymentData);

        assertNotNull(result);
        assertEquals(STATUS_SUCCESS, result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentCODNullAddress() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(KEY_ADDRESS, null);
        paymentData.put(KEY_DELIVERY_FEE, "15000");

        when(paymentRepository.save(any(Payment.class))).thenAnswer((Answer<Payment>) invocation -> invocation.getArgument(0));

        Payment result = codPaymentService.addPayment(order, METHOD_COD, paymentData);

        assertEquals(STATUS_REJECTED, result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentCODEmptyAddress() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(KEY_ADDRESS, "");
        paymentData.put(KEY_DELIVERY_FEE, "15000");

        when(paymentRepository.save(any(Payment.class))).thenAnswer((Answer<Payment>) invocation -> invocation.getArgument(0));

        Payment result = codPaymentService.addPayment(order, METHOD_COD, paymentData);

        assertEquals(STATUS_REJECTED, result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentCODNullDeliveryFee() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(KEY_ADDRESS, "Jalan Margonda Raya");
        paymentData.put(KEY_DELIVERY_FEE, null);

        when(paymentRepository.save(any(Payment.class))).thenAnswer((Answer<Payment>) invocation -> invocation.getArgument(0));

        Payment result = codPaymentService.addPayment(order, METHOD_COD, paymentData);

        assertEquals(STATUS_REJECTED, result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentCODEmptyDeliveryFee() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(KEY_ADDRESS, "Jalan Margonda Raya");
        paymentData.put(KEY_DELIVERY_FEE, "");

        when(paymentRepository.save(any(Payment.class))).thenAnswer((Answer<Payment>) invocation -> invocation.getArgument(0));

        Payment result = codPaymentService.addPayment(order, METHOD_COD, paymentData);

        assertEquals(STATUS_REJECTED, result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }
}