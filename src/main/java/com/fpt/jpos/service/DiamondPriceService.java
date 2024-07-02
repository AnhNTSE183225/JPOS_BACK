package com.fpt.jpos.service;

import com.fpt.jpos.dto.DiamondPriceQueryDTO;
import com.fpt.jpos.pojo.DiamondPrice;
import com.fpt.jpos.repository.IDiamondPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiamondPriceService implements IDiamondPriceService {

    private final IDiamondPriceRepository diamondPriceRepository;

    @Override
    public DiamondPrice addDiamondPrice(DiamondPrice diamondPrice) {
        return diamondPriceRepository.save(diamondPrice);
    }

    @Override
    public int updateDiamondPrice(DiamondPrice diamondPrice) {
        return diamondPriceRepository.update(diamondPrice.getPrice(), diamondPrice.getDiamondPriceId());
    }

    @Override
    public void deletePrice(Integer diamondPriceId) {
        diamondPriceRepository.deleteById(diamondPriceId);
    }

    @Override
    public List<DiamondPrice> getAllDiamondPrice() {
        return diamondPriceRepository.getAllDiamondPrice();
    }

    @Override
    public Double getSingleDiamondPrice(DiamondPriceQueryDTO diamondPriceQueryDTO) {
        return diamondPriceRepository.getSingleDiamondPrice(
                diamondPriceQueryDTO.getOrigin().name(),
                diamondPriceQueryDTO.getShape().name(),
                diamondPriceQueryDTO.getCaratWeight(),
                diamondPriceQueryDTO.getColor().name(),
                diamondPriceQueryDTO.getClarity().name(),
                diamondPriceQueryDTO.getCut().name()
        );
    }
}
