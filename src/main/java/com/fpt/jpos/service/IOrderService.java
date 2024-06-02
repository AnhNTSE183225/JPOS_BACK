package com.fpt.jpos.service;

import com.fpt.jpos.pojo.CustomerRequest;
import com.fpt.jpos.pojo.Order;
import com.fpt.jpos.pojo.Payment;
import com.fpt.jpos.pojo.enums.OrderStatus;

import java.util.List;

public interface IOrderService {

    Order insertOrder(CustomerRequest customerRequest);

    String handleManagerResponse(Integer id, boolean managerApproval);

    List<Order> getOrdersByStatusAndStaffs(int id);

    Order findById(Integer id);

    OrderStatus forwardQuotation(Integer id);

    Order retrieveQuotationFromStaff(Integer id, Integer saleStaffId);

    Order acceptOrder(Integer id);

    Order updateOrderStatusDesigning(Integer id, Payment payment);

    Order updateOrderStatusProduction(Integer id);

    //TODO Update production staff id when production staff select delivered
}
