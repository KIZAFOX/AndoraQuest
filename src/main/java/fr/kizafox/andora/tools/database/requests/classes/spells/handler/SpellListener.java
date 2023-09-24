package fr.kizafox.andora.tools.database.requests.classes.spells.handler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public interface SpellListener {

    Map<UUID, Long> cooldowns = new HashMap<>();

    void onSpellEvent(final PlayerInteractEvent event);

    default void setupCombo(final Player player, final Action action, final Consumer<String> comboHandler){
        String combo = Spell.WAND_COMBO.get(player.getUniqueId());

        if (combo == null || combo.length() < 3) {
            if (combo == null) {
                combo = "";
            }
            if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
                combo += "L";
            } else if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                combo += "R";
            }
            Spell.WAND_COMBO.put(player.getUniqueId(), combo);
        }

        comboHandler.accept(combo);
    }
}
