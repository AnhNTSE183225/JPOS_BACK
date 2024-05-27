package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Order;

public interface IOrderService {

    Order insertOrder(int customerId, String designFile, String budget, String orderType, String description, String status);
    //Order insertOrder(Order theOrder, int customerId);

}
