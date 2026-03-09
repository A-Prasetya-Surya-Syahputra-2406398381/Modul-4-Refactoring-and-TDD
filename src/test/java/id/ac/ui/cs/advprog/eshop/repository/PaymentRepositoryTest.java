package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    Payment payment1;
    Payment payment2;

    // Variables for string literals
    String paymentId1 = "payment-1";
    String paymentId2 = "payment-2";
    String invalidId = "invalid-id";
    String methodVoucher = "VOUCHER";
    String methodCod = "COD";
    String statusSuccess = "SUCCESS";
    String voucherCodeKey = "voucherCode";
    String voucherCodeValue1 = "ESHOP1234ABC5678";
    String voucherCodeValue2 = "ESHOP9876XYZ5432";
    String addressKey = "address";
    String addressValue = "Jalan Sudirman No. 1";
    String deliveryFeeKey = "deliveryFee";
    String deliveryFeeValue = "10000";

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put(voucherCodeKey, voucherCodeValue1);
        payment1 = new Payment(paymentId1, methodVoucher, paymentData1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put(addressKey, addressValue);
        paymentData2.put(deliveryFeeKey, deliveryFeeValue);
        payment2 = new Payment(paymentId2, methodCod, paymentData2);
    }

    @Test
    void testSaveCreate() {
        Payment result = paymentRepository.save(payment1);

        Payment foundPayment = paymentRepository.findById(paymentId1);
        assertNotNull(foundPayment);
        assertEquals(paymentId1, foundPayment.getId());
        assertEquals(methodVoucher, foundPayment.getMethod());
        assertEquals(payment1.getPaymentData(), foundPayment.getPaymentData());
    }

    @Test
    void testSaveUpdate() {
        paymentRepository.save(payment1);

        Map<String, String> newPaymentData = new HashMap<>();
        newPaymentData.put(voucherCodeKey, voucherCodeValue2);
        Payment updatedPayment = new Payment(paymentId1, methodVoucher, newPaymentData);
        updatedPayment.setStatus(statusSuccess);

        Payment result = paymentRepository.save(updatedPayment);

        Payment foundPayment = paymentRepository.findById(paymentId1);
        assertNotNull(foundPayment);
        assertEquals(paymentId1, foundPayment.getId());
        assertEquals(statusSuccess, foundPayment.getStatus());
        assertEquals(newPaymentData, foundPayment.getPaymentData());
    }

    @Test
    void testFindByIdIfFound() {
        paymentRepository.save(payment1);

        Payment foundPayment = paymentRepository.findById(paymentId1);
        assertNotNull(foundPayment);
        assertEquals(paymentId1, foundPayment.getId());
    }

    @Test
    void testFindByIdIfNotFound() {
        paymentRepository.save(payment1);

        Payment foundPayment = paymentRepository.findById(invalidId);
        assertNull(foundPayment);
    }

    @Test
    void testGetAllPayments() {
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        List<Payment> allPayments = paymentRepository.findAll();
        assertEquals(2, allPayments.size());
        assertEquals(paymentId1, allPayments.get(0).getId());
        assertEquals(paymentId2, allPayments.get(1).getId());
    }

    @Test
    void testGetAllPaymentsIfEmpty() {
        List<Payment> allPayments = paymentRepository.findAll();
        assertTrue(allPayments.isEmpty());
    }
}