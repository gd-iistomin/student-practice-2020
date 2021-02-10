package kkramarenko.ecommerceapp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "loyalty_program_status")
@Getter
@Setter
public class LoyaltyProgramStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "status_name")
    private String statusName;

    @Column(name = "target_purchase_total")
    private BigDecimal targetPurchaseTotal;

}
