package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class VoucherPaymentServiceImpl implements VoucherPaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    private Map<String, Order> orderMap = new HashMap<>();

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(paymentId, method, paymentData);

        if (isValidVoucher(paymentData)) {
            payment.setStatus("SUCCESS");
        } else {
            payment.setStatus("REJECTED");
        }

        orderMap.put(payment.getId(), order);
        return paymentRepository.save(payment);
    }

    private boolean isValidVoucher(Map<String, String> paymentData) {
        String voucherCode = paymentData.get("voucherCode");

        // Cek null, panjang 16 karakter, dan diawali "ESHOP"
        if (voucherCode == null || voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
            return false;
        }

        // Cek jumlah karakter numerik (harus tepat 8)
        long numericCount = voucherCode.chars().filter(Character::isDigit).count();
        return numericCount == 8;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);

        Order order = orderMap.get(payment.getId());
        if (order != null) {
            if ("SUCCESS".equals(status)) {
                order.setStatus("SUCCESS");
            } else if ("REJECTED".equals(status)) {
                order.setStatus("FAILED");
            }
        }

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}