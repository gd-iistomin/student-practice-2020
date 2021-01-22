package kkramarenko.ecommerceapp.controller;

import kkramarenko.ecommerceapp.entity.Customer;
import kkramarenko.ecommerceapp.entity.Order;
import kkramarenko.ecommerceapp.repository.CustomerRepository;
import kkramarenko.ecommerceapp.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/orders")
public class UserOrdersController {

    private final CustomerRepository customerRepository;

    private final OrderRepository orderRepository;

    public UserOrdersController(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/{customerId}")
    public List<Order> getUserOrders(@PathVariable Long customerId){
        Customer targetCustomer = customerRepository.findCustomerById(customerId);

        if(targetCustomer == null){ return null; }

        return orderRepository.findOrdersByCustomer(targetCustomer);


    }
}
