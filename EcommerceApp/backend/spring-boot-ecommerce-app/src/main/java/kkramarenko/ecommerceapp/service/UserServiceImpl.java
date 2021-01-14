package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.entity.Customer;
import kkramarenko.ecommerceapp.entity.User;
import kkramarenko.ecommerceapp.repository.CustomerRepository;
import kkramarenko.ecommerceapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private CustomerRepository customerRepository;

    public UserServiceImpl(UserRepository userRepository, CustomerRepository customerRepository) {
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

            userRepository.save(user);
            return true;
        }
        //else return false
        return false;
    }
}
