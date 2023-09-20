package fr.kizafox.andora.tools;

import fr.kizafox.andora.Andora;
import org.bukkit.entity.Player;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 20/09/2023
 * @project : Andora
 */
public class TexturePack {

    public static void load(final Player player){
        Andora.get().getServer().getScheduler().scheduleSyncDelayedTask(Andora.get(), () -> player.setResourcePack("https://www.curseforge.com/api/v1/mods/478165/files/4400733/download"), 20L);
    }
}
