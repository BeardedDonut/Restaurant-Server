import com.readlearncode.dukesbookshop.restserver.domain.Customer;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * Created by navid on 1/7/18.
 */
public class testDB {

    static Session session;

    @BeforeClass
    public static void createSessionFactory() {
        System.out.println("Creating Database Connection");
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        SessionFactory newSessionFactory = configuration
                .buildSessionFactory(builder.build());
        session = newSessionFactory.openSession();
    }

    @AfterClass
    public static void closeSession() {
        System.out.println("Closing Database Connection!");
        session.close();
    }

    @Test
    public void test1() {
        Customer cs1 = new Customer();
        cs1.setFullName("Navid");
        cs1.setPhoneNumber("989383709786");

        Customer cs2 = new Customer();
        cs2.setFullName("Mehdi");
        cs2.setPhoneNumber("123456789111");

        Customer cs3 = new Customer();
        cs3.setFullName("Kamran");
        cs3.setPhoneNumber("123456789112");

        System.out.println("====CREATE====");
        create(cs1);
        create(cs2);
        create(cs3);

        System.out.println("====READ====");
        List<Customer> customers = read();
        for (Customer cs : customers) {
            System.out.println(cs.toString());
        }

        System.out.println("====DELETE====");
        delete(cs1.getCustomerId());

        System.out.println("====READ====");
        customers = read();
        for (Customer cs : customers) {
            System.out.println(cs.toString());
        }

        System.out.println("====UPDATE====");
        cs2.setFullName("Ahmad");

        System.out.println("====READ====");
        customers = read();
        for (Customer cs : customers) {
            System.out.println(cs.toString());
        }

        System.out.println("====DELETE_ALL====");
        deleteAll();


    }

    public static void create(Customer cs) {
        session.beginTransaction();
        session.save(cs);
        session.getTransaction().commit();
        System.out.println("SuccessFully created!");
    }

    public static List<Customer> read() {
        @SuppressWarnings("unchecked")
        List<Customer> customers = session.createQuery("FROM customer").list();
        System.out.println("Found " + customers.size() + " Customers");
        return customers;
    }

    public static void delete(Integer id) {
        session.beginTransaction();
        Customer cs = findByID(id);
        session.delete(cs);
        session.getTransaction().commit();
        System.out.println("Successfully deleted " + cs.toString());

    }

    public static void deleteAll() {
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM customer");
        query.executeUpdate();
        session.getTransaction().commit();
        System.out.println("Successfully deleted all customers.");

    }

    public static Customer findByID(Integer id) {
        Customer cs = (Customer) session.load(Customer.class, id);
        return cs;
    }


}
