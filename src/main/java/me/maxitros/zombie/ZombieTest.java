package me.maxitros.zombie;

import me.maxitros.zombie.commands.PluginCommands;
import me.maxitros.zombie.config.PluginConfig;
import me.maxitros.zombie.listeners.Listeners;
import me.maxitros.zombie.listeners.packets.PacketRegister;
import me.maxitros.zombie.npc.CustomEntities;
import org.bukkit.plugin.java.JavaPlugin;

public class ZombieTest extends JavaPlugin
{
    static ZombieTest plugin;
    static CustomEntities customMobs;
    PacketRegister packetRegister;
    public void onEnable()
    {
        plugin=this;
        new PluginConfig(plugin).Init();
        Listeners.Register(plugin);
        customMobs = new CustomEntities();
        customMobs.registerEntities();
        packetRegister = new PacketRegister();
        packetRegister.enable(this);
        PluginCommands.Register(this);
    }
    public void onDisable()
    {
        packetRegister.disable();
    }
    public static ZombieTest getInstance()
    {
        return plugin;
    }
    public static CustomEntities getCustomEntities()
    {
        return customMobs;
    }
}
