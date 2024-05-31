package com.fpt.jpos.service;

import com.fpt.jpos.pojo.CustomerRequest;
import com.fpt.jpos.pojo.Order;
import com.fpt.jpos.pojo.enums.OrderStatus;

import java.util.List;

public interface IOrderService {

    Order insertOrder(CustomerRequest customerRequest);
    //Order insertOrder(Order theOrder, int customerId);

    String handleManagerResponse(Integer id, boolean managerApproval);

    List<Order> getOrdersByStatusAndStaffs(int id);

    Order findById(Integer id);

    Order updateOrderStatus(int orderId, OrderStatus status);

    OrderStatus forwardQuotation(Integer id);
}
