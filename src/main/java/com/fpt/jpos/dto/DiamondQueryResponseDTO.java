package com.fpt.jpos.dto;

import com.fpt.jpos.pojo.Diamond;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiamondQueryResponseDTO {
    private Diamond diamond;
    private Double latestPrice;
    private Date effectiveDate;
}
