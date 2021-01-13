package kkramarenko.ecommerceapp.controller;

import kkramarenko.ecommerceapp.entity.User;
import kkramarenko.ecommerceapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api")
public class RegistrationController {

    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/users")
    public HttpStatus createUser(@RequestBody User user){
        boolean successfullySavedNewUser = userService.registerUser(user);

        // if user with such username exists -> return BAD_REQUEST status
        if(successfullySavedNewUser) {
            return HttpStatus.CREATED;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }
}
