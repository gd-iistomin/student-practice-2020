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

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final CustomerRepository customerRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,
                           CustomerRepository customerRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Saves new user to db
     * <p>
     * Checks if user with matching username/email exists, if so, aborts creation, returns false
     * If no user with matching fields exist, creates new customer, based on user's info, binds
     * that customer to user, cyphers password, saves user
     *
     * @param user - new user to save in database
     * @return boolean - true on success, false on fail
     */
    @Override
    @Transactional
    @SuppressWarnings("checkstyle:todocomment")
    public boolean registerUser(User user) {
        User userWithMatchingUsername = userRepository.findUserByUsername(user.getUsername());
        User userWithMatchingEmail = userRepository.findUserByEmail(user.getEmail());


        //TODO: Consider informing user whether username or email is already used
        if (userWithMatchingUsername != null || userWithMatchingEmail != null) {
            return false;
        }

        Customer newCustomer = new Customer();
        newCustomer.setFirstName(user.getFirstName());
        newCustomer.setLastName(user.getLastName());
        newCustomer.setEmail(user.getEmail());
        Customer savedCustomer = customerRepository.save(newCustomer);

        user.setCustomer(savedCustomer);

        String pass = user.getPassword();
        user.setPassword(passwordEncoder.encode(pass));

        userRepository.save(user);
        return true;
    }


    /**
     * Gets user details by username
     * <p>
     * If user with given username doesn't exist, returns empty Optional
     * else, populates UserDetails object with user data, returns Optional with UserDetails
     *
     * @param username - username of target user
     * @return Optional<UserDetails>
     * @see UserDetails
     */
    @Override
    public Optional<UserDetails> getUserDetails(String username) {
        Optional<UserDetails> userDetailsOptional = Optional.empty();

        User targetUser = userRepository.findUserByUsername(username);

        if (targetUser == null) {
            return userDetailsOptional;
        }

        UserDetails userDetails = new UserDetails();
        userDetails.setUsername(targetUser.getUsername());
        userDetails.setFirstName(targetUser.getFirstName());
        userDetails.setLastName(targetUser.getLastName());
        userDetails.setEmail(targetUser.getEmail());
        userDetails.setDiscountRate(targetUser.getDiscountRate());
        userDetails.setCustomerId(targetUser.getCustomer().getId());

        userDetailsOptional = Optional.of(userDetails);

        return userDetailsOptional;
    }
}
