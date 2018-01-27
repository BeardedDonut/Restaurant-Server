package com.readlearncode.dukesbookshop.restserver.infrastructure.DAOImplementation;

import com.readlearncode.dukesbookshop.restserver.DatabaseConfig;
import com.readlearncode.dukesbookshop.restserver.domain.*;
import com.readlearncode.dukesbookshop.restserver.infrastructure.DAOInterface.ReservationRepository;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.MenuItemNotFoundException;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.OrderNotFoundException;
import com.readlearncode.dukesbookshop.restserver.infrastructure.DAOInterface.MenuItemRepository;
import com.readlearncode.dukesbookshop.restserver.infrastructure.DAOInterface.OrderRepository;
import org.hibernate.Session;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.*;

/**
 * Created by navid on 11/27/17.
 */
@Stateless(name = "OrderRepositoryDAOImp")
public class OrderRepositoryDAOImp implements OrderRepository {

    @EJB
    private ReservationRepository resRepo;

    @EJB
    private MenuItemRepository myRestaurantMenu;

    public Optional<Order> createNewOrder
            (Reservation res,
             HashMap<MenuItem, Integer> orderedItems) {

        Session session = DatabaseConfig.createSessionFactory().openSession();
        session.beginTransaction();

        Order newOrder = new Order();
        newOrder.setStatus(OrderStatus.PREPARING);
        newOrder.setAccordingReservation(res);
        newOrder = saveOrder(newOrder).get();


        float totalCost = 0;
        Set<MenuItem> itemSet = orderedItems.keySet();
        for (MenuItem item : itemSet) {
            MenuItemOrder menuItemOrder = new MenuItemOrder();
            MenuItemOrderId id = new MenuItemOrderId();
            int number = orderedItems.get(item);

            id.setMenuItem(item);
            id.setOrder(newOrder);

            menuItemOrder.setPk(id);
            menuItemOrder.setNumber(number);

            totalCost += number * item.getPrice();
            session.save(menuItemOrder);

            newOrder.getMenuItemOrders().add(menuItemOrder);
        }
        newOrder.setTotalCost(totalCost);

        session.merge(newOrder);
        session.save(newOrder);
        session.getTransaction().commit();

        session.close();
        return Optional.of(newOrder);
    }

    @SuppressWarnings("Duplicates")
    public Optional<Order> saveOrder(Order order) {
        Session session = DatabaseConfig.createSessionFactory().openSession();

        session.beginTransaction();
        session.save(order);
        session.getTransaction().commit();

        return Optional.of(order);
    }

    @SuppressWarnings("Duplicates")
    public Optional<MenuItemOrder> saveMenuItemOrder(MenuItemOrder menuItemOrder) {
        Session session = DatabaseConfig.createSessionFactory().openSession();

        session.beginTransaction();
        session.save(menuItemOrder);
        session.getTransaction().commit();

        return Optional.of(menuItemOrder);
    }

    @Override
    public Optional<Reservation> findReservationByOrder
            (Order order) {
        return Optional.ofNullable(order.getAccordingReservation());
    }

    @Override
    public Optional<Order> saveOrderForReservation
            (Reservation res, Order newOrder) {

        //TODO
        return this.createNewOrder(res, null);
    }

    @Override
    public Optional<Order> changeOrderStatus
            (Order order, OrderStatus status)
            throws
            MenuItemNotFoundException {

        Optional<Order> registeredOrder = this.getByOrderId(order.getId());

        if (registeredOrder.isPresent()) {
            registeredOrder.get().setStatus(status);

            if (status.equals(OrderStatus.CONFIRMED)) {   /* if the order is confirmed then calculate its cost */
                this.calculateTotalCost(order);
            }

            return registeredOrder;
        } else {
            System.err.println("oops orderId is not valid");
            return null;
        }
    }

    @Override
    public Optional<Order> getByOrderId
            (int orderId) {
        Session session = DatabaseConfig.createSessionFactory().openSession();
        Order order = (Order) session.get(Order.class, orderId);
        session.close();

        return Optional.ofNullable(order);
    }

    @Override
    @Deprecated
    public Optional<Order> confirmOrder(int orderId)
            throws
            OrderNotFoundException,
            MenuItemNotFoundException {

        Optional<Order> order = this.getByOrderId(orderId);

        if (order.isPresent()) {
            this.changeOrderStatus(order.get(), OrderStatus.CONFIRMED);
            return order;
        } else {
            return null;
        }
    }

    @Override
    public Optional<Order> addItemToOrder
            (Order order, MenuItem item, int itemOrderedNumber)
            throws
            MenuItemNotFoundException {

        if (order.getMenuItemOrders() == null) {
            order.setMenuItemOrders(new LinkedHashSet<>());
        }


        MenuItemOrder menuItemOrder = new MenuItemOrder();
        menuItemOrder.getPk().setMenuItem(item);
        menuItemOrder.getPk().setOrder(order);
        menuItemOrder.setNumber(itemOrderedNumber);
        saveMenuItemOrder(menuItemOrder);

        order.getMenuItemOrders().add(menuItemOrder);

        this.calculateTotalCost(order);

        return Optional.of(order);
    }

    @Override
    public float calculateTotalCost
            (Order order)
            throws
            MenuItemNotFoundException {

        float totalCost = 0;
        Set<MenuItemOrder> orderedItems = order.getMenuItemOrders();

        for (MenuItemOrder itm : orderedItems) {
            Optional<MenuItem> menuItem = myRestaurantMenu.findMenuItemByName(itm.getPk().getMenuItem().getName());
            if (menuItem.isPresent()) {
                totalCost += menuItem.get().getPrice() * itm.getNumber(); // item price * item count
            }
        }

        order.setTotalCost(totalCost);

        return totalCost;
    }

    @Override
    public List<Order> getAllOrders() {
        Session session = DatabaseConfig.createSessionFactory().openSession();

        @SuppressWarnings("unchecked")
        List<Order> orders = session.createQuery("FROM Order").list();
        System.out.println("Found " + orders.size() + " Customers");

        session.close();

        return orders;
    }

    @Override
    public List<Order> getOrdersWithStatus
            (OrderStatus orderStatus) {

        Session session = DatabaseConfig.createSessionFactory().openSession();
        String query = "FROM Order AS ord WHERE ord.status = :status";

        @SuppressWarnings("unchecked")
        List<Order> orders = session.createQuery(query).setParameter("status", orderStatus).list();
        System.out.println("Found " + orders.size() + " Orders");

        session.close();

        return orders;
    }

    //TODO implement
    @Override
    public Optional<Order> findOrderByReservationInfo(Reservation res) {
        return null;
    }

    //TODO implement
    @Override
    public ArrayList<Order> findAllReservationsByCustomers(Customer cs) {
        return null;
    }
}
