import com.readlearncode.dukesbookshop.restserver.domain.*;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by navid on 1/27/18.
 */
public class TestOrderDB {
    static Session session;

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
    public void test() {
        session.beginTransaction();
        MenuItem sibzamini = loadMenuItem(2);
        MenuItem dooghSade = loadMenuItem(3);
        MenuItem dooghAbAli = loadMenuItem(4);

        Reservation res = loadReservation(5);

        Order newOrder = new Order();
        newOrder.setAccordingReservation(res);

        MenuItemOrder min1 = new MenuItemOrder();
        min1.setMenuItem(sibzamini);
        min1.setOrder(newOrder);
        min1.setNumber(3);

        MenuItemOrder min2 = new MenuItemOrder();
        min2.setOrder(newOrder);
        min2.setMenuItem(dooghSade);
        min2.setNumber(2);

        MenuItemOrder min3 = new MenuItemOrder();
        min3.setOrder(newOrder);
        min3.setMenuItem(dooghAbAli);
        min3.setNumber(2);

        Set<MenuItemOrder> set = newOrder.getMenuItemOrders();
        newOrder.getMenuItemOrders().add(min1);
        newOrder.getMenuItemOrders().add(min2);
        newOrder.getMenuItemOrders().add(min3);

        session.save(newOrder);
        session.getTransaction().commit();

        Order ord = (Order) session.get(Order.class, newOrder.getId());
        System.out.println("Hello!");
    }

    public static MenuItem loadMenuItem(int id) {
        MenuItem mi = (MenuItem) session.get(MenuItem.class, id);
        return mi;
    }

    public static Reservation loadReservation(int id) {
        Reservation res = (Reservation) session.get(Reservation.class, id);
        return res;
    }

    public static Order saveOrder(Order newOrder) {
        session.beginTransaction();
        session.save(newOrder);
        session.getTransaction().commit();

        return newOrder;
    }

    public static MenuItemOrder saveMenuItemOrder(MenuItemOrder mio) {
        session.beginTransaction();
        session.save(mio);
        session.getTransaction().commit();

        return mio;
    }
}
