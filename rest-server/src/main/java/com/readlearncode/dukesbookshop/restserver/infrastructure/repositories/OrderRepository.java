package com.readlearncode.dukesbookshop.restserver.infrastructure.repositories;

import com.readlearncode.dukesbookshop.restserver.domain.Customer;
import com.readlearncode.dukesbookshop.restserver.domain.MenuItem;
import com.readlearncode.dukesbookshop.restserver.domain.Order;
import com.readlearncode.dukesbookshop.restserver.domain.Reservation;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by navid on 11/26/17.
 */
public interface OrderRepository {

    Optional<Reservation> findReservationByOrder(Order order);

    Optional<Order> saveOrderForReservation(Reservation res, Order newOrder);

    Optional<Order> addItemToOrder(Order order, MenuItem item, int itemOrderedNumber);

    float calculateTotalCost(Order order);

    ArrayList<Order> getAllOrders();

    Optional<Order> findOrderByReservationInfo(Reservation res);

    ArrayList<Order> findAllReservationsByCustomers(Customer cs);

}
