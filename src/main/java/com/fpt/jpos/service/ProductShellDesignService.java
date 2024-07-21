package com.fpt.jpos.service;

import com.fpt.jpos.pojo.ProductShellDesign;
import com.fpt.jpos.repository.IProductShellDesignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductShellDesignService implements IProductShellDesignService {

    private final IProductShellDesignRepository productShellDesignRepository;

    @Override
    public List<ProductShellDesign> getByProductDesignId(Integer productDesignId) {
        return productShellDesignRepository.findByProductDesignId(productDesignId);
    }

    @Override
    public void deleteShell(Integer productShellDesignId) {
        this.productShellDesignRepository.deleteById(productShellDesignId);
    }
}
