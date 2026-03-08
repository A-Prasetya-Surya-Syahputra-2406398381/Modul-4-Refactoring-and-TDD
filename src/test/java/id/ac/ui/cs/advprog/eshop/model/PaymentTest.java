package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
    }

    @Test
    void testCreatePaymentHappyPath() {
        Payment payment = new Payment("payment-1", "VOUCHER", paymentData);

        assertEquals("payment-1", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testSetStatusSuccessHappyPath() {
        Payment payment = new Payment("payment-1", "VOUCHER", paymentData);
        payment.setStatus("SUCCESS");

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetStatusRejectedHappyPath() {
        Payment payment = new Payment("payment-1", "VOUCHER", paymentData);
        payment.setStatus("REJECTED");

        assertEquals("REJECTED", payment.getStatus());
    }


    @Test
    void testCreatePaymentWithNullIdUnhappyPath() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment(null, "VOUCHER", paymentData);
        });
    }

    @Test
    void testCreatePaymentWithEmptyIdUnhappyPath() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("", "VOUCHER", paymentData);
        });
    }

    @Test
    void testCreatePaymentWithNullMethodUnhappyPath() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("payment-1", null, paymentData);
        });
    }

    @Test
    void testCreatePaymentWithNullPaymentDataUnhappyPath() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("payment-1", "VOUCHER", null);
        });
    }

    @Test
    void testSetInvalidStatusUnhappyPath() {
        Payment payment = new Payment("payment-1", "VOUCHER", paymentData);

        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("STATUS_TIDAK_JELAS");
        });
    }
}