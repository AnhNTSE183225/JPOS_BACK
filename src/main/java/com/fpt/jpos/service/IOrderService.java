package com.fpt.jpos.service;

import com.fpt.jpos.pojo.CustomerRequest;
import com.fpt.jpos.pojo.Order;

import java.util.List;

public interface IOrderService {

    Order insertOrder(CustomerRequest customerRequest);
    //Order insertOrder(Order theOrder, int customerId);

    String handleManagerResponse(Integer id, boolean managerApproval);

    List<Order> getOrdersByStatusAndStaffs(int id);

}
