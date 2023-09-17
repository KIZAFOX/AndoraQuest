package fr.kizafox.andora.tools.database.requests.user;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 11/05/2023
 * @project : FeatherPlugin
 */
public abstract class User {

    abstract public void initialize();

    abstract public void delete();

    abstract public double getMoney();

    abstract public void setMoney(final double money);

    abstract public void addMoney(final double money);

    abstract public void removeMoney(final double money);

}
