package kkramarenko.ecommerceapp.dto;

import lombok.Data;

@Data
public class UserDetails {

    // class is used to send back non-sensitive details about user on login to pre-populate forms and bind orders to user.
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String discountRate;
    private long customerId;
}
