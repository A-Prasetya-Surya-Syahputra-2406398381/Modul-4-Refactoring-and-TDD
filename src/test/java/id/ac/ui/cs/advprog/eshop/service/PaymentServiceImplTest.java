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
class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    Order order;
    Map<String, String> dummyPaymentData;

    private final String STATUS_SUCCESS = "SUCCESS";
    private final String STATUS_REJECTED = "REJECTED";
    private final String STATUS_WAITING_PAYMENT = "WAITING_PAYMENT";
    private final String STATUS_FAILED = "FAILED";
    private final String DUMMY_METHOD = "DUMMY_METHOD";

    @BeforeEach
    void setUp() {
        // Inisialisasi Product dan Order yang valid untuk menghindari IllegalArgumentException
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("prod-1");
        product.setProductName("Dummy Product");
        product.setProductQuantity(1);
        products.add(product);

        order = new Order("order-13652556", products, 1708560000L, "Safira Sudrajat");

        dummyPaymentData = new HashMap<>();
        dummyPaymentData.put("dummyKey", "dummyValue");
    }

    @Test
    void testAddPaymentCoreBehavior() {
        // Memastikan repository mengembalikan objek yang sama saat di-save
        when(paymentRepository.save(any(Payment.class))).thenAnswer((Answer<Payment>) invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(order, DUMMY_METHOD, dummyPaymentData);

        assertNotNull(result);
        assertNotNull(result.getId()); // Memastikan ID ter-generate
        assertEquals(DUMMY_METHOD, result.getMethod());
        assertEquals(dummyPaymentData, result.getPaymentData());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusSuccessUpdatesOrderStatus() {
        when(paymentRepository.save(any(Payment.class))).thenAnswer((Answer<Payment>) invocation -> invocation.getArgument(0));

        // Panggil addPayment agar service menghubungkan Payment ID dengan Order ini di memori internalnya
        Payment payment = paymentService.addPayment(order, DUMMY_METHOD, dummyPaymentData);
        order.setStatus(STATUS_WAITING_PAYMENT);

        Payment result = paymentService.setStatus(payment, STATUS_SUCCESS);

        assertEquals(STATUS_SUCCESS, result.getStatus());
        assertEquals(STATUS_SUCCESS, order.getStatus()); // Order menjadi SUCCESS
        verify(paymentRepository, atLeastOnce()).save(any(Payment.class));
    }

    @Test
    void testSetStatusRejectedUpdatesOrderStatus() {
        when(paymentRepository.save(any(Payment.class))).thenAnswer((Answer<Payment>) invocation -> invocation.getArgument(0));

        // Panggil addPayment agar service menghubungkan Payment ID dengan Order ini di memori internalnya
        Payment payment = paymentService.addPayment(order, DUMMY_METHOD, dummyPaymentData);
        order.setStatus(STATUS_WAITING_PAYMENT);

        Payment result = paymentService.setStatus(payment, STATUS_REJECTED);

        assertEquals(STATUS_REJECTED, result.getStatus());
        assertEquals(STATUS_FAILED, order.getStatus()); // Order menjadi FAILED
        verify(paymentRepository, atLeastOnce()).save(any(Payment.class));
    }

    @Test
    void testGetPayment() {
        String paymentId = "pay-123";
        Payment payment = new Payment(paymentId, DUMMY_METHOD, dummyPaymentData);
        when(paymentRepository.findById(paymentId)).thenReturn(payment);

        Payment result = paymentService.getPayment(paymentId);

        assertNotNull(result);
        assertEquals(paymentId, result.getId());
        verify(paymentRepository, times(1)).findById(paymentId);
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment("pay-123", DUMMY_METHOD, dummyPaymentData));
        payments.add(new Payment("pay-456", DUMMY_METHOD, dummyPaymentData));

        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(2, result.size());
        verify(paymentRepository, times(1)).findAll();
    }
}