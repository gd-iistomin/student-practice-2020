package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.entity.User;
import kkramarenko.ecommerceapp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean registerUser(User user) {
        // check if user with such username or email exists
        User userWithMatchingUsername = userRepository.findUserByUsername(user.getUsername());
        User userWithMatchingEmail = userRepository.findUserByEmail(user.getEmail());

        //if no existing user with given username or email -> save
        if(userWithMatchingUsername == null && userWithMatchingEmail == null){
            userRepository.save(user);
            return true;
        }
        //else return false
        return false;
    }
}
