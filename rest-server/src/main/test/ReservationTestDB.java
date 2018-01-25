import com.readlearncode.dukesbookshop.restserver.domain.Reservation;
import com.readlearncode.dukesbookshop.restserver.domain.Table;
import com.readlearncode.dukesbookshop.restserver.domain.TimeSpan;
import com.readlearncode.dukesbookshop.restserver.domain.Customer;
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
 * Created by navid on 1/25/18.
 */
@SuppressWarnings("ALL")
public class ReservationTestDB {
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
        Reservation res = createTest();

        System.out.println("====READ====");
        readTest(res.getId());

        System.out.println("====DELETE_ALL====");
        //delete(res.getId());

    }

    public static Reservation createTest() {
        Customer kab = new Customer();
        kab.setPhoneNumber("9383700666");
        kab.setFullName("Kamran Miadi");


        Reservation res = new Reservation();
        Table tbl = (Table) session.get(Table.class, 2);

        res.setReservationDate(Date.valueOf("2018-01-30"));
        res.setSubmissionDate(Date.valueOf(LocalDate.now()));

        TimeSpan timeSpan = new TimeSpan();
        timeSpan.setStart("1900");
        timeSpan.setEnd("2130");
        res.setReservationTime(timeSpan);
        res.setRelatedTable(tbl);
        res.setOtherRequirements("It's a bithday party... so we need a small size birthday cake");


        // first create customers
        createCustomer(kab);

        // second set the customer of each reservation
        res.setRelatedCustomer(kab);

        // third persist the reservation
        persistReservation(res);

        return res;
    }

    public static void readTest(int id) {
        Reservation reservation = findReservationById(id);
        System.out.println(reservation.getId());
        System.out.println(reservation.getRelatedCustomer().toString());
        System.out.println(reservation.getRelatedTable().toString());
        System.out.println(reservation.getReservationTime().toString());
    }

    public static void deleteAll() {
        for (Integer id : testInstancesId) {
            delete(id);
        }
    }

    public static void delete(int id) {
        session.beginTransaction();
        Reservation res = findReservationById(id);
        session.delete(res);
        System.out.println("Successfully deleted " + res.toString());
        session.getTransaction().commit();
    }

    public static Reservation findReservationById(int id) {
        return (Reservation) session.get(Reservation.class, id);
    }

    public static void persistReservation(Reservation res) {
        session.beginTransaction();
        session.save(res);
        session.getTransaction().commit();
        System.out.println("SuccessFully created!");
    }

    public static void createCustomer(Customer cs) {
        session.beginTransaction();
        session.save(cs);
        session.getTransaction().commit();
        System.out.println("SuccessFully created!");
    }


}
