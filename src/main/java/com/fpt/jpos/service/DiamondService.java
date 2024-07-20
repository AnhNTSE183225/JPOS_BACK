package com.fpt.jpos.service;

import com.fpt.jpos.dto.DiamondQueryDTO;
import com.fpt.jpos.dto.DiamondQueryResponseDTO;
import com.fpt.jpos.pojo.Diamond;
import com.fpt.jpos.pojo.enums.*;
import com.fpt.jpos.repository.IDiamondRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiamondService implements IDiamondService {
    private final IDiamondRepository diamondRepository;

    @Override
    public List<Diamond> getAllDiamond() {
        return diamondRepository.findAll();
    }

    @Override
    public List<DiamondQueryResponseDTO> getDiamondWithPriceBy4C(DiamondQueryDTO diamondQueryDTO) {
//        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Object[]> rawResult = diamondRepository.getDiamondBy4C(
                diamondQueryDTO.getOrigin(),
                diamondQueryDTO.getShapeList(),
                diamondQueryDTO.getMinPrice(),
                diamondQueryDTO.getMaxPrice(),
                diamondQueryDTO.getMinCarat(),
                diamondQueryDTO.getMaxCarat(),
                diamondQueryDTO.getColorList(),
                diamondQueryDTO.getClarityList(),
                diamondQueryDTO.getCutList()
        );
        return rawResult.stream().map(row -> {

            Diamond diamond = new Diamond();
            diamond.setDiamondId((Integer) row[0]);
            diamond.setDiamondCode((String) row[1]);
            diamond.setDiamondName((String) row[2]);
            diamond.setShape(Shape.valueOf((String) row[3]));
            diamond.setOrigin(Origin.valueOf((String) row[4]));
            diamond.setProportions((String) row[5]);
            diamond.setFluorescence(Fluorescence.valueOf((String) row[6]));
            diamond.setSymmetry(Symmetry.valueOf((String) row[7]));
            diamond.setPolish(Polish.valueOf((String) row[8]));
            diamond.setCut(Cut.valueOf((String) row[9]));
            diamond.setColor(Color.valueOf((String) row[10]));
            diamond.setClarity(Clarity.valueOf((String) row[11]));
            diamond.setCaratWeight(((BigDecimal) row[12]).doubleValue());
            diamond.setNote((String) row[13]);
            diamond.setImage((String) row[14]);
            diamond.setActive((Boolean) row[15]);

            return new DiamondQueryResponseDTO(diamond, ((BigDecimal) row[16]).doubleValue(), (Date) row[17]);
        }).collect(Collectors.toList());
    }

    @Override
    public Diamond getDiamondById(int diamondId) {
        return diamondRepository.findById(diamondId).orElseThrow();
    }

    @Override
    public void deleteDiamond(int diamondId) {
        diamondRepository.deleteById(diamondId);
    }

    @Override
    public Diamond updateDiamond(Diamond diamond) {
        return diamondRepository.save(diamond);
    }

    @Override
    public Diamond save(Diamond diamond) {
        return diamondRepository.save(diamond);
    }
}
