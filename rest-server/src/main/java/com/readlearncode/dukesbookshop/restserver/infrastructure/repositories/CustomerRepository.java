package com.readlearncode.dukesbookshop.restserver.infrastructure.repositories;

import com.readlearncode.dukesbookshop.restserver.domain.Customer;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by navid on 11/24/17.
 */

public interface CustomerRepository {

    Optional<Customer> createNewProfile(final String fullName, final String telephoneNumber);

    Optional<Customer> getCustomerById(final int id);

    ArrayList<Customer> getAllCustomers();

    Optional<Customer> createNewProfile(final Customer newCs);

    Optional<Customer> getCustomerByTel(final String telNumber);

}
