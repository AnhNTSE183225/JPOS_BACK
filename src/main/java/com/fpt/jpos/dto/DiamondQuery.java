package com.fpt.jpos.dto;

import com.fpt.jpos.pojo.enums.Clarity;
import com.fpt.jpos.pojo.enums.Color;
import com.fpt.jpos.pojo.enums.Cut;
import com.fpt.jpos.pojo.enums.Shape;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiamondQuery {
    private Double caratWeight;
    private List<String> colorList;
    private List<String> clarityList;
    private List<String> cutList;
    private List<String> shapeList;
}
