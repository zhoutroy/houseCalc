package com.ggiyy.housecalc.vo;

import com.ggiyy.housecalc.core.Buyer;
import com.ggiyy.housecalc.core.Seller;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseVO {
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
}
