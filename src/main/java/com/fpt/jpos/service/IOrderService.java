package com.fpt.jpos.service;

import com.fpt.jpos.dto.PaymentDTO;
import com.fpt.jpos.pojo.CustomerRequestDTO;
import com.fpt.jpos.pojo.Order;
import com.fpt.jpos.pojo.enums.OrderStatus;

import java.util.List;

public interface IOrderService {

    List<Order> findAll();

    Order insertOrder(CustomerRequestDTO customerRequestDTO);

    String handleManagerResponse(Integer id, boolean managerApproval);

    List<Order> getOrdersByStatusAndStaffs(int id);

    Order findById(Integer id);

    OrderStatus forwardQuotation(Integer id);

    Order retrieveQuotationFromStaff(Order order, Integer productId, Integer saleStaffId);

    Order acceptOrder(Integer id);

    Order updateOrderStatusDesigning(Integer id, PaymentDTO paymentDTO);

    Order updateOrderStatusProduction(Integer id);

    Order completeProduct(Integer id, String imageUrl, Integer productionStaffId);

    Order completeOrder(PaymentDTO paymentDTO, Integer orderId);

    //TODO Update production staff id when production staff select delivered
}
