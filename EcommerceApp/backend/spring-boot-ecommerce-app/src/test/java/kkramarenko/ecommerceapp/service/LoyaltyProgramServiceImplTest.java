package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.entity.LoyaltyProgramStatus;
import kkramarenko.ecommerceapp.entity.User;
import kkramarenko.ecommerceapp.repository.LoyaltyProgramStatusRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Loyalty program Service Test")
class LoyaltyProgramServiceImplTest {

    @Mock
    LoyaltyProgramStatusRepository loyaltyProgramStatusRepository;

    @InjectMocks
    LoyaltyProgramServiceImpl loyaltyProgramService;

    @Test
    void getUserCurrentDiscountRate() {
        LoyaltyProgramStatus statusSTARTER = new LoyaltyProgramStatus();
        statusSTARTER.setId(1);
        statusSTARTER.setStatusName("STARTER");
        statusSTARTER.setTargetPurchaseTotal(BigDecimal.valueOf(0L));

        LoyaltyProgramStatus statusBRONZE = new LoyaltyProgramStatus();
        statusBRONZE.setId(2);
        statusBRONZE.setStatusName("BRONZE");
        statusBRONZE.setTargetPurchaseTotal(BigDecimal.valueOf(100L));

        LoyaltyProgramStatus statusSILVER = new LoyaltyProgramStatus();
        statusSILVER.setId(3);
        statusSILVER.setStatusName("SILVER");
        statusSILVER.setTargetPurchaseTotal(BigDecimal.valueOf(250L));

        LoyaltyProgramStatus statusGOLD = new LoyaltyProgramStatus();
        statusGOLD.setId(4);
        statusGOLD.setStatusName("GOLD");
        statusGOLD.setTargetPurchaseTotal(BigDecimal.valueOf(500L));

        LoyaltyProgramStatus statusPLATINUM = new LoyaltyProgramStatus();
        statusPLATINUM.setId(5);
        statusPLATINUM.setStatusName("PLATINUM");
        statusPLATINUM.setTargetPurchaseTotal(BigDecimal.valueOf(1000L));

        List<LoyaltyProgramStatus> statuses = List.of(statusSILVER, statusGOLD, statusBRONZE, statusPLATINUM, statusSTARTER);

        when(loyaltyProgramStatusRepository.findAll()).thenReturn(statuses);

        when(loyaltyProgramStatusRepository.getByStatusName(any())).thenAnswer(
                invocation -> {
                    String statusName = invocation.getArgument(0);

                    switch (statusName) {
                        case "STARTER":
                            return statusSTARTER;

                        case "BRONZE":
                            return statusBRONZE;

                        case "SILVER":
                            return statusSILVER;

                        case "GOLD":
                            return statusGOLD;

                        case "PLATINUM":
                            return statusPLATINUM;

                        default:
                            return statusSTARTER;
                    }
                }
        );


        User user1 = new User();
        user1.setId(1);
        user1.setUsername("test1");
        user1.setDiscountRate("STARTER");
        user1.setPurchaseTotal(BigDecimal.valueOf(560L));

        User user2 = new User();
        user2.setId(2);
        user2.setUsername("test2");
        user2.setDiscountRate("SILVER");
        user2.setPurchaseTotal(BigDecimal.valueOf(340L));


        assertAll(
                () -> assertEquals(statusGOLD, loyaltyProgramService.getUserCurrentDiscountRate(user1)),
                () -> assertEquals(statusSILVER, loyaltyProgramService.getUserCurrentDiscountRate(user2))
        );
    }
}