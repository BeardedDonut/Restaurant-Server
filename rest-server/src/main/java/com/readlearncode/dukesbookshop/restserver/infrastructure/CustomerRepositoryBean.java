package com.readlearncode.dukesbookshop.restserver.infrastructure;

import com.readlearncode.dukesbookshop.restserver.domain.Customer;
import com.readlearncode.dukesbookshop.restserver.infrastructure.repositories.CustomerRepository;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

/**
 * Created by navid on 11/24/17.
 */

@Stateless
public class CustomerRepositoryBean implements CustomerRepository {
    public static int numberOfCustomers = 1;

    private HashMap<Integer, Customer> customersMap = new HashMap<>();

    public static int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public static void setNumberOfCustomers(int numberOfCustomers) {
        CustomerRepositoryBean.numberOfCustomers = numberOfCustomers;
    }

    public Optional<Customer> createNewProfile(final String fullName, final String telephoneNumber) {
        Customer newCs = new Customer(++numberOfCustomers, fullName, telephoneNumber);
        customersMap.put(newCs.getCustomerId(), newCs);
        return Optional.of(newCs);
    }

    @Override
    public Optional<Customer> getCustomerById(final int id) {
        return Optional.ofNullable(customersMap.get(id));
    }

    @Override
    public ArrayList<Customer> getAllCustomers() {
        return new ArrayList<>(customersMap.values());
    }

    @Override
    public Optional<Customer> createNewProfile(Customer newCs) {
        Optional<Customer> registeredCustomer = this.createNewProfile(newCs.getFullName(), newCs.getPhoneNumber());
        return registeredCustomer;
    }

    @Override
    public Optional<Customer> getCustomerByTel(String telNumber) {
        ArrayList<Customer> allCs = new ArrayList<>(customersMap.values());

        Customer foundOne = null;

        for (Customer cs : allCs) {
            if (cs.getPhoneNumber().equals(telNumber)) {
                foundOne = cs;
                return Optional.of(foundOne);
            }
        }

        return Optional.ofNullable(foundOne);
    }
}
