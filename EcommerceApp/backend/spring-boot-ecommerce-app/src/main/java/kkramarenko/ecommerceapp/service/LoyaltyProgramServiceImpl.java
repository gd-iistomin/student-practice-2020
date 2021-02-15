package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.entity.LoyaltyProgramStatus;
import kkramarenko.ecommerceapp.entity.User;
import kkramarenko.ecommerceapp.repository.LoyaltyProgramStatusRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoyaltyProgramServiceImpl implements LoyaltyProgramService {


    private final LoyaltyProgramStatusRepository loyaltyProgramStatusRepository;


    public LoyaltyProgramServiceImpl(LoyaltyProgramStatusRepository loyaltyProgramStatusRepository) {
        this.loyaltyProgramStatusRepository = loyaltyProgramStatusRepository;
    }

    /**
     * Used after new purchase for some user is saved to database
     * <p>
     * Checks if sum of all user orders' totalPrice is more than some value,
     * return user's current loyalty program status based on that
     *
     * @param user - user to check discount rate
     * @return LoyaltyProgramStatus
     * @see LoyaltyProgramStatus
     */
    @Override
    public LoyaltyProgramStatus getUserCurrentDiscountRate(User user) {


        BigDecimal userPurchaseTotal = user.getPurchaseTotal();
        String discountRate = user.getDiscountRate();

        List<LoyaltyProgramStatus> loyaltyProgramStatuses = loyaltyProgramStatusRepository.findAll()
                .stream().sorted(Comparator.comparing(LoyaltyProgramStatus::getTargetPurchaseTotal))
                .collect(Collectors.toList());



        for (LoyaltyProgramStatus status : loyaltyProgramStatuses) {
            if (userPurchaseTotal.compareTo(status.getTargetPurchaseTotal()) > 0) {
                discountRate = status.getStatusName();
            }
        }

        return loyaltyProgramStatusRepository.getByStatusName(discountRate);


    }
}
