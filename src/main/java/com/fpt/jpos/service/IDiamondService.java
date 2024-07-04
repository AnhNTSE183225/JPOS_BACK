package com.fpt.jpos.service;

import com.fpt.jpos.dto.DiamondQueryDTO;
import com.fpt.jpos.dto.DiamondQueryResponseDTO;
import com.fpt.jpos.pojo.Diamond;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDiamondService {
    List<Diamond> getAllDiamond();

    Page<DiamondQueryResponseDTO> getDiamondWithPriceBy4C(DiamondQueryDTO diamondQueryDTO, int pageNo, int pageSize);

    Diamond getDiamondById(int diamondId);

    void deleteDiamond(int diamondId);

    Diamond updateDiamond(Diamond diamond);
}
