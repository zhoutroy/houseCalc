package com.ggiyy.housecalc.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class HouseForm {
    /**
     * 是否为普通房
     * 内环450,中环310,外环140
     */
    @NotEmpty
    private Boolean isCommon;
    /**
     * 是否为首套
     */
    @NotEmpty
    private Boolean isFirst;
    /**
     * 是否有贷款记录
     */
    @NotEmpty
    private Boolean hasLoan;
    /**
     * 是否为唯一住房
     */
    @NotEmpty
    private Boolean isSingle;
    /**
     * 是否为动迁房
     */
    @NotEmpty
    private Boolean isRelocate;
    /**
     * 房产年限
     */
    @NotEmpty
    private Integer houseAge;
    /**
     * 建筑面积
     */
    @NotEmpty
    private Double area;
    /**
     * 中介费用
     * 1 --> 1%
     * 2 --> 2%
     */
    @NotEmpty
    private String agency;
    /**
     * 房东之前房屋买入价
     */
    private Double lastPrice;
    /**
     * 房屋现在卖出价 --> 自动生成(以lastPrice起步, lastPrice * 4止, 一共100个点)
     */
    private Double totalPrice;
}
