package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Payment ID cannot be null or empty");
        }
        if (method == null || method.trim().isEmpty()) {
            throw new IllegalArgumentException("Payment Method cannot be null or empty");
        }
        if (paymentData == null) {
            throw new IllegalArgumentException("Payment Data cannot be null");
        }
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
    }

    public void setStatus(String status) {
        if (!"SUCCESS".equals(status) && !"REJECTED".equals(status) && !"PENDING".equals(status)) {
            throw new IllegalArgumentException("Invalid status");
        }
        this.status = status;
    }

}