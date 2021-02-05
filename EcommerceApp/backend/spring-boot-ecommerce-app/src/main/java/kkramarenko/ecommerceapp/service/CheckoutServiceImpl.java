package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.dto.Purchase;
import kkramarenko.ecommerceapp.dto.PurchaseResponse;
import kkramarenko.ecommerceapp.entity.Address;
import kkramarenko.ecommerceapp.entity.Customer;
import kkramarenko.ecommerceapp.entity.Order;
import kkramarenko.ecommerceapp.entity.OrderItem;
import kkramarenko.ecommerceapp.entity.User;
import kkramarenko.ecommerceapp.enums.DiscountRate;
import kkramarenko.ecommerceapp.enums.OrderStatus;
import kkramarenko.ecommerceapp.repository.AddressRepository;
import kkramarenko.ecommerceapp.repository.CustomerRepository;
import kkramarenko.ecommerceapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CustomerRepository customerRepository;

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    private final LoyaltyProgramService loyaltyProgramService;

    public CheckoutServiceImpl(CustomerRepository customerRepository, AddressRepository addressRepository,
                               UserRepository userRepository, LoyaltyProgramService loyaltyProgramService) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.loyaltyProgramService = loyaltyProgramService;
    }

    /**
     * @param purchase - purchase dto coming from frontend
     * @return PurchaseResponse - contains orderTrackingNumber, boolean(flag) discountRateChanged
     * <p>
     * Gets dto from frontend, populates entities based on dto,
     * checks addresses to reuse existing entries in db,
     * saves info to database,
     * checks current discount rate for customer, if it has changed,
     * sets flag to true, so frontend will inform customer about that(via alert)
     * <p>
     * We provide loyalty program only for registered users.
     * <p>
     * <p>
     * returns response with generated tracking number, flag if discount rate has changed, and new discount rate
     * discount rate is represented by String value of enum, if present, or empty string '' otherwise
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

        boolean discountRateChanged = false;
        String newDiscountRate = "";

        if (existingCustomer != null) {
            User userWithGivenCustomer = userRepository.findUserByCustomer(existingCustomer);
            if (userWithGivenCustomer == null) {
                existingCustomer.add(order);
                customerRepository.save(existingCustomer);
            } else {
                DiscountRate discountRateBeforePurchase = DiscountRate.valueOf(userWithGivenCustomer.getDiscountRate());
                existingCustomer.add(order);
                customerRepository.save(existingCustomer);
                DiscountRate discountRateAfterPurchase =
                        loyaltyProgramService.getCustomerCurrentDiscountRate(existingCustomer);
                if (!(discountRateAfterPurchase.equals(discountRateBeforePurchase))) {
                    discountRateChanged = true;
                    newDiscountRate = discountRateAfterPurchase.toString();
                    userWithGivenCustomer.setDiscountRate(discountRateAfterPurchase.toString());
                }
            }
        } else {
            // no need to use LoyaltyProgram service here, as if user has registered,
            // customer entity is created according to user's fields
            customer.add(order);
            customerRepository.save(customer);
        }


        return new PurchaseResponse(orderTrackingNumber, discountRateChanged, newDiscountRate);
    }

    private String generateOrderTrackingNumber() {

        return UUID.randomUUID().toString();
    }

    private Address getExistingOrNewSavedAddress(Address address) {

        Address existingAddress = addressRepository.findAddressByCityAndCountryAndStateAndStreetAndZipCode(
                address.getCity(), address.getCountry(), address.getState(), address.getStreet(), address.getZipCode());

        if (existingAddress != null) {
            return existingAddress;
        }

        return addressRepository.save(address);

    }


}
