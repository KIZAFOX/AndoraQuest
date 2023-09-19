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

    abstract public int getSkillPoint();

    abstract public void setSkillPoint(final int amount);

    abstract public void addSkillPoint(final int amount);

    abstract public void removeSkillPoint(final int amount);

    abstract public double getDamageBonus();

    abstract public void setDamageBonus(final double amount);

    abstract public void addDamageBonus(final double amount);

    abstract public void removeDamageBonus(final double amount);

    abstract public double getHealthBonus();

    abstract public void setHealthBonus(final double amount);

    abstract public void addHealthBonus(final double amount);

    abstract public void removeHealthBonus(final double amount);
}
