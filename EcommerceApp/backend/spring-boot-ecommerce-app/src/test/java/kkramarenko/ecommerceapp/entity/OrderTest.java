package kkramarenko.ecommerceapp.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JsonTest
@Tag("Jsontest")
@DisplayName("Test Order JSON")
class OrderTest {

    @Autowired
    private JacksonTester<Order> jacksonTester;

    @Test
    @DisplayName("Test output JSON is created properly")
    void testSerialize() throws IOException {
        Order order = new Order();
        order.setId(1L);
        order.setOrderTrackingNumber("test6481273track");
        order.setTotalPrice(BigDecimal.valueOf(8.9));
        order.setTotalQuantity(23);
        order.setStatus("Test");
        order.setDateCreated(Date.from(Instant.now()));
        order.setLastUpdated(Date.from(Instant.now()));

        OrderItem orderItem = new OrderItem();
        orderItem.setId(2L);
        order.add(orderItem);

        Customer customer = new Customer();
        customer.setId(32L);
        order.setCustomer(customer);

        Address address = new Address();
        address.setId(5L);
        order.setShippingAddress(address);
        order.setBillingAddress(address);

        JsonContent<Order> resultJSON = jacksonTester.write(order);

        System.out.println(resultJSON);
        assertAll(
                () -> assertThat(resultJSON).hasJsonPath("$.id"),
                () -> assertThat(resultJSON).hasJsonPath("$.orderTrackingNumber"),
                () -> assertThat(resultJSON).hasJsonPath("$.totalPrice"),
                () -> assertThat(resultJSON).hasJsonPath("$.totalQuantity"),
                () -> assertThat(resultJSON).hasJsonPath("$.status"),
                () -> assertThat(resultJSON).hasJsonPath("$.dateCreated"),
                () -> assertThat(resultJSON).hasJsonPath("$.lastUpdated"),
                () -> assertThat(resultJSON).doesNotHaveJsonPath("$.orderItems"),
                () -> assertThat(resultJSON).doesNotHaveJsonPath("$.customer"),
                () -> assertThat(resultJSON).doesNotHaveJsonPath("$.shippingAddress"),
                () -> assertThat(resultJSON).doesNotHaveJsonPath("$.billingAddress")
                );

    }
}