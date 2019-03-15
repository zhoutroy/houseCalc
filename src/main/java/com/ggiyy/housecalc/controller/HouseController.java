
package com.ggiyy.housecalc.controller;

import com.ggiyy.housecalc.core.House;
import com.ggiyy.housecalc.form.HouseForm;
import com.ggiyy.housecalc.helper.CorsHelper;
import com.ggiyy.housecalc.vo.HouseTaxVO;
import com.ggiyy.housecalc.vo.HouseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@RestController()
public class HouseController {
    /**
     * input:
     * isCommon : false
     * isFirst : false
     * hasLoan : true
     * isSingle : true
     * isRelocate : false
     * houseAge : 5
     * area : 100
     * agency : 2
     * lastPrice : 3000000
     * totalPrice : 6000000
     *
     * output:
     * {
     *   "isCommon": false,
     *   "isFirst": false,
     *   "hasLoan": true,
     *   "isSingle": true,
     *   "isRelocate": false,
     *   "houseAge": 5,
     *   "area": 100.0,
     *   "agency": "2",
     *   "lastPrice": 3000000.0,
     *   "totalPrice": 6000000.0,
     *   "buyer": {
     *     "deedTax": 175714.0,
     *     "agencyCost": 120000.0,
     *     "initPayment": 4200000.0
     *   },
     *   "seller": {
     *     "incrementTax": 142857.0,
     *     "additionalTax": 8571.0,
     *     "incomeTax": 0.0
     *   },
     *   "initPaymentDescription": "二套房,非普通住宅，首付\u003d核定价*70%",
     *   "deedTaxDescription": "非首套房，契税\u003d(核定价-增值税)*3%",
     *   "incrementTaxDescription": "满两年不唯一，增值税\u003d(核定价-买入价)/1.05*5%",
     *   "agencyCostDescription": "中介费\u003d核定价*2%",
     *   "additionalTaxDescription": "附加税 \u003d 增值税 / 5% * 0.3%",
     *   "incomeTaxDescription": "满五唯一没有个税"
     * }
     *
     * @param houseForm
     * @return
     */
    @PostMapping(value = "/house")
    public Object houseCalc(HouseForm houseForm, HttpServletRequest request, HttpServletResponse response) {
        CorsHelper.buildCorsResponse(request, response);
        if (houseForm.getLastPrice() == null) {
            // 设置默认价格为100w
            houseForm.setLastPrice(1000000d);
        }
        return calTaxVO(houseForm);
    }

    private static final int pointCount = 500;
    private HouseTaxVO calTaxVO(HouseForm houseForm) {
        HouseTaxVO result = new HouseTaxVO();

        Double start, end;
        if (houseForm.getLastPrice() != null) {
            start = houseForm.getLastPrice();
            end = start * 4;
            // 结束价格小于500万的,重置为500万
            if (end < 5000000) {
                end = 5000000d;
            }
        } else {
            // 默认起始100万
            start = 1000000d;
            // 默认结束1000万
            end = 10000000d;
        }

        House house = null;
        // 100个点
        double step = (end - start) / pointCount;
        for (int i = 0; i < pointCount; i++) {
            house = new House();
            BeanUtils.copyProperties(houseForm, house);
            house.setTotalPrice(Math.floor(start + i * step));
            house.calculateTax();

            HouseVO vo = new HouseVO();
            vo.setTotalPrice(house.getTotalPrice());
            vo.setBuyer(house.getBuyer());
            vo.setSeller(house.getSeller());
            result.getHouses().add(vo);
        }
        BeanUtils.copyProperties(house, result);

        return result;
    }
}
