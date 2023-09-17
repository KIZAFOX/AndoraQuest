package fr.kizafox.andora.tools.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 16/09/2023
 * @project : Andora
 */
public enum ClassUnit {

    PEASANT("Paysant"),
    WARRIOR("Guerrier");

    private final String name;

    ClassUnit(String name) {
        this.name = name;
    }

    public static final List<ClassUnit> rank = new ArrayList<>();

    static{
        rank.addAll(Arrays.asList(ClassUnit.values()));
    }

    public static ClassUnit getByName(String name){
        return Arrays.stream(values()).filter(c -> c.getName().equalsIgnoreCase(name)).findAny().orElse(ClassUnit.PEASANT);
    }

    public String getName() {
        return name;
    }
}
