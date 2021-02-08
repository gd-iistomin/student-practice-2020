package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.entity.Customer;
import kkramarenko.ecommerceapp.entity.Order;
import kkramarenko.ecommerceapp.enums.DiscountRate;
import kkramarenko.ecommerceapp.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LoyaltyProgramServiceImpl implements LoyaltyProgramService {

    private final OrderRepository orderRepository;

    public LoyaltyProgramServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Used after new purchase for some customer is saved to database
     * <p>
     * Checks if sum of all customer orders' totalPrice is more than some value,
     * return customer's current discount rate based on that
     *
     * @param customer - customer to check discount rate
     * @return DiscountRate
     * @see DiscountRate
     */
    @Override
    public DiscountRate getCustomerCurrentDiscountRate(Customer customer) {

        List<Order> customerOrders = orderRepository.findOrdersByCustomer(customer);

        if (customerOrders.isEmpty()) {
            return DiscountRate.STARTER;
        }

        BigDecimal customerOrdersTotalPriceSum = new BigDecimal("0.00");
        for (Order order : customerOrders) {
            customerOrdersTotalPriceSum = customerOrdersTotalPriceSum.add(order.getTotalPrice());
        }

        if (customerOrdersTotalPriceSum.compareTo(DiscountRate.BRONZE.getTargetPurchasesSum()) < 0) {
            return DiscountRate.STARTER;
        } else if (customerOrdersTotalPriceSum.compareTo(DiscountRate.SILVER.getTargetPurchasesSum()) < 0) {
            return DiscountRate.BRONZE;
        } else if (customerOrdersTotalPriceSum.compareTo(DiscountRate.GOLD.getTargetPurchasesSum()) < 0) {
            return DiscountRate.SILVER;
        } else if (customerOrdersTotalPriceSum.compareTo(DiscountRate.PLATINUM.getTargetPurchasesSum()) < 0) {
            return DiscountRate.GOLD;
        } else {
            return DiscountRate.PLATINUM;
        }

    }
}
