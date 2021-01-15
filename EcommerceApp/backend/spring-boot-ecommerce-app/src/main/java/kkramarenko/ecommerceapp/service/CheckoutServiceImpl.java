package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.dto.Purchase;
import kkramarenko.ecommerceapp.dto.PurchaseResponse;
import kkramarenko.ecommerceapp.entity.Customer;
import kkramarenko.ecommerceapp.entity.Order;
import kkramarenko.ecommerceapp.entity.OrderItem;
import kkramarenko.ecommerceapp.enums.OrderStatus;
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


    /**
     * @param purchase - purchase dto coming from frontend
     * @return PurchaseResponse - contains orderTrackingNumber
     *
     *  Gets dto from frontend, populates entities based on dto, saves order to database
     */
    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        Order order = purchase.getOrder();

        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(orderItem -> order.add(orderItem));

        order.setShippingAddress(purchase.getShippingAddress());
        order.setBillingAddress(purchase.getBillingAddress());
        order.setStatus(OrderStatus.CREATED.toString());

<<<<<<< HEAD
=======
        //get customer
>>>>>>> Added feature to view orders for logged in users
        Customer customer = purchase.getCustomer();

<<<<<<< HEAD
        customerRepository.save(customer);
=======
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


>>>>>>> Added feature to view orders for logged in users

        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {

         return UUID.randomUUID().toString();
    }
}
