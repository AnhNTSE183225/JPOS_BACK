package com.fpt.jpos.pojo.enums;

public enum OrderStatus {
    wait_sale_staff,
    wait_manager,
    manager_approved,
    wait_customer,
    customer_accept,
    designing,
    pending_design,
    production,
    delivered,
    completed
}
