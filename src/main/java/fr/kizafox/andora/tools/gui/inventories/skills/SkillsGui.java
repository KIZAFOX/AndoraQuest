package fr.kizafox.andora.tools.gui.inventories.skills;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.ItemBuilder;
import fr.kizafox.andora.tools.database.requests.skills.SkillUnit;
import fr.kizafox.andora.tools.utils.RomanConverter;
import fr.kizafox.andora.tools.database.requests.skills.PlayerSkills;
import fr.kizafox.andora.tools.database.requests.user.UserAccount;
import fr.kizafox.andora.tools.gui.Gui;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 17/09/2023
 * @project : Andora
 */
public class SkillsGui extends Gui implements Listener {

    private BukkitRunnable particleTask;

    /**
     * Create a new Gui easily.
     *
     * @param instance This is the instance of the main class
     * @param size     This is the size of the Gui
     * @param title    This is the title of the Gui
     */
    public SkillsGui(Andora instance, int size, String title) {
        super(instance, size, title);

        this.instance.getServer().getPluginManager().registerEvents(this, this.instance);
    }

    /**
     * Show the Gui
     *
     * @param player The player
     */
    @Override
    public void display(Player player) {
        final Inventory inventory = Bukkit.createInventory(null, this.getSize(), this.getTitle());

        final PlayerSkills playerSkills = UserAccount.getUserAccount(player).getPlayerSkills();

        inventory.setItem(13, new ItemBuilder(Material.EXPERIENCE_BOTTLE, (playerSkills.getSkillPoint() == 0 ? 1 : playerSkills.getSkillPoint()))
                .setName(ChatColor.GREEN + "Point de compétence restant")
                .setLore(Arrays.asList("", ChatColor.GRAY + "Vous avez " + ChatColor.GREEN + playerSkills.getSkillPoint() + ChatColor.GRAY + " point de compétence restant"))
                .toItemStack());

        inventory.setItem(29, this.skillItem(SkillUnit.DAMAGE, playerSkills.getDamageBonus()));
        inventory.setItem(33, this.skillItem(SkillUnit.HEALTH, playerSkills.getHealthBonus()));

        this.instance.getServer().getScheduler().runTask(this.instance, () -> player.openInventory(inventory));
    }

