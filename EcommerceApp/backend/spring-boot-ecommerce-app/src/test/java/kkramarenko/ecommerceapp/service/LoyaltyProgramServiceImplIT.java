package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.entity.Customer;
import kkramarenko.ecommerceapp.entity.Order;
import kkramarenko.ecommerceapp.entity.OrderItem;
import kkramarenko.ecommerceapp.enums.DiscountRate;
import kkramarenko.ecommerceapp.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Loyalty Program Service Test")
class LoyaltyProgramServiceImplIT {


    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    LoyaltyProgramService loyaltyProgramService;

    Customer customer;
    Customer customer2;
    Customer customer3;
    OrderItem orderItem;
    OrderItem orderItem2;
    Order order;
    Order order2;
    Order order3;


    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Walker");
        customer.setEmail("test");

        customer2 = new Customer();
        customer2.setFirstName("Jake");
        customer2.setLastName("White");
        customer2.setEmail("test2");

        customer3 = new Customer();
        customer3.setFirstName("Ann");
        customer3.setLastName("Li");
        customer3.setEmail("test3");



        orderItem = new OrderItem();
        orderItem.setName("Test");
        orderItem.setImageUrl("cugvuyecguwvj");
        orderItem.setUnitPrice(BigDecimal.valueOf(90));
        orderItem.setQuantity(1);
        orderItem.setProductId(1L);

        orderItem2 = new OrderItem();
        orderItem2.setName("Test2");
        orderItem2.setImageUrl("vkhweukv");
        orderItem2.setUnitPrice(BigDecimal.valueOf(170));
        orderItem2.setQuantity(1);
        orderItem2.setProductId(2L);

        order = new Order();
        order.setTotalQuantity(1);
        order.add(orderItem);
        order.setTotalPrice(orderItem.getUnitPrice());

        order2 = new Order();
        order2.setTotalQuantity(1);
        order2.add(orderItem2);
        order2.setTotalPrice(orderItem2.getUnitPrice());

        order3 = new Order();
        order3.setTotalQuantity(2);
        order3.add(orderItem);
        order3.add(orderItem2);
        order3.setTotalPrice(orderItem.getUnitPrice().add(orderItem2.getUnitPrice()));


        customer.add(order);
        customer2.add(order2);
        customer3.add(order3);

        customerRepository.save(customer);
        customerRepository.save(customer2);
        customerRepository.save(customer3);


    }

    @Test
    @DisplayName("Customer discount rate is returned properly")
    void getCustomerCurrentDiscountRate() {

        assertEquals(DiscountRate.STARTER, loyaltyProgramService.getCustomerCurrentDiscountRate(customer));
        assertEquals(DiscountRate.BRONZE, loyaltyProgramService.getCustomerCurrentDiscountRate(customer2));
        assertEquals(DiscountRate.SILVER, loyaltyProgramService.getCustomerCurrentDiscountRate(customer3));

    }

    @AfterEach
    void tearDown() {
    }
}