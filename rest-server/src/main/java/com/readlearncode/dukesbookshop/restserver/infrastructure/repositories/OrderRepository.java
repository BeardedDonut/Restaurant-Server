package com.readlearncode.dukesbookshop.restserver.infrastructure.repositories;

import com.readlearncode.dukesbookshop.restserver.domain.Customer;
import com.readlearncode.dukesbookshop.restserver.domain.MenuItem;
import com.readlearncode.dukesbookshop.restserver.domain.Order;
import com.readlearncode.dukesbookshop.restserver.domain.Reservation;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exception.OrderNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by navid on 11/26/17.
 */
public interface OrderRepository {

    Optional<Reservation> findReservationByOrder(Order order);

    Optional<Order> saveOrderForReservation(Reservation res, Order newOrder);

    Optional<Order> getByOrderId(int orderId);

    Optional<Order> changeOrderStatus(Order order, Order.OrderStatus status);

    Optional<Order> confirmOrder(int orderId) throws OrderNotFoundException;

    Optional<Order> addItemToOrder(Order order, MenuItem item, int itemOrderedNumber);

    float calculateTotalCost(Order order);

    ArrayList<Order> getAllOrders();

    ArrayList<Order> getOrdersWithStatus(final Order.OrderStatus orderStatus);

    Optional<Order> findOrderByReservationInfo(Reservation res);

    ArrayList<Order> findAllReservationsByCustomers(Customer cs);

}
