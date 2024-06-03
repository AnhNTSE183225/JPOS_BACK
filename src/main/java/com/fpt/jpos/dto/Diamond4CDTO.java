package com.fpt.jpos.dto;

import com.fpt.jpos.pojo.enums.Clarity;
import com.fpt.jpos.pojo.enums.Color;
import com.fpt.jpos.pojo.enums.Cut;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diamond4CDTO {
    private Color color;
    private Clarity clarity;
    private Cut cut;
    private Double fromCaratWeight;
    private Double toCaratWeight;
}
