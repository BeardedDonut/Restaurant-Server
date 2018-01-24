package com.readlearncode.dukesbookshop.restserver.infrastructure.DAOImplementation;

import com.readlearncode.dukesbookshop.restserver.DatabaseConfig;
import com.readlearncode.dukesbookshop.restserver.domain.Customer;
import com.readlearncode.dukesbookshop.restserver.infrastructure.DAOInterface.CustomerRepository;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * Created by navid on 11/24/17.
 */

@Stateless(name = "CustomerRepositoryDAOImp")
public class CustomerRepositoryDAOImp implements CustomerRepository {


    @Override
    public Optional<Customer> createNewProfile(final String fullName, final String telephoneNumber) {
        Customer cs = new Customer();
        cs.setFullName(fullName);
        cs.setPhoneNumber(telephoneNumber);
        Session session = DatabaseConfig.createSessionFactory().openSession();

        session.beginTransaction();
        session.save(cs);
        session.getTransaction().commit();
        System.out.println("Customer Created:" + cs.toString());

        session.close();
        return Optional.ofNullable(cs);
    }

    @Override
    public Optional<Customer> getCustomerById(final int id) {
        Session session = DatabaseConfig.createSessionFactory().openSession();

        Customer cs = (Customer) session.get(Customer.class, id);

        session.close();
        return Optional.ofNullable(cs);
    }

    @Override
    public List<Customer> getAllCustomers() {
        Session session = DatabaseConfig.createSessionFactory().openSession();

        @SuppressWarnings("unchecked")
        List<Customer> customers = session.createQuery("FROM customer").list();
        System.out.println("Found " + customers.size() + " Customers");

        session.close();
        return customers;
    }

    @Override
    public Optional<Customer> createNewProfile(Customer newCs) {
        Optional<Customer> registeredCustomer = this.createNewProfile(newCs.getFullName(), newCs.getPhoneNumber());
        return registeredCustomer;
    }

    @Override
    public Optional<Customer> getCustomerByTel(String telNumber) {
        Session session = DatabaseConfig.createSessionFactory().openSession();

        String hql = "FROM customer WHERE phoneNumber = :telNumber";
        Query query = session.createQuery(hql);
        query.setParameter("telNumber", telNumber);

        @SuppressWarnings("unchecked")
        List<Customer> customers = query.list();
        Customer cs = null;
        if (customers.size() != 0) {
            cs = customers.get(0);
            System.out.println("Found Customer By PhoneNumber:" + cs.toString());
        }

        session.close();
        return Optional.ofNullable(cs);
    }
}
