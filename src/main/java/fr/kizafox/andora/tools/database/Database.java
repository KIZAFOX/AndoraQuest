package fr.kizafox.andora.tools.database;

import fr.kizafox.andora.tools.database.hikari.HikariPool;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 17/09/2023
 * @project : Andora
 */
public abstract class Database {

    abstract public String username();

    abstract public String password();

    abstract public String host();

    abstract public int port();

    abstract public String database();

    abstract public HikariPool pool();

}
