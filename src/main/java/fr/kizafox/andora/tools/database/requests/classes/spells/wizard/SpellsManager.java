package fr.kizafox.andora.tools.database.requests.classes.spells.wizard;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.managers.listeners.CancelListeners;
import fr.kizafox.andora.managers.listeners.PlayerListeners;
import fr.kizafox.andora.tools.database.requests.classes.spells.Spell;
import fr.kizafox.andora.tools.database.requests.classes.spells.wizard.spells.fireball.FireballLevelOne;
import fr.kizafox.andora.tools.database.requests.classes.spells.wizard.spells.teleport.Teleport;
import fr.kizafox.andora.tools.utils.StringFormat;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 20/09/2023
 * @project : Andora
 */
public class SpellsManager {

    protected final Andora instance;

    private final FireballLevelOne fireballLevelOne;
    private final Teleport teleport;

    private final List<ItemStack> itemForSpell;

    public SpellsManager(final Andora instance) {
        this.instance = instance;

        this.fireballLevelOne = new FireballLevelOne();
        this.teleport = new Teleport();

        this.itemForSpell = new ArrayList<>();
        this.itemForSpell.add(fireballLevelOne.getItem());
        this.itemForSpell.add(teleport.getItem());

        new BukkitRunnable() {

            /**
             * Actionbar task for mana and combo
             */
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(players -> {
                    if (players.getInventory().getItemInMainHand().getItemMeta() == null) return;

                    if (players.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(teleport.getItem().getItemMeta().getDisplayName()) ||
                        players.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(fireballLevelOne.getItem().getItemMeta().getDisplayName())) {
                        if (!Spell.mana.containsKey(players.getUniqueId())) {
                            Spell.mana.put(players.getUniqueId(), 200);
                        }

                        String message = ChatColor.AQUA + "Mana: " + ChatColor.GREEN + Spell.mana.get(players.getUniqueId()) + ChatColor.AQUA + " / " + ChatColor.GREEN + "200";
                        final String combo = Spell.wandCombo.get(players.getUniqueId());

                        if (combo != null) {
                            message = message + "   " + ChatColor.YELLOW + "Combo: " + ChatColor.GREEN + StringFormat.formatCombo(combo);
                        }

                        players.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
                    } else {
                        Spell.wandCombo.remove(players.getUniqueId());
                    }
                });
            }
        }.runTaskTimer(this.instance, 0L, 1L);

        new BukkitRunnable() {

            /**
             * Mana regen task
             */
            @Override
            public void run() {
                Spell.mana.keySet().forEach(uuid -> {
                    int manaLeft = Spell.mana.get(uuid);

                    if (manaLeft < 200) {
                        Spell.mana.put(uuid, manaLeft + 1);
                    }
                });
            }
        }.runTaskTimer(this.instance, 0L, 10L);
    }

    public FireballLevelOne getFireballLevelOne() {
        return fireballLevelOne;
    }

    public Teleport getTeleport() {
        return teleport;
    }

    public List<ItemStack> getItemForSpell() {
        return itemForSpell;
    }
}
