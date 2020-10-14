package me.maxitros.zombie.sql.runnables;

import me.maxitros.zombie.api.CallBack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryRunnable extends BukkitRunnable
{
    private final Connection connection;
    private final String statement;
    private final CallBack<ResultSet, SQLException> callback;
    public QueryRunnable(Connection connection, String statement, CallBack<ResultSet, SQLException> callback)
    {
        this.connection = connection;
        this.statement = statement;
        this.callback = callback;
    }
    @Override
    public void run()
    {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try
        {
            Connection connection = this.connection;
            preparedStatement = connection.prepareStatement(statement);
            resultSet = preparedStatement.executeQuery();
            callback.call(resultSet, null);
        }
        catch (SQLException e)
        {
            callback.call(null, e);
        }
        finally
        {
            if (resultSet != null)
            {
                try
                {
                    resultSet.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }

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
