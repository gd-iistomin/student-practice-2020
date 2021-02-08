package kkramarenko.ecommerceapp.repository;

import kkramarenko.ecommerceapp.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findAddressByCityAndCountryAndStateAndStreetAndZipCode(String city, String country,
                                                                   String state, String street, String zipcode);

}
