package me.maxitros.zombie.commands;

import me.maxitros.zombie.ZombieTest;
import org.bukkit.Bukkit;

public class PluginCommands
{
    public static void Register(ZombieTest plugin)
    {
        plugin.getCommand("zombielist").setExecutor(new ShowNamesCommand());
    }
}
