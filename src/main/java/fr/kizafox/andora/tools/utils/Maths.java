package fr.kizafox.andora.tools.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 17/09/2023
 * @project : Andora
 */
public class Maths {

    public static double around(double value){
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
