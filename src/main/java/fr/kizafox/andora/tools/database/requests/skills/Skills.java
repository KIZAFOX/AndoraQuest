package fr.kizafox.andora.tools.database.requests.skills;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 17/09/2023
 * @project : Andora
 */
public abstract class Skills {

    abstract public void initialize();

    abstract public void delete();

    abstract public double getHealthBonus();

    abstract public void setHealthBonus(final double amount);

    abstract public void addHealthBonus(final double amount);

    abstract public void removeHealthBonus(final double amount);

    abstract public double getDamageBonus();

    abstract public void setDamageBonus(final double amount);

    abstract public void addDamageBonus(final double amount);

    abstract public void removeDamageBonus(final double amount);

    abstract public double getMovementSpeedBonus();

    abstract public void setMovementSpeedBonus(final double amount);

    abstract public void addMovementSpeedBonus(final double amount);

    abstract public void removeMovementSpeedBonus(final double amount);
}
