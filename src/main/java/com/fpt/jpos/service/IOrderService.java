package com.fpt.jpos.service;

import com.fpt.jpos.dto.NoteDTO;
import com.fpt.jpos.dto.PaymentDTO;
import com.fpt.jpos.dto.ProductDesignDTO;
import com.fpt.jpos.dto.CustomerRequestDTO;
import com.fpt.jpos.pojo.Order;
import com.fpt.jpos.pojo.enums.OrderStatus;

import java.util.List;

public interface IOrderService {

    List<Order> findAll();

    Order insertOrder(CustomerRequestDTO customerRequestDTO);

    String handleManagerResponse(Integer id, boolean managerApproval);

    List<Order> getOrdersByCustomerId(Integer customerId);

    List<Order> getOrderForSalesStaff(Integer id);

    List<Order> getOrderForDesignStaff(Integer id);

    List<Order> getOrderForProductionStaff(Integer id);

    List<Order> getOrderForManager();

    Order findById(Integer id);

    OrderStatus forwardQuotation(Integer id);

    Order retrieveQuotationFromStaff(Order order, Integer productId, Integer saleStaffId);

    Order acceptOrder(Order order);

    Order updateOrderStatusDesigning(Integer id, PaymentDTO paymentDTO);

    Order updateOrderStatusDesigning(Integer id, NoteDTO noteDTO);

    Order updateOrderStatusProduction(Integer id);

    Order completeProduct(Integer id, String imageUrl, Integer productionStaffId);

    Order completeOrder(PaymentDTO paymentDTO, Integer orderId);
    
    //Order addProductDesignToOrder(ProductDesignDTO productDesignDTO);


    //TODO Update production staff id when production staff select delivered
}
