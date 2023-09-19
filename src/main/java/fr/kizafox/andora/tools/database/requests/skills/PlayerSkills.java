package fr.kizafox.andora.tools.database.requests.skills;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.database.requests.DBQuery;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.SQLException;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 17/09/2023
 * @project : Andora
 */
public class PlayerSkills extends Skills{

    protected final Andora instance;

    private final Player player;
    private final String uuid;

    private final String TABLE = "player_infos_skills";

    public PlayerSkills(final Andora instance, final Player player) {
        this.instance = instance;

        this.player = player;
        this.uuid = player.getUniqueId().toString();
    }

    public static PlayerSkills getPlayerSkills(final Player player){
        return Andora.get().getPlayerSkills().stream().filter(playerSkills -> playerSkills.player == player).findFirst().orElse(null);
    }

    @Override
    public void initialize() {
        this.instance.getPlayerSkills().add(this);

        new DBQuery(this.instance.getManagers().getDbHandler().pool().getDataSource()).query(((resultSet, throwables) -> {
            if(throwables != null){
                Andora.sendLog(ChatColor.RED + "An error occurred: " + throwables.getMessage());
                return;
            }

            try {
                if(!resultSet.next()){
                    new DBQuery(this.instance.getManagers().getDbHandler().pool().getDataSource()).update("INSERT INTO " + TABLE + " (uuid, name, skillPoint, damageBonus, healthBonus) VALUES ('" + this.uuid + "', '" + player.getName() + "', '0', '0.0', '0.0')");
                }
            }catch (final SQLException e){
                Andora.sendLog(ChatColor.RED + "An error occurred while processing the result set: " + e.getMessage());
            }
        }), "SELECT * FROM " + TABLE + " WHERE uuid=?", uuid);
    }
    @Override
    public void delete() {
        this.instance.getPlayerSkills().remove(this);
    }

    @Override
    public int getSkillPoint() {
        return (int) new DBQuery(this.instance.getManagers().getDbHandler().pool().getDataSource()).query((resultSet -> {
            try {
                if(resultSet.next()){
                    return resultSet.getInt("skillPoint");
                }
            } catch (SQLException e) {
                Andora.sendLog(ChatColor.RED + "An error occurred while processing the result set: " + e.getMessage());
            }
            return 0;
        }), "SELECT * FROM " + TABLE + " WHERE uuid='" + uuid + "'");
    }

    @Override
    public void setSkillPoint(int amount) {
        new DBQuery(this.instance.getManagers().getDbHandler().pool().getDataSource()).update("UPDATE " + TABLE + " SET skillPoint='" + amount + "' WHERE uuid='" + uuid + "'");
    }

    @Override
    public void addSkillPoint(int amount) {
        this.setSkillPoint(this.getSkillPoint() + amount);
    }

    @Override
    public void removeSkillPoint(int amount) {
        this.setSkillPoint(this.getSkillPoint() < amount ? 0 : this.getSkillPoint() - amount);
    }

    @Override
    public double getDamageBonus() {
        return (double) new DBQuery(this.instance.getManagers().getDbHandler().pool().getDataSource()).query((resultSet -> {
            try {
                if(resultSet.next()){
                    return resultSet.getDouble("damageBonus");
                }
            } catch (SQLException e) {
                Andora.sendLog(ChatColor.RED + "An error occurred while processing the result set: " + e.getMessage());
            }
            return 0.0;
        }), "SELECT * FROM " + TABLE + " WHERE uuid='" + uuid + "'");
    }

    @Override
    public void setDamageBonus(double amount) {
        new DBQuery(this.instance.getManagers().getDbHandler().pool.getDataSource()).update("UPDATE " + TABLE + " SET damageBonus='" + amount + "' WHERE uuid='" + uuid + "'");
    }

    @Override
    public void addDamageBonus(double amount) {
        this.setDamageBonus(this.getDamageBonus() + amount);
    }

    @Override
    public void removeDamageBonus(double amount) {
        this.setDamageBonus(this.getDamageBonus() < amount ? 0 : this.getDamageBonus() - amount);
    }

    @Override
    public double getHealthBonus() {
        return (double) new DBQuery(this.instance.getManagers().getDbHandler().pool().getDataSource()).query((resultSet -> {
            try {
                if(resultSet.next()){
                    return resultSet.getDouble("healthBonus");
                }
            } catch (SQLException e) {
                Andora.sendLog(ChatColor.RED + "An error occurred while processing the result set: " + e.getMessage());
            }
            return 0.0;
        }), "SELECT * FROM " + TABLE + " WHERE uuid='" + uuid + "'");
    }

    @Override
    public void setHealthBonus(double amount) {
        new DBQuery(this.instance.getManagers().getDbHandler().pool().getDataSource()).update("UPDATE " + TABLE + " SET healthBonus='" + amount + "' WHERE uuid='" + uuid + "'");
    }

    @Override
    public void addHealthBonus(double amount) {
        this.setHealthBonus(this.getHealthBonus() + amount);
    }

    @Override
    public void removeHealthBonus(double amount) {
        this.setHealthBonus(this.getHealthBonus() < amount ? 0 : this.getHealthBonus() - amount);
    }
}
