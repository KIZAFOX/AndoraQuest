package fr.kizafox.andora.tools.base64;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 18/09/2023
 * @project : Andora
 */
public class CustomSkull {

    public static ItemStack create(final String base64) {
        final ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        final ItemMeta headM = head.getItemMeta();

        try {
            final Field field = Objects.requireNonNull(headM).getClass().getDeclaredField("profile");
            field.setAccessible(true);

            final GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            final PropertyMap propertyMap = profile.getProperties();

            if (propertyMap == null){
                throw new IllegalStateException("Profile doesn't contain a property map");
            }

            final byte[] encodedData = base64.getBytes();
            propertyMap.put("textures", new Property("textures", new String(encodedData)));

            field.set(headM, profile);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage());
        }

        head.setItemMeta(headM);
        return head;
    }
}