import com.readlearncode.dukesbookshop.restserver.domain.Table;
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
 * Created by navid on 1/21/18.
 */
public class TestTableDB {
    static Session session;

    @BeforeClass
    @SuppressWarnings("Duplicates")
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
    public void tableDatabaseTest() {
        Table tb1 = new Table();
        tb1.setNumberOfSeats(12);

        Table tb2 = new Table();
        tb2.setNumberOfSeats(4);

        Table tb3 = new Table();
        tb3.setNumberOfSeats(4);

        Table tb4 = new Table();
        tb4.setNumberOfSeats(6);

        System.out.println("====CREATE====");
        create(tb1);
        create(tb2);
        create(tb3);
        create(tb4);

        System.out.println("====READ====");
        List<Table> tables = read();
        for (Table tbl : tables) {
            System.out.println(tbl.toString());
        }

        System.out.println("====DELETE====");
        delete(tb1.getId());

        System.out.println("====READ====");
        tables = read();
        for (Table tbl : tables) {
            System.out.println(tbl.toString());
        }

        System.out.println("====UPDATE====");
        tb3.setNumberOfSeats(8);

        System.out.println("====READ====");
        tables = read();
        for (Table tbl : tables) {
            System.out.println(tbl.toString());
        }

        System.out.println("====DELETE_ALL====");
        deleteAll();
    }

    public static void create(Table tbl) {
        session.beginTransaction();
        session.save(tbl);
        session.getTransaction().commit();
        System.out.println("SuccessFully created!");
    }

    public static List<Table> read() {
        @SuppressWarnings("unchecked")
        List<Table> tables = session.createQuery("FROM restaurantTable").list();
        System.out.println("Found " + tables.size() + " Tables");
        return tables;
    }

    public static void delete(Integer id) {
        session.beginTransaction();
        Table tbl = findByID(id);
        session.delete(tbl);
        session.getTransaction().commit();
        System.out.println("Successfully deleted " + tbl.toString());

    }

    public static void deleteAll() {
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM restaurantTable");
        query.executeUpdate();
        session.getTransaction().commit();
        System.out.println("Successfully deleted all tables.");

    }

    public static Table findByID(Integer id) {
        Table tbl = (Table) session.load(Table.class, id);
        return tbl;
    }
}
