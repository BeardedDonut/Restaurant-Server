import com.readlearncode.dukesbookshop.restserver.dataTransferObjects.CheckRequestDTO;
import com.readlearncode.dukesbookshop.restserver.domain.Customer;
import com.readlearncode.dukesbookshop.restserver.domain.TimeSpan;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 * Created by navid on 1/23/18.
 */

public class TestCheckRequestDTODB {
    static Session session;
    static ArrayList<Integer> testInstancesId = new ArrayList<>();

    @SuppressWarnings("Duplicates")
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
    public void checkRequestDatabaseTest() {
        System.out.println("====CREATE====");
        createTest();

        System.out.println("====READ====");
        readTest(testInstancesId.get(0));

        System.out.println("====DELETE_ALL====");
        deleteAll();

    }

    public static void createTest() {
        Customer navid = new Customer();
        navid.setPhoneNumber("9383709186");
        navid.setFullName("Navid Alipoor");

        Customer alireza = new Customer();
        alireza.setFullName("Legolong");
        alireza.setPhoneNumber("9383039303");

        CheckRequestDTO cr1 = new CheckRequestDTO();
        cr1.setDate(Date.valueOf(LocalDate.now()));
        cr1.setNumberOfSeats(4);

        CheckRequestDTO cr2 = new CheckRequestDTO();
        cr2.setDate(Date.valueOf("2018-01-28"));
        cr2.setNumberOfSeats(2);

        TimeSpan timeSpan = new TimeSpan();
        timeSpan.setStart("19:00");
        timeSpan.setEnd("21:30");
        cr1.setTs(timeSpan);

        TimeSpan timeSpan1 = new TimeSpan();
        timeSpan1.setStart("20:00");
        timeSpan1.setEnd("21:00");
        cr2.setTs(timeSpan1);

        // first create customers
        createCustomer(navid);
        createCustomer(alireza);

        // second set the customer of each checkRequests
        cr1.setRelatedCustomer(navid);
        cr2.setRelatedCustomer(alireza);

        // third persist the checkRequests
        createRequestCheck(cr1);
        createRequestCheck(cr2);

        // register these so we can delete them
        testInstancesId.add(navid.getCustomerId());
        testInstancesId.add(alireza.getCustomerId());
    }

    public static void deleteAll() {
        for (Integer id : testInstancesId) {
            delete(id);
        }
    }

    public static void delete
            (int id) {
        session.beginTransaction();
        CheckRequestDTO cr = findCheckReqById(id);
        session.delete(cr);
        System.out.println("Successfully deleted " + cr.toString());
        session.getTransaction().commit();
    }

    public static void readTest
            (int id) {
        CheckRequestDTO crEvicted = findCheckReqById(id);
        System.out.println(crEvicted.getId());
        System.out.println(crEvicted.getDate());
        System.out.println(crEvicted.getNumberOfSeats());
        System.out.println(crEvicted.getTs().toString());
        System.out.println(crEvicted.getRelatedCustomer().toString());
    }

    public static CheckRequestDTO findCheckReqById
            (int id) {

        return (CheckRequestDTO) session.get(CheckRequestDTO.class, id);
    }

    public static void createCustomer
            (Customer cs) {
        session.beginTransaction();
        session.save(cs);
        session.getTransaction().commit();
        System.out.println("SuccessFully created!");
    }

    public static void createRequestCheck
            (CheckRequestDTO rs) {
        session.beginTransaction();
        session.save(rs);
        session.getTransaction().commit();
        System.out.println("SuccessFully created!");
    }

}
