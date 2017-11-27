package com.readlearncode.dukesbookshop.restserver.infrastructure;

import com.readlearncode.dukesbookshop.restserver.domain.Customer;
import com.readlearncode.dukesbookshop.restserver.domain.MenuItem;
import com.readlearncode.dukesbookshop.restserver.domain.Order;
import com.readlearncode.dukesbookshop.restserver.domain.Reservation;
import com.readlearncode.dukesbookshop.restserver.infrastructure.repositories.Menu;
import com.readlearncode.dukesbookshop.restserver.infrastructure.repositories.OrderRepository;

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
    //e.g. <ReservationId, <OrderId, (Order Object)>> -> < resId:23, <orderId:23531, Order:orderObj>>
    private HashMap<Integer, HashMap<Integer, Order>> ordersMap = new HashMap<>();
    private ArrayList<Order> allOrdersShortCutList = new ArrayList<>();

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
        this.allOrdersShortCutList.add(newOrder);

        return Optional.of(newOrder);
    }

    //TODO test
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
    public Optional<Order> addItemToOrder(Order order, MenuItem item, int itemOrderedNumber) {
        if (order.getOrderItems() == null) {
            order.setOrderItems(new HashMap<>());
        }
        order.getOrderItems().put(item.getName(), itemOrderedNumber);
        return Optional.of(order);
    }

    @Override
    public float calculateTotalCost(Order order) {
        float totalCost = 0;
        Set<String> orderedItems = order.getOrderItems().keySet();

        for (String itm : orderedItems) {
            Optional<MenuItem> menuItem = myRestaurantMenu.findMenuItemByName(itm);
            if(menuItem.isPresent()) {
                totalCost += menuItem.get().getPrice();
            }
        }

        return totalCost;
    }

    @Override
    public ArrayList<Order> getAllOrders() {
        return allOrdersShortCutList;
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
