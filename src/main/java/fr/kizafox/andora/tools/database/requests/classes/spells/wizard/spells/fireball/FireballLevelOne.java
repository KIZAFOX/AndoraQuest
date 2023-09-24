package fr.kizafox.andora.tools.database.requests.classes.spells.wizard.spells.fireball;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.ItemBuilder;
import fr.kizafox.andora.tools.database.requests.classes.spells.handler.Spell;
import fr.kizafox.andora.tools.utils.RomanConverter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 20/09/2023
 * @project : Andora
 */
public class FireballLevelOne extends Spell implements Listener {

    public FireballLevelOne(final Andora instance) {
        super(instance);

        this.instance.getServer().getPluginManager().registerEvents(this, this.instance);
    }

    @Override
    public String getName() {
        return "Fireball ";
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getManaCost() {
        return 50;
    }

    @Override
    public float getDamage() {
        return 10 * 5;
    }

    @Override
    public int getAOE() {
        return 20;
    }

    @Override
    public int getCooldown() {
        return 5;
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.ENCHANTED_BOOK).addBookEnchant(Enchantment.PROTECTION_FIRE, 1)
                .setName(ChatColor.RED + this.getName() + ChatColor.BOLD + RomanConverter.toRoman(this.getLevel()))
                .setLore(Arrays.asList(
                        ChatColor.DARK_GRAY + "Genre: " + ChatColor.AQUA + "Sort, Mage, Feu",
                        " ",
                        ChatColor.WHITE + "" + ChatColor.BOLD + "       » Description «",
                        " ",
                        ChatColor.GRAY + "  Lance une boule de feu,",
                        ChatColor.GRAY + "  qui fait des dégats de zone,",
                        ChatColor.GRAY + "  et brûle les ennemis touchés.",
                        " ",
                        ChatColor.WHITE + "" + ChatColor.BOLD + "       » Statistiques «",
                        " ",
                        ChatColor.GRAY + "  Niveau du sort: " + ChatColor.YELLOW + this.getLevel(),
                        ChatColor.GRAY + "  Coût en mana: " + ChatColor.AQUA + this.getManaCost(),
                        ChatColor.GRAY + "  Dégats: " + ChatColor.RED + this.getDamage(),
                        ChatColor.GRAY + "  Zone d'effect: " + ChatColor.RED + this.getAOE(),
                        ChatColor.GRAY + "  Temps de recharge: " + ChatColor.WHITE + this.getCooldown(),
                        "",
                        ChatColor.GREEN + "" + ChatColor.ITALIC + "Lancer le sort: L-L-L"
                )).toItemStack();
    }

    @EventHandler
    public void onSpellEvent(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack itemStack = player.getInventory().getItemInMainHand();

        if(itemStack.getItemMeta() == null) return;

        final Action action = event.getAction();

        if (itemStack.getItemMeta().getDisplayName().equals(this.getItem().getItemMeta().getDisplayName())) {
            event.setCancelled(true);

            if (cooldowns.containsKey(player.getUniqueId())) {
                long secondsLeft = ((cooldowns.get(player.getUniqueId()) / 1000) + this.getCooldown()) - (System.currentTimeMillis() / 1000);

                if (secondsLeft > 0) {
                    player.sendMessage(ChatColor.RED + "Vous devez attendre " + secondsLeft + ChatColor.RESET + ChatColor.RED + " secondes avant de pouvoir réutiliser ce sort.");
                    return;
                }
            }

            this.setupCombo(player, action, (combo) -> {
                if (combo.length() == 3) {
                    Spell.WAND_COMBO.remove(player.getUniqueId());
                    int manaLeft = Spell.MANA.get(player.getUniqueId());

                    if (combo.equals("LLL")) {
                        if (manaLeft < this.getManaCost()) {
                            player.sendMessage(ChatColor.DARK_RED + "Vous n'avez pas la mana requise pour utiliser ce sort.");
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                            return;
                        }

                        Spell.MANA.put(player.getUniqueId(), manaLeft - this.getManaCost());

                        final Fireball fireball = player.launchProjectile(Fireball.class);
                        fireball.setIsIncendiary(false);
                        fireball.setYield(this.getAOE());
                        fireball.setDirection(player.getEyeLocation().getDirection().normalize());

                        fireball.setMetadata("damage", new FixedMetadataValue(this.instance, this.getDamage()));

                        this.instance.getServer().getScheduler().runTaskLater(this.instance, fireball::remove, 20L * 3);

                        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
                    }
                }
            });
        }
    }
}