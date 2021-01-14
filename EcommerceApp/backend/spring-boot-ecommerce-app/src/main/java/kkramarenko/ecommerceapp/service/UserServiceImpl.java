package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.dto.UserDetails;
import kkramarenko.ecommerceapp.entity.Customer;
import kkramarenko.ecommerceapp.entity.User;
import kkramarenko.ecommerceapp.repository.CustomerRepository;
import kkramarenko.ecommerceapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    private CustomerRepository customerRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, CustomerRepository customerRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public boolean registerUser(User user) {
        // check if user with such username or email exists
        User userWithMatchingUsername = userRepository.findUserByUsername(user.getUsername());
        User userWithMatchingEmail = userRepository.findUserByEmail(user.getEmail());

        //if no existing user with given username or email -> create new customer based on user info, then
        //add customerId to user, finally save user
        if(userWithMatchingUsername == null && userWithMatchingEmail == null){

            // create new customer, populate it, save
            Customer newCustomer = new Customer();
            newCustomer.setFirstName(user.getFirstName());
            newCustomer.setLastName(user.getLastName());
            newCustomer.setEmail(user.getEmail());
            // save customer, retrieve it to get Id
            Customer savedCustomer = customerRepository.save(newCustomer);

            user.setCustomer(savedCustomer);

            // get user password, cypher it
            String pass = user.getPassword();
            user.setPassword(passwordEncoder.encode(pass));

            userRepository.save(user);
            return true;
        }
        //else return false
        return false;
    }

    @Override
    public Optional<UserDetails> getUserDetails(String username) {
        //create empty Optional
        Optional<UserDetails> userDetailsOptional = Optional.empty();

        // get target user
        User targetUser = userRepository.findUserByUsername(username);

        //populate dto object with data if user != null
        if (targetUser != null) {
            UserDetails userDetails = new UserDetails();
            userDetails.setUsername(targetUser.getUsername());
            userDetails.setFirstName(targetUser.getFirstName());
            userDetails.setLastName(targetUser.getLastName());
            userDetails.setEmail(targetUser.getEmail());
            userDetails.setDiscountRate(targetUser.getDiscountRate());
            userDetails.setCustomerId(targetUser.getCustomer().getId());

            //put to Optional
            userDetailsOptional = Optional.of(userDetails);
        }

        return userDetailsOptional;
    }
}
