package me.maxitros.zombie.listeners;

import me.maxitros.zombie.ZombieTest;
import me.maxitros.zombie.api.NameGenerator;
import me.maxitros.zombie.npc.CustomEntities;
import me.maxitros.zombie.npc.Ocelot_zombie;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.util.Vector;

public class BlockBreak implements Listener
{
    @EventHandler
    public void OnPlayerBreakeBlock(BlockBreakEvent e)
    {
        if(e.getBlock().getType().equals(Material.DIRT))
        {
            ZombieTest.getCustomEntities().spawnEntity(CustomEntities.CUSTOM_ZOMBIE,e.getBlock().getLocation().add(new Vector(0,1,0)), NameGenerator.generateRandomWord(10));
        }
    }

}
