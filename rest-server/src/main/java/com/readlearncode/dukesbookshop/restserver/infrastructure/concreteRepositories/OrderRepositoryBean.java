package com.readlearncode.dukesbookshop.restserver.infrastructure.concreteRepositories;

import com.readlearncode.dukesbookshop.restserver.domain.Customer;
import com.readlearncode.dukesbookshop.restserver.domain.MenuItem;
import com.readlearncode.dukesbookshop.restserver.domain.Order;
import com.readlearncode.dukesbookshop.restserver.domain.Reservation;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exception.MenuItemNotFoundException;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exception.OrderNotFoundException;
import com.readlearncode.dukesbookshop.restserver.infrastructure.abstractRepositories.Menu;
import com.readlearncode.dukesbookshop.restserver.infrastructure.abstractRepositories.OrderRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

/**
 * Created by navid on 11/27/17.
 */
@Stateless
public class OrderRepositoryBean implements OrderRepository {

    private static int orderNumber = 1;

    private HashMap<Integer, HashMap<Integer, Order>> ordersMap = new HashMap<>();
    //e.g. <ReservationId, <OrderId, (Order Object)>> -> < resId:23, <orderId:23531, Order:orderObj>>

    private HashMap<Integer, Order> allOrdersShortCutList = new HashMap<>();
    //e.g. <OrderId, (Order Object)> -> <orderId:23531, Order:orderObj>


    @EJB
    private ReservationRepository resRepo;

    @EJB
    private Menu myRestaurantMenu;

    public Optional<Order> createNewOrder(Reservation res, HashMap<String, Integer> orderedItems) {
        Order newOrder = new Order(++orderNumber, res, orderedItems);

        HashMap<Integer, Order> orderOnlyMap = ordersMap.get(res.getReservationId());

        if (orderOnlyMap == null) {
            orderOnlyMap = new HashMap<>();
        }

        orderOnlyMap.put(newOrder.getOrderId(), newOrder);
        this.allOrdersShortCutList.put(newOrder.getOrderId(), newOrder);

        return Optional.of(newOrder);
    }

    @Override
    public Optional<Reservation> findReservationByOrder(Order order) {
        ArrayList<Integer> allResIds = new ArrayList<>(ordersMap.keySet());

        for (Integer resId : allResIds) {
            HashMap<Integer, Order> correspondingOrder = ordersMap.get(resId);
            if (correspondingOrder.get(order.getOrderId()) != null) {
                return Optional.ofNullable(resRepo.getByResIdOnly(resId));
            }
        }

        return null;
    }

    @Override
    public Optional<Order> saveOrderForReservation(Reservation res, Order newOrder) {
        return this.createNewOrder(res, newOrder.getOrderItems());
    }

    @Override
    public Optional<Order>
    changeOrderStatus(Order order, Order.OrderStatus status)
            throws MenuItemNotFoundException {

        Optional<Order> registeredOrder = this.getByOrderId(order.getOrderId());

        if (registeredOrder.isPresent()) {
            registeredOrder.get().setStatus(status);

            if (status.equals(Order.OrderStatus.CONFIRMED)) {   /* if the order is confirmed then calculate its cost */
                this.calculateTotalCost(order);
            }

            return registeredOrder;
        } else {
            System.err.println("oops orderId is not valid");
            return null;
        }
    }

    @Override
    public Optional<Order> getByOrderId(int orderId) {
        return Optional.ofNullable(allOrdersShortCutList.get(orderId));
    }

    @Override
    @Deprecated
    public Optional<Order>
    confirmOrder(int orderId)
            throws OrderNotFoundException, MenuItemNotFoundException {

        Optional<Order> order = this.getByOrderId(orderId);

        if (order.isPresent()) {
            this.changeOrderStatus(order.get(), Order.OrderStatus.CONFIRMED);
            return order;
        } else {
            return null;
        }
    }

    @Override
    public Optional<Order>
    addItemToOrder(Order order, MenuItem item, int itemOrderedNumber)
            throws MenuItemNotFoundException {

        if (order.getOrderItems() == null) {
            order.setOrderItems(new HashMap<>());
        }
        order.getOrderItems().put(item.getName(), itemOrderedNumber);

        this.calculateTotalCost(order);

        return Optional.of(order);
    }

    @Override
    public float calculateTotalCost(Order order) throws MenuItemNotFoundException {
        float totalCost = 0;
        Set<String> orderedItems = order.getOrderItems().keySet();

        for (String itm : orderedItems) {
            Optional<MenuItem> menuItem = myRestaurantMenu.findMenuItemByName(itm);
            if (menuItem.isPresent()) {
                totalCost += menuItem.get().getPrice() * order.getOrderItems().get(itm); // item price * item count
            }
        }

        order.setTotalCost(totalCost);

        return totalCost;
    }

    @Override
    public ArrayList<Order> getAllOrders() {
        return new ArrayList<>(allOrdersShortCutList.values());
    }

    @Override
    public ArrayList<Order> getOrdersWithStatus(Order.OrderStatus orderStatus) {
        ArrayList<Order> selectedOrders = new ArrayList<>();

        for(Order order: allOrdersShortCutList.values()) {
            if (order.getStatus().equals(orderStatus)) {
                selectedOrders.add(order);
            }
        }

        return selectedOrders;
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
