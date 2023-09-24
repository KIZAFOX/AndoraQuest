package fr.kizafox.andora.tools.gui.inventories.classes;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.ItemBuilder;
import fr.kizafox.andora.tools.base64.CustomSkull;
import fr.kizafox.andora.tools.database.requests.classes.ClassUnit;
import fr.kizafox.andora.tools.database.requests.skills.PlayerSkills;
import fr.kizafox.andora.tools.database.requests.user.UserAccount;
import fr.kizafox.andora.tools.gui.Gui;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 18/09/2023
 * @project : Andora
 */
public class ClassesGui extends Gui implements Listener {
    /**
     * Create a new Gui easily.
     *
     * @param instance This is the instance of the main class
     * @param size     This is the size of the Gui
     * @param title    This is the title of the Gui
     */
    public ClassesGui(Andora instance, int size, String title) {
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
        /**
        if(!UserAccount.getUserAccount(player).getClassUnit().equals(ClassUnit.PEASANT)){
            player.sendMessage(ChatColor.RED + "Vous ne pouvez pas changer de classe !");
            return;
        }
         **/

        final Inventory inventory = Bukkit.createInventory(null, this.getSize(), this.getTitle());

        inventory.setItem(4, new ItemBuilder(Material.NETHER_STAR).setName(ChatColor.AQUA + "" + ChatColor.BOLD + "Choix aléatoire").setLore(Arrays.asList(
                "",
                ChatColor.GREEN + "» Cliquez pour choisir une classe aléatoire",
                ""
        )).toItemStack());

        Arrays.stream(ClassUnit.values())
                .filter(heads -> !heads.getName().equalsIgnoreCase(ClassUnit.PEASANT.getName()))
                .forEach(heads -> inventory.setItem(heads.getSlot(), new ItemBuilder(CustomSkull.create(heads.getHead().getUrl()))
                        .setName(ChatColor.GOLD + heads.getName())
                        .setLore(heads.getDescription())
                        .toItemStack()));

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

            if(itemStack.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "" + ChatColor.BOLD + "Choix aléatoire")){
                final List<ClassUnit> classesList = new ArrayList<>();

                Arrays.stream(ClassUnit.values())
                        .filter(classUnit -> !classUnit.getName().equalsIgnoreCase(ClassUnit.PEASANT.getName()))
                        .forEach(classesList::add);

                this.selectClass(player, classesList.get(new Random().nextInt(classesList.size())));
                player.closeInventory();
                return;
            }

            switch (itemStack.getItemMeta().getDisplayName()) {
                case "§6Guerrier" -> this.selectClass(player, ClassUnit.WARRIOR);
                case "§6Nain" -> this.selectClass(player, ClassUnit.DWARF);
                case "§6Archère" -> this.selectClass(player, ClassUnit.ARCHER);
                case "§6Magicien" -> this.selectClass(player, ClassUnit.WIZARD);
            }

            event.setCancelled(true);
        }
    }

    private void selectClass(final Player player, final ClassUnit classUnit){
        final UserAccount userAccount = UserAccount.getUserAccount(player);

        switch (classUnit){
            case WARRIOR -> userAccount.setClassUnit(ClassUnit.WARRIOR);
            case DWARF -> userAccount.setClassUnit(ClassUnit.DWARF);
            case ARCHER -> userAccount.setClassUnit(ClassUnit.ARCHER);
            case WIZARD -> userAccount.setClassUnit(ClassUnit.WIZARD);
        }

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + classUnit.getHealth());
        player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(classUnit.getArmor());
        player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(classUnit.getArmorToughness());
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(classUnit.getAttackDamage());
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(classUnit.getAttackSpeed());
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(classUnit.getMovementSpeed());
        player.sendMessage(ChatColor.GREEN + "Vous avez choisi la classe " + ChatColor.BOLD + classUnit.getName().toUpperCase() + ChatColor.RESET + ChatColor.GREEN + ".");
    }
}
