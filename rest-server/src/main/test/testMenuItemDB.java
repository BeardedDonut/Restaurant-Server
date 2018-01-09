import com.readlearncode.dukesbookshop.restserver.domain.MenuItem;
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
 * Created by navid on 1/10/18.
 */
public class testMenuItemDB {
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
    public void MenuItemDatabaseTest() {
        MenuItem mi1 = new MenuItem();
        mi1.setName("Doogh Ab Ali");
        mi1.setPrice(10.50f);
        mi1.setImageFileName("/images/Doogh-Ab-Ali.jpg");

        MenuItem mi2 = new MenuItem();
        mi2.setName("Potato Dish");
        mi2.setPrice(14.50f);
        mi2.setImageFileName("/images/Potato.jpg");

        System.out.println("====CREATE====");
        create(mi1);
        create(mi2);

        System.out.println("====READ====");
        List<MenuItem> items = read();
        for (MenuItem mi : items) {
            System.out.println(mi.toString());
        }

        System.out.println("====DELETE====");
        delete(mi1.getId());

        System.out.println("====READ====");
        items = read();
        for (MenuItem menuItem : items) {
            System.out.println(menuItem.toString());
        }

        System.out.println("====UPDATE====");
        mi2.setName("Boshghab Sib-Zamini");

        System.out.println("====READ====");
        items = read();
        for (MenuItem menuItem : items) {
            System.out.println(menuItem.toString());
        }

        System.out.println("====DELETE_ALL====");
        deleteAll();

    }

    public static void create(MenuItem mi) {
        session.beginTransaction();
        session.save(mi);
        session.getTransaction().commit();
        System.out.println("SuccessFully created!");
    }

    public static List<MenuItem> read() {
        @SuppressWarnings("unchecked")
        List<MenuItem> items = session.createQuery("FROM menuItem").list();
        System.out.println("Found " + items.size() + " Customers");
        return items;
    }

    public static void delete(Integer id) {
        session.beginTransaction();
        MenuItem mi = findByID(id);
        session.delete(mi);
        session.getTransaction().commit();
        System.out.println("Successfully deleted " + mi.toString());

    }

    public static void deleteAll() {
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM customer");
        query.executeUpdate();
        session.getTransaction().commit();
        System.out.println("Successfully deleted all customers.");

    }

    public static MenuItem findByID(Integer id) {
        MenuItem mi = (MenuItem) session.load(MenuItem.class, id);
        return mi;
    }

}
