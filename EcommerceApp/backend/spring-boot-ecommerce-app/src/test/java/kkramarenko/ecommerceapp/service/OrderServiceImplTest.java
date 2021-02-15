package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.dto.Purchase;
import kkramarenko.ecommerceapp.entity.Address;
import kkramarenko.ecommerceapp.entity.Customer;
import kkramarenko.ecommerceapp.entity.Order;
import kkramarenko.ecommerceapp.entity.OrderItem;
import kkramarenko.ecommerceapp.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("Order service test")
@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    void getOrderDetailsByTrackingNumber() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setName("TestItem");
        orderItem.setProductId(1L);
        orderItem.setUnitPrice(BigDecimal.TEN);
        orderItem.setQuantity(2);

        Address address = new Address();
        address.setId(1L);
        address.setCountry("Russia");
        address.setState("Moscow");
        address.setCity("Moscow");
        address.setStreet("Tverskaya");
        address.setZipCode("876327");

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Ronald");
        customer.setLastName("McKinsey");
        customer.setEmail("rmc@yahoo.com");

        Order order = new Order();
        order.setId(1L);
        order.setOrderItems(Set.of(orderItem));
        order.setTotalQuantity(orderItem.getQuantity());
        order.setTotalPrice(orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        order.setOrderTrackingNumber("testtrack");
        order.setShippingAddress(address);
        order.setBillingAddress(address);
        order.setCustomer(customer);
        order.setStatus("CREATED");

        when(orderRepository.findByOrderTrackingNumber(order.getOrderTrackingNumber())).thenReturn(order);

        Purchase result = orderService.getOrderDetailsByTrackingNumber(order.getOrderTrackingNumber());

        assertAll(
                () -> assertEquals(result.getCustomer(), customer),
                () -> assertEquals(result.getShippingAddress(), address),
                () -> assertEquals(result.getBillingAddress(), address),
                () -> assertEquals(result.getOrder(), order)
        );

    }
}