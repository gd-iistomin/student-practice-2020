package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.entity.Customer;
import kkramarenko.ecommerceapp.entity.User;
import kkramarenko.ecommerceapp.enums.UserRegistrationStatus;
import kkramarenko.ecommerceapp.repository.CustomerRepository;
import kkramarenko.ecommerceapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Service Test")
class UserServiceImplTest {

    @Spy
    PasswordEncoder passwordEncoder;

    @Mock
    UserRepository userRepository;

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Captor
    ArgumentCaptor<Customer> customerArgumentCaptor;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    User user1;

    User user2;

    private final static String DISCOUNT_RATE_BRONZE = "BRONZE";
    private final static String DISCOUNT_RATE_SILVER = "SILVER";

    @BeforeEach
    void setUp() {

        user1 = new User();
        user1.setId(3);
        user1.setFirstName("Jack");
        user1.setLastName("Pepper");
        user1.setEmail("fbjhv@gycg.com");
        user1.setUsername("test1");
        user1.setPassword("bchbYGY65r");
        user1.setAuthority("USER");
        user1.setDiscountRate(DISCOUNT_RATE_BRONZE);


        user2 = new User();
        user2.setId(7);
        user2.setFirstName("Mary");
        user2.setLastName("Lee");
        user2.setEmail("testemail@test.com");;
        user2.setUsername("hjvcv");
        user2.setPassword("bHVJC$24");
        user2.setAuthority("USER");
        user2.setDiscountRate(DISCOUNT_RATE_SILVER);

    }

//    todo complete test
    @Test
    @DisplayName("Test user registration")
    void registerUser() {
        User testUser1 = new User();
        testUser1.setUsername("test1");
        testUser1.setEmail("testemail@test.com");

        User testUser2 = new User();
        testUser2.setUsername("bjsdb");
        testUser2.setEmail("testemail@test.com");

        User testUser3 = new User();
        testUser3.setUsername("test1");
        testUser3.setEmail("user2@test.com");

        User testUser4 = new User();
        testUser4.setUsername("bjsdb");
        testUser4.setEmail("gjvgy@tfyt.com");

        when(userRepository.findUserByUsername(any())).thenAnswer(
                invocationOnMock -> {
                    String username = invocationOnMock.getArgument(0);

                    if (username.equals(user1.getUsername())){
                        return user1;
                    }

                    return null;
                });

        when(userRepository.findUserByEmail(any())).thenAnswer(
                invocationOnMock -> {
                    String email = invocationOnMock.getArgument(0);

                    if (email.equals(user2.getEmail())){
                        return user2;
                    }

                    return null;
                });

        when(customerRepository.save(customerArgumentCaptor.capture())).thenReturn(null);

        when(passwordEncoder.encode(any())).thenReturn("fgdcyguyg2t78t7d8c");

        when(userRepository.save(userArgumentCaptor.capture())).thenReturn(null);

        assertAll(
                () -> assertEquals(UserRegistrationStatus.FOUND_USER_WITH_MATCHING_USERNAME,
                                            userService.registerUser(testUser1)),
                () -> assertEquals(UserRegistrationStatus.FOUND_USER_WITH_MATCHING_EMAIL,
                                            userService.registerUser(testUser2)),
                () -> assertEquals(UserRegistrationStatus.FOUND_USER_WITH_MATCHING_USERNAME,
                                            userService.registerUser(testUser3)),
                () -> assertEquals(UserRegistrationStatus.OK, userService.registerUser(testUser4))
            );





    }

    @Test
    void getUserDetails() {
    }
}