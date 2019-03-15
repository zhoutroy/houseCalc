package com.ggiyy.housecalc.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Seller {
    /**
     * 增值税
     * 动迁房没有增值税
     * 满两年唯一没有增值税
     * 满两年不唯一，增值税=(核定价-买入价)/1.05*5%
     * 不满两年，增值税=核定价/1.05*5%
     */
    public Double incrementTax;
    /**
     * 附加税
     * 动迁房没有附加税
     * 附加税 = 增值税 / 5% * 0.3%
     */
    public Double additionalTax;
    /**
     * 个税
     * 满五唯一没有个税
     * 不满五唯一，动迁房,个税=(核定价-增值税)*1%
     * 不满五唯一，非普通住宅,个税=(核定价-增值税)*2%
     * 不满五唯一，普通住宅,个税=(核定价-增值税)*1%
     * 或利润*20%，个税=利润的20%
     */
    public Double incomeTax;
}
