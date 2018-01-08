package com.readlearncode.dukesbookshop.restserver.infrastructure.concreteRepositories;

import com.readlearncode.dukesbookshop.restserver.DatabaseConfig;
import com.readlearncode.dukesbookshop.restserver.domain.Customer;
import com.readlearncode.dukesbookshop.restserver.infrastructure.abstractRepositories.CustomerRepository;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Created by navid on 11/24/17.
 */

@Stateless
public class CustomerRepositoryBean implements CustomerRepository {


    @Override
    public Optional<Customer> createNewProfile(final String fullName, final String telephoneNumber) {
        Customer cs = new Customer(fullName, telephoneNumber);

        Session session = DatabaseConfig.getSession();
        session.beginTransaction();
        session.save(cs);
        session.getTransaction().commit();
        System.out.println("Customer Created:" + cs.toString());

        return Optional.ofNullable(cs);
    }

    @Override
    public Optional<Customer> getCustomerById(final int id) {
        Session session = DatabaseConfig.getSession();
        Customer cs = (Customer) session.load(Customer.class, id);
        return Optional.ofNullable(cs);
    }

    @Override
    public List<Customer> getAllCustomers() {
        Session session = DatabaseConfig.getSession();
        @SuppressWarnings("unchecked")
        List<Customer> customers = session.createQuery("FROM customer").list();
        System.out.println("Found " + customers.size() + " Customers");
        return customers;
    }

    @Override
    public Optional<Customer> createNewProfile(Customer newCs) {
        Optional<Customer> registeredCustomer = this.createNewProfile(newCs.getFullName(), newCs.getPhoneNumber());
        return registeredCustomer;
    }

    @Override
    public Optional<Customer> getCustomerByTel(String telNumber) {
        Session session = DatabaseConfig.getSession();

        String hql = "FROM customer WHERE phoneNumber = :telNumber";
        Query query = session.createQuery(hql);
        query.setParameter("telNumber", telNumber);

        @SuppressWarnings("unchecked")
        List<Customer> customers = query.list();
        Customer cs = customers.get(0);

        System.out.println("Found Customer By PhoneNumber:" + cs.toString());

        return Optional.ofNullable(cs);
    }
}
