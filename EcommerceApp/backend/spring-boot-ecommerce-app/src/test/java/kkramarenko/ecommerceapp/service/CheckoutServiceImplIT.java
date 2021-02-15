package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.dto.Purchase;
import kkramarenko.ecommerceapp.dto.PurchaseResponse;
import kkramarenko.ecommerceapp.entity.Address;
import kkramarenko.ecommerceapp.entity.Customer;
import kkramarenko.ecommerceapp.entity.Order;
import kkramarenko.ecommerceapp.entity.OrderItem;
import kkramarenko.ecommerceapp.repository.AddressRepository;
import kkramarenko.ecommerceapp.repository.CustomerRepository;
import kkramarenko.ecommerceapp.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
@ActiveProfiles("test")
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

    Customer customer;
    OrderItem orderItem;
    Order order;
    Address address;
    Purchase purchase;

    @BeforeEach
    void setUp(){
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Walker");
        customer.setEmail("test");
        customer.setOrders(null);

        customerRepository.save(customer);

        orderItem = new OrderItem();
        orderItem.setName("Test");
        orderItem.setImageUrl("cugvuyecguwvj");
        orderItem.setUnitPrice(BigDecimal.valueOf(120));
        orderItem.setQuantity(1);
        orderItem.setProductId(1L);

        order = new Order();
        order.setTotalQuantity(1);
        order.add(orderItem);
        order.setTotalPrice(BigDecimal.ONE);

        address = new Address();
        address.setCountry("Russia");
        address.setState("Moscow");
        address.setCity("Moscow");
        address.setStreet("Lenina");
        address.setZipCode("675426");

        purchase = new Purchase();
        purchase.setCustomer(customer);
        purchase.setBillingAddress(address);
        purchase.setShippingAddress(address);
        purchase.setOrderItems(Set.of(orderItem));
        purchase.setOrder(order);
    }


    @Test
    void placeOrder() {

        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);

        assertThat(addressRepository.findAll()).hasSize(1);
        assertFalse(purchaseResponse.isDiscountRateChanged());
        assertEquals("", purchaseResponse.getNewDiscountRate());

    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
        addressRepository.deleteAll();
        userRepository.deleteAll();

    }
}