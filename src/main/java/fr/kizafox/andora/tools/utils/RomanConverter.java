package fr.kizafox.andora.tools.utils;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 17/09/2023
 * @project : Andora
 */
public class RomanConverter {

    public static String toRoman(final int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("Le nombre doit être compris entre 1 et 3999.");
        }

        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        final int thousandsDigit = number / 1000;
        final int hundredsDigit = (number % 1000) / 100;
        final int tensDigit = (number % 100) / 10;
        final int onesDigit = number % 10;

        return thousands[thousandsDigit] + hundreds[hundredsDigit] + tens[tensDigit] + ones[onesDigit];
    }
}