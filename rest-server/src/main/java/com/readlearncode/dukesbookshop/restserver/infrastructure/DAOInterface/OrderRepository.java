package com.readlearncode.dukesbookshop.restserver.infrastructure.DAOInterface;

import com.readlearncode.dukesbookshop.restserver.dataTransferObjects.OrderRequestDTO;
import com.readlearncode.dukesbookshop.restserver.domain.*;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.MenuItemNotFoundException;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.OrderNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by navid on 11/26/17.
 */
public interface OrderRepository {

    Optional<Reservation> findReservationByOrder(Order order);

    Optional<Order> saveOrderForReservation(Reservation res, Order newOrder);

    Optional<Order> getByOrderId(int orderId);

    Optional<Order> changeOrderStatus(Order order, OrderStatus status) throws MenuItemNotFoundException;

    Optional<Order> confirmOrder(int orderId) throws OrderNotFoundException, MenuItemNotFoundException;

    Optional<Order> addItemToOrder(Order order, MenuItem item, int itemOrderedNumber) throws MenuItemNotFoundException;

    Optional<Order> findOrderByReservationInfo(Reservation res);

    Optional<Order> processOrderRequest(OrderRequestDTO orderReq) throws MenuItemNotFoundException;

    float calculateTotalCost(Order order) throws MenuItemNotFoundException;

    List<Order> getAllOrders();

    List<Order> getOrdersWithStatus(OrderStatus orderStatus);

    List<Order> findAllReservationsByCustomers(Customer cs);

}
