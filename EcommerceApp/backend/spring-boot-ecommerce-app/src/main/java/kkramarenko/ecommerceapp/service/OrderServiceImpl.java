package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.dto.Purchase;
import kkramarenko.ecommerceapp.entity.Address;
import kkramarenko.ecommerceapp.entity.Customer;
import kkramarenko.ecommerceapp.entity.Order;
import kkramarenko.ecommerceapp.entity.OrderItem;
import kkramarenko.ecommerceapp.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * @param orderTrackingNumber
     * @return Purchase - dto
     *
     * Gets target order from repo by tracking number,
     * collects data about that order in purchase dto,
     * returns dto
     */
    @Override
    public Purchase getOrderDetailsByTrackingNumber(String orderTrackingNumber) {

        Purchase currentPurchase = new Purchase();

        Order currentOrder = orderRepository.findByOrderTrackingNumber(orderTrackingNumber);
        currentPurchase.setOrder(currentOrder);

        Customer currentCustomer = currentOrder.getCustomer();
        currentPurchase.setCustomer(currentCustomer);

        Address currentShippingAddress = currentOrder.getShippingAddress();
        Address currentBillingAddress = currentOrder.getBillingAddress();
        currentPurchase.setShippingAddress(currentShippingAddress);
        currentPurchase.setBillingAddress(currentBillingAddress);

        Set<OrderItem> currentItems = currentOrder.getOrderItems();
        currentPurchase.setOrderItems(currentItems);

        return currentPurchase;
    }
}
