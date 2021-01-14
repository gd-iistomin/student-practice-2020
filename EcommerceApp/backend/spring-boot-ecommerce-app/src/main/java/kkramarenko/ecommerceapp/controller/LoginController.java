package kkramarenko.ecommerceapp.controller;

import kkramarenko.ecommerceapp.dto.UserDetails;
import kkramarenko.ecommerceapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@CrossOrigin("http://localhost:4200")
@RestController
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public Principal checkUser(Principal user){
        return user;
    }

    // endpoint for retrieving user data by username
    @GetMapping("/userdetails/{username}")
    public ResponseEntity<UserDetails> getUserDetails(@PathVariable String username){
        Optional<UserDetails> userDetailsOptional = userService.getUserDetails(username);

        // on success, send 200 OK status code and user detail, else send
        if(userDetailsOptional.isPresent()){
            return new ResponseEntity<>(userDetailsOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
