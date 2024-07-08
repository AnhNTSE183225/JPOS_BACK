package com.fpt.jpos.service;

import com.fpt.jpos.dto.DiamondPriceQueryDTO;
import com.fpt.jpos.dto.ListDiamondPriceQueryDTO;
import com.fpt.jpos.pojo.DiamondPrice;
import com.fpt.jpos.repository.IDiamondPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiamondPriceService implements IDiamondPriceService {

    private final IDiamondPriceRepository diamondPriceRepository;

    @Override
    public DiamondPrice addDiamondPrice(DiamondPrice diamondPrice) {
        diamondPrice.setEffectiveDate(new Date());
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

    @Override
    public List<DiamondPrice> getDiamondPriceByOriginAndShapeAndCaratRange(String origin, String shape, Double caratFrom, Double caratTo) {
        return diamondPriceRepository.getDiamondPriceByOriginAndShape(origin, shape, caratFrom, caratTo);
    }

    @Override
    public Page<DiamondPrice> getDiamondPricesByQuery(ListDiamondPriceQueryDTO listDiamondPriceQueryDTO, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return diamondPriceRepository.getDiamondPricesByQuery(listDiamondPriceQueryDTO.getListOrigin(), listDiamondPriceQueryDTO.getListShape(), listDiamondPriceQueryDTO.getListClarity(), listDiamondPriceQueryDTO.getListColor(), listDiamondPriceQueryDTO.getListCut(), listDiamondPriceQueryDTO.getMinCarat(), listDiamondPriceQueryDTO.getMaxCarat(), pageable);
    }
}
