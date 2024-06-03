package com.fpt.jpos.service;

import com.fpt.jpos.dto.ProductDTO;
import com.fpt.jpos.pojo.*;
import com.fpt.jpos.repository.IDiamondRepository;
import com.fpt.jpos.repository.IMaterialRepository;
import com.fpt.jpos.repository.IProductMaterialRepository;
import com.fpt.jpos.repository.IProductRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final IMaterialRepository materialRepository;
    private final IProductMaterialRepository productMaterialRepository;
    private final IDiamondRepository diamondRepository;

    ModelMapper modelMapper = new ModelMapper();


    @Autowired
    public ProductService(IProductRepository productRepository, IMaterialRepository materialRepository, IProductMaterialRepository productMaterialRepository, IDiamondRepository diamondRepository) {
        this.productRepository = productRepository;
        this.materialRepository = materialRepository;
        this.productMaterialRepository = productMaterialRepository;
        this.diamondRepository = diamondRepository;
    }

    @Override
    @Transactional
    public Product saveProduct(ProductDTO productDTO) {

//        Product product = modelMapper.map(productDTO, Product.class);
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setProductType(productDTO.getProductType());
        product.setEDiamondPrice(productDTO.getEMaterialPrice());
        product.setEMaterialPrice(productDTO.getEDiamondPrice());
        product.setMarkupRate(productDTO.getMarkupRate());
        product.setProductionPrice(productDTO.getProductionPrice());

        product = productRepository.save(product);

        for (Pair<Integer, Double> materialPair : productDTO.getMaterialsIds()) {
            Material material = materialRepository.findById(materialPair.getFirst()).orElseThrow(() -> new RuntimeException("Material not found"));

            ProductMaterialId productMaterialId = new ProductMaterialId(product.getProductId(), material.getMaterialId());

            ProductMaterial productMaterial = new ProductMaterial();
            productMaterial.setId(productMaterialId);
            productMaterial.setMaterial(material);
            productMaterial.setProduct(product);
            productMaterial.setWeight(materialPair.getSecond());

            productMaterialRepository.save(productMaterial);
        }

        List<Diamond> diamonds = new ArrayList<>();
        for (Integer id : productDTO.getDiamondIds()) {
            Diamond diamond = diamondRepository.findById(id).orElse(null);
            if (diamond != null) {
                diamonds.add(diamond);
            }
        }
        product.setDiamonds(diamonds);


        return productRepository.save(product);
    }

    //Test
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
