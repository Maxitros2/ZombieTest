package me.maxitros.zombie.listeners;

import me.maxitros.zombie.ZombieTest;
import org.bukkit.Bukkit;

public class Listeners
{
    public static void Register(ZombieTest plugin)
    {
        Bukkit.getPluginManager().registerEvents(new BlockBreak(), plugin);
        Bukkit.getPluginManager().registerEvents(new MobDead(), plugin);
    }
}
