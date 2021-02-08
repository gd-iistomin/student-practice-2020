package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.entity.Address;
import kkramarenko.ecommerceapp.entity.Customer;
import kkramarenko.ecommerceapp.entity.Order;
import kkramarenko.ecommerceapp.entity.OrderItem;
import kkramarenko.ecommerceapp.repository.AddressRepository;
import kkramarenko.ecommerceapp.repository.CustomerRepository;
import kkramarenko.ecommerceapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;


@SpringBootTest
class CheckoutServiceImplIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoyaltyProgramService loyaltyProgramService;

    @Autowired
    CheckoutService checkoutService;


    @BeforeAll
    void beforeAll(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Walker");
        customer.setEmail("test");
        customer.setOrders(null);

        customerRepository.save(customer);

        OrderItem orderItem = new OrderItem();
        orderItem.setName("Test");
        orderItem.setImageUrl("cugvuyecguwvj");
        orderItem.setUnitPrice(BigDecimal.ONE);
        orderItem.setQuantity(1);
        orderItem.setProductId(1L);

        Order order = new Order();
        order.setTotalQuantity(1);
        order.add(orderItem);
        order.setTotalPrice(BigDecimal.ONE);

        Address address = new Address();
        address.setCity("Moscow");
    }


    @Test
    void placeOrder() {
        


    }
}