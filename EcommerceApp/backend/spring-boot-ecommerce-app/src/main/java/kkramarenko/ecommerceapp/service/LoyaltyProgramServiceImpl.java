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
     *
     * Checks if sum of all customer orders' totalPrice is more than some value,
     * return customer's current discount rate based on that
     * @see DiscountRate
     *
     * @param customer - customer to check discount rate
     * @return DiscountRate
     */
    @Override
    public DiscountRate getCustomerCurrentDiscountRate(Customer customer) {

        List<Order> customerOrders = orderRepository.findOrdersByCustomer(customer);

        if(customerOrders.size() == 0) { return DiscountRate.starter; }

        BigDecimal customerOrdersTotalPriceSum = new BigDecimal("0.00");
        for(Order order: customerOrders){
            customerOrdersTotalPriceSum = customerOrdersTotalPriceSum.add(order.getTotalPrice());
        }

        if(customerOrdersTotalPriceSum.compareTo(new BigDecimal("100.00")) < 0){
            return DiscountRate.starter;
        } else if (customerOrdersTotalPriceSum.compareTo(new BigDecimal("250.00")) < 0) {
            return DiscountRate.bronze;
        } else if (customerOrdersTotalPriceSum.compareTo(new BigDecimal("500.00")) < 0) {
            return DiscountRate.silver;
        } else if (customerOrdersTotalPriceSum.compareTo(new BigDecimal("1000.00")) < 0) {
            return DiscountRate.gold;
        } else {
            return DiscountRate.platinum;
        }

    }
}
