package me.maxitros.zombie.sql.runnables;

import me.maxitros.zombie.api.CallBack;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateRunnable extends BukkitRunnable {
    private final Connection connection;
    private final String statement;
    private final CallBack<Integer, SQLException> callback;

    public UpdateRunnable(Connection connection, String statement, @Nullable CallBack<Integer, SQLException> callback)
    {
        this.connection = connection;
        this.statement = statement;
        this.callback = callback;
    }
    @Override
    public void run() {
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = this.connection;
            preparedStatement = connection.prepareStatement(statement);

            if (callback != null)
            {
                callback.call(preparedStatement.executeUpdate(), null);
            }
        } catch (SQLException e)
        {
            if (callback != null)
            {
                callback.call(null, e);
            }
        }
        finally
        {
            if (preparedStatement != null)
            {
                try
                {
                    preparedStatement.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }

            if (connection != null)
            {
                try
                {
                    connection.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}