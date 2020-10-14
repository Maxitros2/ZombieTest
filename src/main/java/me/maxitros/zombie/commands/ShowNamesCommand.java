package me.maxitros.zombie.commands;

import me.maxitros.zombie.ZombieTest;
import me.maxitros.zombie.api.CallBack;
import me.maxitros.zombie.sql.DataSource;
import me.maxitros.zombie.sql.runnables.QueryRunnable;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowNamesCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        if (!(commandSender instanceof Player))
            return false;
        Player p = (Player) commandSender;
        try
        {
            new QueryRunnable(DataSource.getConnection(), "SELECT Name FROM zombies ORDER BY id DESC LIMIT 10;", new CallBack<ResultSet, SQLException>() {
                @Override
                public void call(ResultSet resultSet, SQLException thrown)
                {
                    if (thrown == null)
                    {
                        try {
                            List<String> result = new ArrayList<String>();
                            while (resultSet.next())
                            {
                                result.add(resultSet.getString("Name"));
                            }
                            p.sendMessage(ChatColor.GOLD+String.join(", ",result));
                        }
                        catch (SQLException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        thrown.printStackTrace();
                    }
                }
            }).runTaskAsynchronously(ZombieTest.getInstance());
        }
        catch (Exception ex)
        {
            return false;
        }


    return true;
    }
}
