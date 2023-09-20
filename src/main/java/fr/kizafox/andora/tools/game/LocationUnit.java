package fr.kizafox.andora.tools.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 19/09/2023
 * @project : Andora
 */
public enum LocationUnit {

    BOAT_SPAWN(new Location(Bukkit.getWorlds().get(0), 1494.452, 65.0, -319.463, 90.5f, 1.4f)),
    MAIN_TOWN(new Location(Bukkit.getWorlds().get(0), 1302.523, 65.0, -327.350, 164.3f, -3.0f)),
    NORTH_VILLAGE(new Location(Bukkit.getWorlds().get(0), 1302.523, 65.0, -327.350, 164.3f, -3.0f)),
    BIG_VILLAGE(new Location(Bukkit.getWorlds().get(0), 1302.523, 65.0, -327.350, 164.3f, -3.0f)),
    EAST_VILLAGE(new Location(Bukkit.getWorlds().get(0), 1302.523, 65.0, -327.350, 164.3f, -3.0f));

    private final Location location;

    LocationUnit(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}