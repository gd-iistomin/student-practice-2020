package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.dto.UserDetails;
import kkramarenko.ecommerceapp.entity.User;
import kkramarenko.ecommerceapp.enums.UserRegistrationStatus;

import java.util.Optional;

public interface UserService {

    UserRegistrationStatus registerUser(User user);

    Optional<UserDetails> getUserDetails(String username);
}
