package com.ggiyy.housecalc.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Buyer {
    /**
     * 契税
     * 首套普通住宅，契税=(核定价-增值税)*1%
     * 首套非普通住宅，契税=(核定价-增值税)*1.5%
     * 非首套房，契税=(核定价-增值税)*3%
     */
    public Double deedTax;
    /**
     * 中介费
     * 中介费=核定价*x%
     */
    public Double agencyCost;
    /**
     * 首付 (无房有贷款记录算二套), 内环450,中环310,外环140为普通和非普通界限
     * 无房无贷款记录,首付=核定价*35%
     * 普通住宅，首付=核定价*50%
     * 非普通住宅，首付=核定价*70%
     */
    public Double initPayment;
}
