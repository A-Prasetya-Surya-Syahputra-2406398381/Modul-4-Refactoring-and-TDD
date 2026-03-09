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
class VoucherPaymentServiceImplTest {

    @InjectMocks
    VoucherPaymentServiceImpl voucherPaymentService;

    @Mock
    PaymentRepository paymentRepository;

    Order order;

    private final String METHOD_VOUCHER = "VOUCHER";
    private final String STATUS_SUCCESS = "SUCCESS";
    private final String STATUS_REJECTED = "REJECTED";
    private final String KEY_VOUCHER_CODE = "voucherCode";

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
    void testAddPaymentVoucherValid() {
        String validVoucher = "ESHOP1234ABC5678";
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(KEY_VOUCHER_CODE, validVoucher);

        when(paymentRepository.save(any(Payment.class))).thenAnswer((Answer<Payment>) invocation -> invocation.getArgument(0));

        Payment result = voucherPaymentService.addPayment(order, METHOD_VOUCHER, paymentData);

        assertNotNull(result);
        assertEquals(STATUS_SUCCESS, result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentVoucherInvalidLength() {
        String invalidVoucherLength = "ESHOP123";
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(KEY_VOUCHER_CODE, invalidVoucherLength);

        when(paymentRepository.save(any(Payment.class))).thenAnswer((Answer<Payment>) invocation -> invocation.getArgument(0));

        Payment result = voucherPaymentService.addPayment(order, METHOD_VOUCHER, paymentData);

        assertEquals(STATUS_REJECTED, result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentVoucherInvalidPrefix() {
        String invalidVoucherPrefix = "PROMO1234ABC5678";
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(KEY_VOUCHER_CODE, invalidVoucherPrefix);

        when(paymentRepository.save(any(Payment.class))).thenAnswer((Answer<Payment>) invocation -> invocation.getArgument(0));

        Payment result = voucherPaymentService.addPayment(order, METHOD_VOUCHER, paymentData);

        assertEquals(STATUS_REJECTED, result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentVoucherInvalidNumericCountLessThanEight() {
        String invalidVoucherNumbers = "ESHOP12ABCDEFGHI";
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(KEY_VOUCHER_CODE, invalidVoucherNumbers);

        when(paymentRepository.save(any(Payment.class))).thenAnswer((Answer<Payment>) invocation -> invocation.getArgument(0));

        Payment result = voucherPaymentService.addPayment(order, METHOD_VOUCHER, paymentData);

        assertEquals(STATUS_REJECTED, result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentVoucherInvalidNumericCountMoreThanEight() {
        String invalidVoucherNumbers = "ESHOP1234567890A";
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(KEY_VOUCHER_CODE, invalidVoucherNumbers);

        when(paymentRepository.save(any(Payment.class))).thenAnswer((Answer<Payment>) invocation -> invocation.getArgument(0));

        Payment result = voucherPaymentService.addPayment(order, METHOD_VOUCHER, paymentData);

        assertEquals(STATUS_REJECTED, result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentVoucherNullCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(KEY_VOUCHER_CODE, null);

        when(paymentRepository.save(any(Payment.class))).thenAnswer((Answer<Payment>) invocation -> invocation.getArgument(0));

        Payment result = voucherPaymentService.addPayment(order, METHOD_VOUCHER, paymentData);

        assertEquals(STATUS_REJECTED, result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }
}