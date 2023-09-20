package fr.kizafox.andora.tools.utils;

import org.bukkit.ChatColor;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 20/09/2023
 * @project : Andora
 */
public class StringFormat {

    public static String formatCombo(String combo) {
        final StringBuilder comboBuilder = new StringBuilder(combo + ChatColor.GRAY);
        comboBuilder.append("_".repeat(Math.max(0, 3 - comboBuilder.length())));
        combo = comboBuilder.toString();
        combo = combo.toUpperCase().replace("L", ChatColor.DARK_BLUE + "L").replace("R", ChatColor.DARK_RED + "R");
        return combo;
    }
}
