package kkramarenko.ecommerceapp.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JsonTest
@Tag("Jsontest")
@DisplayName("Test Customer JSON")
class CustomerTest {

    @Autowired
    private JacksonTester<Customer> jacksonTester;

    @Test
    @DisplayName("Test output JSON is created properly")
    void testSerialize() throws IOException {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Test");
        customer.setLastName("Customer");
        customer.setEmail("testmail@test.com");

        Order order = new Order();
        order.setStatus("Test");

        customer.add(order);

        JsonContent<Customer> resultJSON = jacksonTester.write(customer);

        System.out.println(resultJSON);

        assertAll(
                () -> assertThat(resultJSON).hasJsonPath("$.id"),
                () -> assertThat(resultJSON).hasJsonPath("$.firstName"),
                () -> assertThat(resultJSON).hasJsonPath("$.lastName"),
                () -> assertThat(resultJSON).hasJsonPath("$.email"),
                () -> assertThat(resultJSON).doesNotHaveJsonPath("$.orders")
        );
    }
}