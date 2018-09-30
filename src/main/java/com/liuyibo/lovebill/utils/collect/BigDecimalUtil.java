package com.liuyibo.lovebill.utils.collect;

import java.math.BigDecimal;

/**
 * BigDecimal工具类
 *
 * @author wangl_lc
 * @version 2018-08-26
 */
public class BigDecimalUtil {

    /**
     * Object 转成指定位数的 BigDecimal
     *
     * @author wangl_lc
     * @version 2018-08-26
     */
    public static BigDecimal toBigDecimal(Object obj, int i) {
        BigDecimal zero = new BigDecimal("0.00");
        BigDecimal tempBigDecimal = zero;
        String str = (obj == null || "".equals(obj)) ? "0.00" : obj.toString();
        tempBigDecimal = new BigDecimal(str);
        tempBigDecimal = tempBigDecimal.setScale(i, BigDecimal.ROUND_HALF_UP);
        return tempBigDecimal;
    }
}
