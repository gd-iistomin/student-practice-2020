package kkramarenko.ecommerceapp.repository;

import kkramarenko.ecommerceapp.entity.Customer;
import kkramarenko.ecommerceapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByEmail(String userEmail);

    User findUserByUsername(String username);

    User findUserByCustomer(Customer customer);

}
