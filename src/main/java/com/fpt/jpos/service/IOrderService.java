package com.fpt.jpos.service;

import com.fpt.jpos.pojo.CustomerRequest;
import com.fpt.jpos.pojo.Order;
import com.fpt.jpos.pojo.enums.OrderStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IOrderService {

    Order insertOrder(CustomerRequest customerRequest);
    //Order insertOrder(Order theOrder, int customerId);

    String handleManagerResponse(Integer id, boolean managerApproval);

    List<Order> getOrdersByStatusAndStaffs(int id);

    Order findById(Integer id);

    Order updateOrderStatusDesigning(int id);

    Order updateOrderStatusProduction(int id);

    OrderStatus forwardQuotation(Integer id);

    String retrieveQuotationFromStaff(Integer id);

    Order acceptOrder(Integer id);

    //TODO
    // - Production Staff set status to production_completed
    // - Sale Staff deliver product to Customer - set status to Delivered
    // - Sale Staff confirm final payment - set status to completed
    // - Get productDesign list for customer to choose
    // - Get diamond list for customer to choose
}
