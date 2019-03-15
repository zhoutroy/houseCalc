package com.ggiyy.housecalc.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HouseTaxVO {
    /**
     * 税费说明
     */
    private String initPaymentDescription;
    /**
     * 税费说明2
     */
    private String deedTaxDescription;
    /**
     * 增值税说明
     */
    private String incrementTaxDescription;
    /**
     * 中介费说明
     */
    private String agencyCostDescription;
    /**
     * 附加税说明
     */
    private String additionalTaxDescription;
    /**
     * 个税说明
     */
    private String incomeTaxDescription;

    private List<HouseVO> houses = new ArrayList<>();
}
