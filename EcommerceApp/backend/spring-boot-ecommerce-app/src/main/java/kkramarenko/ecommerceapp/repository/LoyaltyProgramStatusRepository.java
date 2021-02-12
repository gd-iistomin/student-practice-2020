package kkramarenko.ecommerceapp.repository;

import kkramarenko.ecommerceapp.entity.LoyaltyProgramStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoyaltyProgramStatusRepository extends JpaRepository<LoyaltyProgramStatus, Integer> {

    LoyaltyProgramStatus getByStatusName(String statusName);

}
