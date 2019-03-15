package com.ggiyy.housecalc.core;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class House {
    /**
     * 是否为普通房
     * 内环450,中环310,外环140
     */
    private Boolean isCommon;
    /**
     * 是否为首套
     */
    private Boolean isFirst;
    /**
     * 是否有贷款记录
     */
    private Boolean hasLoan;
    /**
     * 是否为唯一住房
     */
    private Boolean isSingle;
    /**
     * 是否为动迁房
     */
    private Boolean isRelocate;
    /**
     * 房产年限
     */
    private Integer houseAge;
    /**
     * 建筑面积
     */
    private Double area;
    /**
     * 中介费用
     * 1 --> 1%
     * 2 --> 2%
     */
    private String agency;
    /**
     * 房东之前房屋买入价
     */
    private Double lastPrice;
    /**
     * 房屋现在卖出价
     */
    private Double totalPrice;
    /**
     * 买方
     */
    private Buyer buyer = new Buyer();
    /**
     * 卖方
     */
    private Seller seller = new Seller();

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

    public House(Boolean isCommon, Boolean isFirst, Boolean hasLoan, Boolean isSingle, Boolean isRelocate,
                 Integer houseAge, Double area, String agency, Double lastPrice, Double totalPrice) {
        this.isCommon = isCommon;
        this.isFirst = isFirst;
        this.hasLoan = hasLoan;
        this.isSingle = isSingle;
        this.isRelocate = isRelocate;
        this.houseAge = houseAge;
        this.area = area;
        this.agency = agency;
        this.lastPrice = lastPrice;
        this.totalPrice = totalPrice;

        if (this.totalPrice < this.lastPrice) {
            this.lastPrice = this.totalPrice;
        }
    }

    public Double buyerPay() {
        return this.buyer.deedTax + this.buyer.agencyCost + this.buyer.initPayment;
    }

    public Double sellerPay() {
        return this.seller.incrementTax + this.seller.additionalTax + this.seller.incomeTax;
    }

    public Double totalPay() {
        return this.buyerPay() + this.sellerPay();
    }

    public Double initPaymentCalculate() {
        Double ratio = 0.35;

        if (isFirst && !hasLoan) {
            //首套房
            this.initPaymentDescription = "无房无贷款记录,首付=核定价*35%";
            ratio = 0.35;
        } else {
            //二套
            if (!isFirst) {
                this.initPaymentDescription = "二套房,";
            } else {
                this.initPaymentDescription = "无房有贷款记录算二套,";
            }

            if (isCommon) {
                ratio = 0.5;
                this.initPaymentDescription += "普通住宅，首付=核定价*50%";
            } else {
                ratio = 0.7;
                this.initPaymentDescription += "非普通住宅，首付=核定价*70%";
            }
        }

        return totalPrice * ratio;
    }

    public Double deedTaxCalculate(Double incrementTax) {
        Double ratio = 0.01;

        if (isFirst) {
            if (area <= 90) {
                ratio = 0.01;
                this.deedTaxDescription = "首套普通住宅，契税=(核定价-增值税)*1%";
            } else {
                ratio = 0.015;
                this.deedTaxDescription = "首套非普通住宅，契税=(核定价-增值税)*1.5%";
            }
        } else {
            ratio = 0.03;
            this.deedTaxDescription = "非首套房，契税=(核定价-增值税)*3%";
        }

        return (totalPrice - incrementTax) * ratio;
    }

    public Double agencyCostCalculate() {
        Double ratio = 0.01;
        switch (agency) {
            case "1":
                ratio = 0.01;
                this.agencyCostDescription = "中介费=核定价*1%";
                break;
            case "2":
                ratio = 0.02;
                this.agencyCostDescription = "中介费=核定价*2%";
                break;
            case "3":
                ratio = 0.03;
                this.agencyCostDescription = "中介费=核定价*3%";
                break;
            case "4":
                ratio = 0.04;
                this.agencyCostDescription = "中介费=核定价*4%";
                break;
        }
        return totalPrice * ratio;
    }

    public Double incrementTaxCalculate() {
        //动迁房不交增值税
        if (isRelocate) {
            this.incrementTaxDescription = "动迁房没有增值税";
            return 0d;
        }

        Double base = totalPrice;
        if (houseAge >= 2) {
            if (isCommon) {
                this.incrementTaxDescription = "满两年唯一没有增值税";
                return 0d;
            } else {
                base = totalPrice - lastPrice;
                this.incrementTaxDescription = "满两年不唯一，增值税=(核定价-买入价)/1.05*5%";
            }
        } else {
            this.incrementTaxDescription = "不满两年，增值税=核定价/1.05*5%";
        }
        return base / 1.05 * 0.05;
    }

    public Double additionalTaxCalculate(Double incrementTax) {
        //动迁房不交附加税
        if (isRelocate) {
            this.additionalTaxDescription = "动迁房没有附加税";
            return 0d;
        }
        this.additionalTaxDescription = "附加税 = 增值税 / 5% * 0.3%";
        return incrementTax / 0.05 * 0.003;
    }

    public Double incomeTaxCalculate(Double incrementTax, Double additionalTax) {
        if (houseAge >= 5 && isSingle) {
            this.incomeTaxDescription = "满五唯一没有个税";
            return 0d;
        }
        //动迁房1%个税
        if (isRelocate) {
            this.incomeTaxDescription = "不满五唯一，动迁房,个税=(核定价-增值税)*1%";
            return totalPrice * 0.01;
        }

        Double ratio = 0.01;
        if (!isCommon) {
            this.incomeTaxDescription = "不满五唯一，非普通住宅,个税=(核定价-增值税)*2%";
            ratio = 0.02;
        } else {
            this.incomeTaxDescription = "不满五唯一，普通住宅,个税=(核定价-增值税)*1%";
        }
        Double ratioCost = (totalPrice - incrementTax) * ratio;
        Double profit = (totalPrice - lastPrice - incrementTax - additionalTax) * 0.2;

        if (ratioCost < profit) {
            return ratioCost;
        } else {
            if (profit > 0) {
                this.incomeTaxDescription += "或利润*20%，个税=利润的20%";
                return profit;
            } else {
                this.incomeTaxDescription += "无利润,个税为0";
                return 0d;
            }
        }
    }

    public void calculateTax() {
        this.seller.incrementTax = Math.floor(this.incrementTaxCalculate());
        this.seller.additionalTax = Math.floor(this.additionalTaxCalculate(this.seller.incrementTax));
        this.seller.incomeTax = Math.floor(this.incomeTaxCalculate(this.seller.incrementTax, this.seller.additionalTax));
        this.buyer.deedTax = Math.floor(this.deedTaxCalculate(this.seller.incrementTax));
        this.buyer.agencyCost = Math.floor(this.agencyCostCalculate());
        this.buyer.initPayment = Math.floor(this.initPaymentCalculate());
    }

}
