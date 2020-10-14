package me.maxitros.zombie.listeners;

import me.maxitros.zombie.ZombieTest;
import me.maxitros.zombie.api.CallBack;
import me.maxitros.zombie.sql.DataSource;
import me.maxitros.zombie.sql.runnables.UpdateRunnable;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;

public class MobDead implements Listener
{
    @EventHandler
    public void ZombieDead(EntityDeathEvent e)
    {
        if(!e.getEntity().hasMetadata("IsOcelotZombie"))
            return;
        LivingEntity zombie = e.getEntity();
        if(!(zombie.getKiller() instanceof Player))
            return;
        String killer = ((Player)zombie.getKiller()).getDisplayName();
        String name = zombie.getCustomName();
        e.getDrops().clear();
        Entity entity = zombie.getWorld().dropItem(zombie.getLocation(),new ItemStack(Material.ROTTEN_FLESH));
        entity.setCustomName("%name%'s flesh");
        entity.setCustomNameVisible(true);
        try {
            new UpdateRunnable(DataSource.getConnection(),
                    String.format("INSERT INTO zombies (Name, Killer) VALUES ('%s','%s')", name,killer),
                    new CallBack<Integer, SQLException>() {
                            @Override
                            public void call(Integer rows, SQLException thrown) {
                                if (thrown == null) {
                                    System.out.println("Successfully added " + name);
                                } else {
                                    thrown.printStackTrace();
                                }
                            }
            }).runTaskAsynchronously(ZombieTest.getInstance());
        }
        catch (SQLException ex)
        {
            System.out.println(ex);
        }

    }
}
