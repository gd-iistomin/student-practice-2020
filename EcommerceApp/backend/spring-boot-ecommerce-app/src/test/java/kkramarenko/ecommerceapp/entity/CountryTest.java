package kkramarenko.ecommerceapp.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JsonTest
@Tag("Jsontest")
@DisplayName("Test Country JSON")
class CountryTest {

    @Autowired
    private JacksonTester<Country> jacksonTester;

    @Test
    @DisplayName("Test output JSON is created properly")
    void testSerialize() throws IOException {
        State teststate = new State();
        teststate.setName("Test state");

        List<State> states = List.of(teststate);

        Country country = new Country();
        country.setId(1);
        country.setCode("RU");
        country.setName("RUSSIA");
        country.setStates(states);

        JsonContent<Country> resultJSON = jacksonTester.write(country);

        System.out.println(resultJSON);
        assertAll(
                () -> assertThat(resultJSON).hasJsonPath("$.id"),
                () -> assertThat(resultJSON).hasJsonPath("$.code"),
                () -> assertThat(resultJSON).hasJsonPath("$.name"),
                () -> assertThat(resultJSON).doesNotHaveJsonPath("$.states"));



    }
}