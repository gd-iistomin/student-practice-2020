package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.entity.Customer;
import kkramarenko.ecommerceapp.enums.DiscountRate;

public interface LoyaltyProgramService {

    DiscountRate getCustomerCurrentDiscountRate(Customer customer);
}
