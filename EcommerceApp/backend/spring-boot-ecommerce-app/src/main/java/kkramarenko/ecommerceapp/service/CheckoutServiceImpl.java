package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.dto.Purchase;
import kkramarenko.ecommerceapp.dto.PurchaseResponse;
import kkramarenko.ecommerceapp.entity.Address;
import kkramarenko.ecommerceapp.entity.Customer;
import kkramarenko.ecommerceapp.entity.Order;
import kkramarenko.ecommerceapp.entity.OrderItem;
import kkramarenko.ecommerceapp.enums.OrderStatus;
import kkramarenko.ecommerceapp.repository.AddressRepository;
import kkramarenko.ecommerceapp.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CustomerRepository customerRepository;

    private final AddressRepository addressRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    /**
     * @param purchase - purchase dto coming from frontend
     * @return PurchaseResponse - contains orderTrackingNumber
     *
     *  Gets dto from frontend, populates entities based on dto,
     *  checks addresses to reuse existing entries in db,
     *  saves info to database, returns response with generated tracking number
     */
    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        Order order = purchase.getOrder();

        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(orderItem -> order.add(orderItem));

        Address shippingAddress = purchase.getShippingAddress();
        Address billingAddress = purchase.getBillingAddress();

        order.setShippingAddress(getExistingOrNewSavedAddress(shippingAddress));
        order.setBillingAddress(getExistingOrNewSavedAddress(billingAddress));


        order.setStatus(OrderStatus.CREATED.toString());


        Customer customer = purchase.getCustomer();

        Customer existingCustomer = customerRepository.findCustomerByFirstNameAndLastNameAndEmail(
                    customer.getFirstName(), customer.getLastName(), customer.getEmail());

        if(existingCustomer != null){
            existingCustomer.add(order);
            customerRepository.save(existingCustomer);
        } else {
            customer.add(order);
            customerRepository.save(customer);
        }



        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {

         return UUID.randomUUID().toString();
    }

    private Address getExistingOrNewSavedAddress(Address address){

        Address existingAddress = addressRepository.findAddressByCityAndCountryAndStateAndStreetAndZipCode(
                address.getCity(), address.getCountry(), address.getState(), address.getStreet(), address.getZipCode());

        if (existingAddress != null){ return existingAddress; }

        return addressRepository.save(address);

    }


}
