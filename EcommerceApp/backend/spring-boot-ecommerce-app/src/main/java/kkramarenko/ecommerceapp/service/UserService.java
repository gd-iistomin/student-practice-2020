package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.dto.UserDetails;
import kkramarenko.ecommerceapp.entity.User;

import java.util.Optional;

public interface UserService {

    boolean registerUser(User user);

    Optional<UserDetails> getUserDetails(String username);
}
