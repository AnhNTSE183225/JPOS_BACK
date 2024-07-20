package com.fpt.jpos.service;

import com.fpt.jpos.dto.DiamondQueryDTO;
import com.fpt.jpos.dto.DiamondQueryResponseDTO;
import com.fpt.jpos.pojo.Diamond;

import java.util.List;

public interface IDiamondService {
    List<Diamond> getAllDiamond();

    List<DiamondQueryResponseDTO> getDiamondWithPriceBy4C(DiamondQueryDTO diamondQueryDTO);

    Diamond getDiamondById(int diamondId);

    void deleteDiamond(int diamondId);

    Diamond updateDiamond(Diamond diamond);

    Diamond save(Diamond diamond);
}
