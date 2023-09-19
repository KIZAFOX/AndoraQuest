package fr.kizafox.andora.tools.database.requests.user;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.utils.Maths;
import fr.kizafox.andora.tools.database.requests.classes.ClassUnit;
import fr.kizafox.andora.tools.database.requests.DBQuery;
import fr.kizafox.andora.tools.database.requests.skills.PlayerSkills;
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
public class UserAccount extends User{

    protected final Andora instance;

    private final Player player;
    private final String uuid;

    private final PlayerSkills playerSkills;

    private final String TABLE = "player_infos";

    public UserAccount(final Andora instance, final Player player) {
        this.instance = instance;

        this.player = player;
        this.uuid = player.getUniqueId().toString();

        this.playerSkills = new PlayerSkills(this.instance, this.player);
    }

    public static UserAccount getUserAccount(final Player player){
        return Andora.get().getUserAccounts().stream().filter(userAccount -> userAccount.player == player).findFirst().orElse(null);
    }

    @Override
    public void initialize() {
        this.instance.getUserAccounts().add(this);

        new DBQuery(this.instance.getManagers().getDbHandler().pool().getDataSource()).query(((resultSet, throwables) -> {
            if(throwables != null){
                Andora.sendLog(ChatColor.RED + "An error occurred: " + throwables.getMessage());
                return;
            }

            try {
                if(!resultSet.next()){
                    new DBQuery(this.instance.getManagers().getDbHandler().pool().getDataSource()).update("INSERT INTO " + TABLE + " (uuid, name, class, money) VALUES ('" + this.uuid + "', '" + player.getName() + "', '" + ClassUnit.PEASANT.getName() + "', '0.0')");
                }
            }catch (final SQLException e){
                Andora.sendLog(ChatColor.RED + "An error occurred while processing the result set: " + e.getMessage());
            }
        }), "SELECT * FROM " + TABLE + " WHERE uuid=?", uuid);
        this.playerSkills.initialize();
    }

    @Override
    public void delete() {
        this.instance.getUserAccounts().remove(this);
        PlayerSkills.getPlayerSkills(player).delete();
    }

    @Override
    public ClassUnit getClassUnit() {
        return (ClassUnit) new DBQuery(this.instance.getManagers().getDbHandler().pool().getDataSource()).query((resultSet -> {
            try {
                if(resultSet.next()){
                    return ClassUnit.getByName(resultSet.getString("class"));
                }
            } catch (SQLException e) {
                Andora.sendLog(ChatColor.RED + "An error occurred while processing the result set: " + e.getMessage());
            }
            return ClassUnit.PEASANT;
        }), "SELECT * FROM " + TABLE + " WHERE uuid='" + uuid + "'");
    }

    @Override
    public void setClassUnit(ClassUnit classUnit) {
        new DBQuery(this.instance.getManagers().getDbHandler().pool().getDataSource()).update("UPDATE " + TABLE + " SET class='" + classUnit.getName() + "' WHERE uuid='" + uuid + "'");
    }

    @Override
    public double getMoney() {
        return (double) new DBQuery(this.instance.getManagers().getDbHandler().pool().getDataSource()).query((resultSet -> {
            try {
                if(resultSet.next()){
                    return resultSet.getDouble("money");
                }
            } catch (SQLException e) {
                Andora.sendLog(ChatColor.RED + "An error occurred while processing the result set: " + e.getMessage());
            }
            return 0.0;
        }), "SELECT * FROM " + TABLE + " WHERE uuid='" + uuid + "'");
    }

    @Override
    public void setMoney(double amount) {
        new DBQuery(this.instance.getManagers().getDbHandler().pool().getDataSource()).update("UPDATE " + TABLE + " SET money='" + Maths.around(amount) + "' WHERE uuid='" + uuid + "'");
    }

    @Override
    public void addMoney(double amount) {
        this.setMoney(this.getMoney() + amount);
    }

    @Override
    public void removeMoney(double amount) {
        this.setMoney(this.getMoney() < amount ? 0 : this.getMoney() - amount);
    }

    @Override
    public PlayerSkills getPlayerSkills() {
        return this.playerSkills;
    }
}