    /**
     * Interaction with the Gui
     *
     * @param event The InventoryClickEvent event from listener
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(this.getTitle())) {
            if (event.getCurrentItem() == null) return;

            final Player player = (Player) event.getWhoClicked();
            final ItemStack itemStack = event.getCurrentItem();
            final ClickType clickType = event.getClick();

            switch (itemStack.getType()) {
                case IRON_SWORD -> this.handleSkillClick(player, SkillUnit.DAMAGE, clickType);
                case APPLE -> this.handleSkillClick(player, SkillUnit.HEALTH, clickType);
            }
            event.setCancelled(true);
        }
    }

    private ItemStack skillItem(final SkillUnit skillEnum, final double bonusPoint) {
        final ItemBuilder item = new ItemBuilder(skillEnum.getMaterial(), (int) Math.max(bonusPoint, 1))
                .setName(ChatColor.GREEN + skillEnum.getName() + " " + (bonusPoint == 0 ? ChatColor.YELLOW + "" + ChatColor.ITALIC + "(Pas encore augmenté)" : ChatColor.GREEN + "" + ChatColor.BOLD + RomanConverter.toRoman((int) bonusPoint)))
                .setLore(Arrays.asList(
                        "",
                        ChatColor.GRAY + "Vous avez alloué " + ChatColor.AQUA + (int) bonusPoint + ChatColor.GRAY + (bonusPoint == 0 ? " point" : " points") + " dans cette compétence,",
                        ChatColor.GRAY + "bonus de " + ChatColor.LIGHT_PURPLE + skillEnum.getPercentage() + "%" + ChatColor.GRAY + " en plus par point ajouté",
                        "",
                        ChatColor.GRAY + "Bonus actuel: " + ChatColor.LIGHT_PURPLE + (bonusPoint * skillEnum.getPercentage()) + "%",
                        "",
                        ChatColor.GRAY + "Clique-gauche pour allouer " + ChatColor.BOLD + ChatColor.ITALIC + "1 point",
                        ChatColor.GRAY + "Clique-droit pour retirer " + ChatColor.BOLD + ChatColor.ITALIC + "1 point",
                        "",
                        ChatColor.RED + "Vous pouvez allouer un maximum de " + ChatColor.DARK_RED + skillEnum.getMaxLevel() + ChatColor.RED + " points.",
                        ChatColor.RED + "Encore " + ChatColor.DARK_RED + (skillEnum.getMaxLevel() - bonusPoint) + " " + ChatColor.RED + (skillEnum.getMaxLevel() - bonusPoint == 0 ? "point" : "points") + " pour atteindre le niveau maximum."
                ));

        if (bonusPoint == 10) {
            if(skillEnum.getName().equals(SkillUnit.HEALTH.getName())){
                this.particleTask = new BukkitRunnable() {
                    double angle = 0;

                    @Override
                    public void run() {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            Location playerLocation = player.getLocation();
                            double radius = 1.0;

                            double x = playerLocation.getX() + radius * Math.cos(angle);
                            double y = playerLocation.getY() + .5;
                            double z = playerLocation.getZ() + radius * Math.sin(angle);

                            player.spawnParticle(Particle.HEART, x, y, z, 1);

                            angle += Math.PI / 8;
                        }
                    }
                };

                particleTask.runTaskTimer(this.instance, 0, 2);
            }

            return item.setName(ChatColor.GREEN + skillEnum.getName() + " " + ChatColor.BOLD + RomanConverter.toRoman((int) bonusPoint) + " " + ChatColor.GOLD + "(NIVEAU MAX)").hideEnchant().toItemStack();
        } else {
            if(this.particleTask != null) this.particleTask.cancel();
            return item.toItemStack();
        }
    }

    private void handleSkillClick(final Player player, final SkillUnit skillUnit, final ClickType clickType) {
        if (clickType.isLeftClick()) {
            this.increaseSkill(player, skillUnit);
        } else if (clickType.isRightClick()) {
            this.decreaseSkill(player, skillUnit);
        }
        this.display(player);
    }

    private void increaseSkill(final Player player, final SkillUnit skillUnit) {
        final PlayerSkills playerSkills = UserAccount.getUserAccount(player).getPlayerSkills();

        if (playerSkills.getSkillPoint() == 0) {
            player.sendMessage(ChatColor.RED + "Vous n'avez plus de point de compétence");
            return;
        }

        switch (skillUnit){
            case DAMAGE -> {
                if (playerSkills.getDamageBonus() == skillUnit.getMaxLevel()) {
                    player.sendMessage(ChatColor.RED + "Vous avez atteint le maximum de point pour cette compétence.");
                    return;
                }
                playerSkills.addDamageBonus(1);
                player.getAttribute(skillUnit.getAttribute()).setBaseValue(player.getAttribute(skillUnit.getAttribute()).getBaseValue() + skillUnit.getValue());
            }
            case HEALTH -> {
                if (playerSkills.getHealthBonus() == skillUnit.getMaxLevel()) {
                    player.sendMessage(ChatColor.RED + "Vous avez atteint le maximum de point pour cette compétence.");
                    return;
                }
                playerSkills.addHealthBonus(1);
                player.getAttribute(skillUnit.getAttribute()).setBaseValue(player.getAttribute(skillUnit.getAttribute()).getBaseValue() + skillUnit.getValue());
            }
        }
        playerSkills.removeSkillPoint(1);
    }

    private void decreaseSkill(final Player player, final SkillUnit skillUnit){
        final PlayerSkills playerSkills = UserAccount.getUserAccount(player).getPlayerSkills();

        switch (skillUnit){
            case DAMAGE -> {
                if (playerSkills.getDamageBonus() == 0) {
                    player.sendMessage(ChatColor.RED + "Vous n'avez pas encore alloué de point dans cette compétence");
                    return;
                }
                playerSkills.removeDamageBonus(1);
                player.getAttribute(skillUnit.getAttribute()).setBaseValue(player.getAttribute(skillUnit.getAttribute()).getBaseValue() - skillUnit.getValue());
            }
            case HEALTH -> {
                if (playerSkills.getHealthBonus() == 0) {
                    player.sendMessage(ChatColor.RED + "Vous avez atteint le maximum de point pour cette compétence.");
                    return;
                }
                playerSkills.removeHealthBonus(1);
                player.getAttribute(skillUnit.getAttribute()).setBaseValue(player.getAttribute(skillUnit.getAttribute()).getBaseValue() - skillUnit.getValue());
            }
        }
        playerSkills.addSkillPoint(1);
    }
}
