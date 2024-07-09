package com.fpt.jpos.service;

import com.fpt.jpos.dto.*;
import com.fpt.jpos.pojo.Order;
import com.fpt.jpos.pojo.enums.OrderStatus;

import java.util.List;

public interface IOrderService {

    List<Order> findAll();

    Order insertOrder(CustomerRequestDTO customerRequestDTO);

    String handleManagerResponse(Integer id, boolean managerApproval, ManagerResponseDTO managerResponseDTO);

    List<Order> getOrdersByCustomerId(Integer customerId);

    List<Order> getOrderForSalesStaff(Integer id);

    List<Order> getOrderForDesignStaff(Integer id);

    List<Order> getOrderForProductionStaff(Integer id);

    List<Order> getOrderForManager();

    Order findById(Integer id);

    OrderStatus forwardQuotation(Integer id);

    Order retrieveQuotationFromStaff(Order order, Integer productId, Integer saleStaffId);

    Integer acceptQuotation(Integer orderId);

    Order updateOrderStatusDesigning(Integer id, PaymentRestDTO.PaymentRequest paymentDTO);

    Order updateOrderStatusDesigning(Integer id, NoteDTO noteDTO);

    Order updateOrderStatusProduction(Integer id);

    Order completeProduct(Integer id, String imageUrls);

    Order completeOrder(Integer orderId);

    //Order addProductDesignToOrder(ProductDesignDTO productDesignDTO);

    Order createOrderFromDesign(ProductDesignDTO productDesignDTO);

    void confirmPaymentSuccess(Integer orderId, String orderType);

    Integer assign(Integer orderId, Integer saleStaffId, Integer designStaffId, Integer productionStaffId);

    Order addImage(String imageUrls, Integer orderId);

    Order cancelOrder(Integer orderId);

    List<Order> findCompletedOrders();
    //TODO Update production staff id when production staff select delivered
}
