package me.maxitros.zombie.config;

import me.maxitros.zombie.ZombieTest;
import me.maxitros.zombie.sql.DataSource;
import org.bukkit.Bukkit;

import java.sql.SQLException;

public class PluginConfig
{
    ZombieTest plugin;
    public PluginConfig(ZombieTest plugin)
    {
        this.plugin=plugin;
    }
    public void Init()
    {
        plugin.getConfig().options().copyDefaults(true);
        plugin.getConfig().addDefault("database.driver-class", "org.mariadb.jdbc.Driver");
        plugin.getConfig().addDefault("database.jdbc-url", "jdbc:mariadb://%s:%s/%s");
        plugin.getConfig().addDefault("database.host", "localhost");
        plugin.getConfig().addDefault("database.port", "3306");
        plugin.getConfig().addDefault("database.database", "minecraft");
        plugin.getConfig().addDefault("database.user", "root");
        plugin.getConfig().addDefault("database.password", "rootroot");
        plugin.getConfig().addDefault("database.poolsize", 30);
        plugin.saveConfig();
        try
        {
            new DataSource(plugin.getConfig().getConfigurationSection("database"));
        }
        catch (SQLException e)
        {
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }
}
