package fr.kizafox.andora.tools.database.requests;

import fr.kizafox.andora.Andora;
import org.bukkit.Bukkit;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 10/05/2023
 * @project : FeatherPlugin
 */
public class DBQuery {

    private final DataSource source;
    private final ExecutorService executorService;

    private final List<Connection> connections;
    private final List<PreparedStatement> statements;

    protected final Andora instance = Andora.get();

    public DBQuery(final DataSource source){
        this.source = source;
        this.executorService = Executors.newFixedThreadPool(10);

        this.connections = new ArrayList<>();
        this.statements = new ArrayList<>();
    }

    public void initializeDB(){
        //player_infos
        this.update("CREATE TABLE IF NOT EXISTS player_infos (" +
                "`#` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "uuid VARCHAR(255), " +
                "name VARCHAR(255), " +
                "class VARCHAR(255), " +
                "money DOUBLE)");
        //player_infos_skills
        this.update("CREATE TABLE IF NOT EXISTS player_infos_skills (" +
                "`#` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "uuid VARCHAR(255), " +
                "name VARCHAR(255), " +
                "skillPoint BIGINT, " +
                "damageBonus DOUBLE, " +
                "healthBonus DOUBLE)");
    }

    public void update(final String query){
        this.instance.getServer().getScheduler().runTaskAsynchronously(this.instance, () -> {
            try (Connection connection = this.source.getConnection()){
                final PreparedStatement statement = connection.prepareStatement(query);
                statement.executeUpdate();
            }catch(SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void query(final BiConsumer<ResultSet, SQLException> resultConsumer, final String query, final String... params){
        this.instance.getServer().getScheduler().runTaskAsynchronously(this.instance, () -> this.executorService.submit(() -> {
            try (final Connection connection = this.source.getConnection();
                 final PreparedStatement statement = connection.prepareStatement(query)){

                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }

                try (ResultSet resultSet = statement.executeQuery()) {
                    resultConsumer.accept(resultSet, null);
                }

                this.connections.add(connection);
                this.statements.add(statement);
            }catch (final SQLException e){
                resultConsumer.accept(null, e);
            }finally {
                if(this.connections.get(0) != null){
                    try {
                        this.connections.get(0).close();
                        this.statements.get(0).close();
                        this.connections.remove(0);
                        this.statements.remove(0);

                        executorService.shutdown();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }));
    }

    public Object query(Function<ResultSet, Object> consumer, String query){
        try (final Connection connection = this.source.getConnection();
             final PreparedStatement statement = connection.prepareStatement(query);
             final ResultSet resultSet = statement.executeQuery()){

            final Future<Object> resultFuture = executorService.submit(() -> consumer.apply(resultSet));

            return resultFuture.get();
        }catch (final SQLException | InterruptedException | ExecutionException e){
            throw new IllegalStateException(e.getMessage());
        }finally {
            executorService.shutdown();
        }
    }
}
