package com.fpt.jpos.service;

import com.fpt.jpos.pojo.ProductShellMaterial;
import com.fpt.jpos.repository.IProductShellMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductShellMaterialService implements IProductShellMaterialService {

    private final IProductShellMaterialRepository productShellMaterialRepository;

    @Autowired
    public ProductShellMaterialService(IProductShellMaterialRepository productShellMaterialRepository) {
        this.productShellMaterialRepository = productShellMaterialRepository;
    }


    //Binh
    @Override
    @Transactional
    public List<ProductShellMaterial> findByShellId(Integer shellId) {
        return productShellMaterialRepository.findByShellId(shellId);
    }
}
