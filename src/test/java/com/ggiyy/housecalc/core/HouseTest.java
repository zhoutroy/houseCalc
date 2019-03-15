package com.ggiyy.housecalc.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class HouseTest {

    @Test
    public void calculateTax() {
        // Boolean isCommon, Boolean isFirst, Boolean hasLoan, Boolean isSingle, Boolean isRelocate,
        //                 Integer houseAge, Double area, String agency, Double lastPrice, Double totalPrice
        House house = new House(false, false, true, true, false, 5, 100d, "1", 3000000d, 6000000d);
        house.calculateTax();
        System.out.println(house.getBuyer());
        System.out.println(house.getSeller());
        System.out.println("buyerPay:" + house.buyerPay());
        System.out.println("sellerPay:" + house.sellerPay());
    }
}