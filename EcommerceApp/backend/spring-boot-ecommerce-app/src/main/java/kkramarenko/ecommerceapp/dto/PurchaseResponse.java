package kkramarenko.ecommerceapp.dto;

import lombok.Data;

@Data
public class PurchaseResponse {

    private final String orderTrackingNumber;

    private final boolean discountRateChanged;

    private final String newDiscountRate;
}
