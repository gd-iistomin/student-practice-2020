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
        // get Customer by customerId
        Customer targetCustomer = customerRepository.findCustomerById(customerId);

        List<Order> userOrdersList = new ArrayList<>();

        // get List of orders by customer, if customer with such id exists
        if(targetCustomer != null){
            userOrdersList = orderRepository.findOrdersByCustomer(targetCustomer);
        }

        // return list of orders
        return  userOrdersList;

    }
}
