package kkramarenko.ecommerceapp.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

@JsonTest
@Tag("Jsontest")
@DisplayName("Test Address JSON")
class AddressTest {

    @Autowired
    private JacksonTester<Address> jacksonTester;

    @Test
    @DisplayName("Test output JSON is created properly")
    void testSerialize() throws IOException {
        Order order = new Order();
        order.setStatus("Test");

        Address address = new Address();
        address.setId(1L);
        address.setCountry("Russia");
        address.setState("Moscow");
        address.setCity("Moscow");
        address.setStreet("Lenina");
        address.setZipCode("489516");
        address.setOrder(order);

        JsonContent<Address> resultJSON = jacksonTester.write(address);

        System.out.println(resultJSON);
        assertAll(
                () -> assertThat(resultJSON).hasJsonPath("$.id"),
                () -> assertThat(resultJSON).hasJsonPath("$.country"),
                () -> assertThat(resultJSON).hasJsonPath("$.state"),
                () -> assertThat(resultJSON).hasJsonPath("$.city"),
                () -> assertThat(resultJSON).hasJsonPath("$.street"),
                () -> assertThat(resultJSON).hasJsonPath("$.zipCode"),
                () -> assertThat(resultJSON).doesNotHaveJsonPath("$.order"));

    }
}