package kkramarenko.ecommerceapp.controller;

import kkramarenko.ecommerceapp.entity.User;
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

    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody User user) {
        boolean successfullySavedNewUser = userService.registerUser(user);

        if (successfullySavedNewUser) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.IM_USED);
        }
    }


}
