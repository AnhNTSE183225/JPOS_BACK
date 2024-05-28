package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Order;

public interface ICustomerRequestService {
    Order handleRequest(int orderId);
}
