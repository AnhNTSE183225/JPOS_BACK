package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Warranty;

public interface IWarrantyService {
    Warranty getWarrantyByProductId(Integer productId);
}
