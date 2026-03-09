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
        return null;
    }

    private boolean isValidVoucher(Map<String, String> paymentData) {
        return false;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        return null;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return null;
    }

    @Override
    public List<Payment> getAllPayments() {
        return null;
    }
}