package com.fpt.jpos.service;

import com.fpt.jpos.pojo.CustomerRequest;
import com.fpt.jpos.pojo.Order;

public interface IOrderService {

    Order insertOrder(CustomerRequest customerRequest);
    //Order insertOrder(Order theOrder, int customerId);

}
