package kkramarenko.ecommerceapp.repository;

import kkramarenko.ecommerceapp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:4200")
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findCustomerById(Long customerId);

    Customer findCustomerByFirstNameAndLastNameAndEmail(String firstName, String lastName, String email);
}
