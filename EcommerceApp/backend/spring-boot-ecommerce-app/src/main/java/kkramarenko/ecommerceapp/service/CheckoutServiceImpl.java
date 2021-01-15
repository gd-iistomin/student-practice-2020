package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.dto.Purchase;
import kkramarenko.ecommerceapp.dto.PurchaseResponse;
import kkramarenko.ecommerceapp.entity.Customer;
import kkramarenko.ecommerceapp.entity.Order;
import kkramarenko.ecommerceapp.entity.OrderItem;
import kkramarenko.ecommerceapp.enums.OrderStatusEnum;
import kkramarenko.ecommerceapp.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // retrieve the order info from dto
        Order order = purchase.getOrder();

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(orderItem -> order.add(orderItem));

        // populate order with billingAddress and shippingAddress
        order.setShippingAddress(purchase.getShippingAddress());
        order.setBillingAddress(purchase.getBillingAddress());
        // set order status to "created"
        order.setStatus(OrderStatusEnum.CREATED.toString());

        //get customer
        Customer customer = purchase.getCustomer();

        // check if such customer exists
        Customer existingCustomer = customerRepository.findCustomerByFirstNameAndLastNameAndEmail(
                    customer.getFirstName(), customer.getLastName(), customer.getEmail());

        // if customer exists, add him new order and save
        if(existingCustomer != null){
            existingCustomer.add(order);
            customerRepository.save(existingCustomer);
        } else {
            // else populate new customer with order
            customer.add(order);
            // save to db
            customerRepository.save(customer);
        }



        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {

        // generate a random UUID number
        // chance of Collision is negligible
         return UUID.randomUUID().toString();
    }
}
