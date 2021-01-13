package kkramarenko.ecommerceapp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@CrossOrigin("http://localhost:4200")
@RestController
public class LoginController {

    @GetMapping("/user")
    public Principal checkUser(Principal user){
        return user;
    }
}
