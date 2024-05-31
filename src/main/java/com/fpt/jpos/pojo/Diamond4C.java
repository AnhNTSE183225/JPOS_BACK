package com.fpt.jpos.pojo;

import com.fpt.jpos.pojo.enums.Clarity;
import com.fpt.jpos.pojo.enums.Color;
import com.fpt.jpos.pojo.enums.Cut;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diamond4C {
    private Color color;
    private Clarity clarity;
    private Cut cut;
    private Double caratWeight;
}