package fr.kizafox.andora.tools.database.requests.classes.spells;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.database.requests.classes.spells.archer.ArcherSpellsManager;
import fr.kizafox.andora.tools.database.requests.classes.spells.dwarf.DwarfSpellsManager;
import fr.kizafox.andora.tools.database.requests.classes.spells.handler.Spell;
import fr.kizafox.andora.tools.database.requests.classes.spells.warrior.WarriorSpellsManager;
import fr.kizafox.andora.tools.database.requests.classes.spells.wizard.WizardSpellsManager;
import fr.kizafox.andora.tools.utils.Maths;
import fr.kizafox.andora.tools.utils.StringFormat;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
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

    private final WarriorSpellsManager warriorSpellsManager;
    private final DwarfSpellsManager dwarfSpellsManager;
    private final ArcherSpellsManager archerSpellsManager;
    private final WizardSpellsManager wizardSpellsManager;

    public SpellsManager(final Andora instance) {
        this.instance = instance;

        this.warriorSpellsManager = new WarriorSpellsManager(this.instance);
        this.dwarfSpellsManager = new DwarfSpellsManager(this.instance);
        this.archerSpellsManager = new ArcherSpellsManager(this.instance);
        this.wizardSpellsManager = new WizardSpellsManager(this.instance);

        new BukkitRunnable() {

            /**
             * Actionbar task for mana and combo
             */
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(players -> {
                    String message = ChatColor.RED + "" + Maths.around(players.getHealth()) + ChatColor.DARK_RED + "/" + ChatColor.RED + players.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + ChatColor.DARK_RED + " ❤   " + ChatColor.BLUE + Spell.MANA.get(players.getUniqueId()) + ChatColor.DARK_BLUE + "/" + ChatColor.BLUE + "200" + ChatColor.DARK_BLUE + " 💧";

                    Spell.MANA.put(players.getUniqueId(), 200);

                    if (players.getInventory().getItemInMainHand().getItemMeta() != null){
                        boolean isSpellItem = false;

                        final List<ItemStack> items = new ArrayList<>();

                        items.addAll(warriorSpellsManager.getItemForSpell());
                        items.addAll(dwarfSpellsManager.getItemForSpell());
                        items.addAll(archerSpellsManager.getItemForSpell());
                        items.addAll(wizardSpellsManager.getItemForSpell());

                        for (ItemStack spellItem : items) {
                            if (players.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(spellItem.getItemMeta().getDisplayName())) {
                                isSpellItem = true;
                                break;
                            }
                        }

                        if (isSpellItem) {
                            final String combo = Spell.WAND_COMBO.get(players.getUniqueId());

                            if (combo != null) {
                                message = message + "   " + ChatColor.GREEN + "Combo: " + ChatColor.GREEN + StringFormat.formatCombo(combo);
                            }
                        } else {
                            Spell.WAND_COMBO.remove(players.getUniqueId());
                        }
                    }

                    players.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
                });
            }
        }.runTaskTimer(this.instance, 0L, 1L);

        new BukkitRunnable() {

            /**
             * Mana regen task
             */
            @Override
            public void run() {
                Spell.MANA.keySet().forEach(uuid -> {
                    int manaLeft = Spell.MANA.get(uuid);

                    if (manaLeft < 200) {
                        Spell.MANA.put(uuid, manaLeft + 1);
                    }
                });
            }
        }.runTaskTimer(this.instance, 0L, 10L / 3);
    }

    public WarriorSpellsManager getWarriorSpellsManager() {
        return warriorSpellsManager;
    }

    public DwarfSpellsManager getDwarfSpellsManager() {
        return dwarfSpellsManager;
    }

    public ArcherSpellsManager getArcherSpellsManager() {
        return archerSpellsManager;
    }

    public WizardSpellsManager getWizardSpellsManager() {
        return wizardSpellsManager;
    }
}
