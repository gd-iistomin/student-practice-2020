package kkramarenko.ecommerceapp.dto;

import lombok.Data;

/**
 * Class is used to send back details about user on login to pre-populate forms,
 * get info about user's orders
 */
@Data
public class UserDetails {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String discountRate;
    private long customerId;
}
