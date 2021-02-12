package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.entity.LoyaltyProgramStatus;
import kkramarenko.ecommerceapp.entity.User;

public interface LoyaltyProgramService {

    LoyaltyProgramStatus getUserCurrentDiscountRate(User user);
}
