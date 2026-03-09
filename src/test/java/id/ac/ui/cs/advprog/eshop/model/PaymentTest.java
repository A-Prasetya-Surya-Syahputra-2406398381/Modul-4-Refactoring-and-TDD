package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    Map<String, String> paymentData;

    private final String PAYMENT_ID = "payment-1";
    private final String PAYMENT_METHOD = "VOUCHER";

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
    }

    @Test
    void testCreatePaymentHappyPath() {
        Payment payment = new Payment(PAYMENT_ID, PAYMENT_METHOD, paymentData);

        assertEquals(PAYMENT_ID, payment.getId());
        assertEquals(PAYMENT_METHOD, payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testSetStatusSuccessHappyPath() {
        Payment payment = new Payment(PAYMENT_ID, PAYMENT_METHOD, paymentData);
        payment.setStatus("SUCCESS");

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetStatusRejectedHappyPath() {
        Payment payment = new Payment(PAYMENT_ID, PAYMENT_METHOD, paymentData);
        payment.setStatus("REJECTED");

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentWithNullIdUnhappyPath() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment(null, PAYMENT_METHOD, paymentData);
        });
    }

    @Test
    void testCreatePaymentWithEmptyIdUnhappyPath() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("", PAYMENT_METHOD, paymentData);
        });
    }

    @Test
    void testCreatePaymentWithNullMethodUnhappyPath() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment(PAYMENT_ID, null, paymentData);
        });
    }

    @Test
    void testCreatePaymentWithNullPaymentDataUnhappyPath() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment(PAYMENT_ID, PAYMENT_METHOD, null);
        });
    }

    @Test
    void testSetInvalidStatusUnhappyPath() {
        Payment payment = new Payment(PAYMENT_ID, PAYMENT_METHOD, paymentData);

        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("STATUS_TIDAK_JELAS");
        });
    }
}