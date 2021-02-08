package kkramarenko.ecommerceapp.controller;

import kkramarenko.ecommerceapp.dto.RegistrationResponse;
import kkramarenko.ecommerceapp.entity.User;
import kkramarenko.ecommerceapp.enums.UserRegistrationStatus;
import kkramarenko.ecommerceapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/users")
    public ResponseEntity<RegistrationResponse> createUser(@RequestBody User user) {
        UserRegistrationStatus userRegistrationStatus = userService.registerUser(user);

        if (userRegistrationStatus.equals(UserRegistrationStatus.OK)) {
            return new ResponseEntity<>(new RegistrationResponse("Successfully created user."), HttpStatus.CREATED);
        } else if (userRegistrationStatus.equals(UserRegistrationStatus.FOUND_USER_WITH_MATCHING_USERNAME)) {
            return new ResponseEntity<>(new RegistrationResponse("Username already taken"), HttpStatus.IM_USED);
        } else {
            return new ResponseEntity<>(new RegistrationResponse("Email already taken"), HttpStatus.IM_USED);
        }
    }


}
