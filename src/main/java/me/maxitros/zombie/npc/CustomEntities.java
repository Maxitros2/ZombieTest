package me.maxitros.zombie.npc;

import com.mojang.datafixers.types.Type;
import me.maxitros.zombie.ZombieTest;
import me.maxitros.zombie.npc.Ocelot_zombie;
import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.DataConverterRegistry;
import net.minecraft.server.v1_13_R2.DataConverterTypes;
import net.minecraft.server.v1_13_R2.EntityTypes;
import net.minecraft.server.v1_13_R2.EntityZombie;
import net.minecraft.server.v1_13_R2.World;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.function.Function;

import static me.maxitros.zombie.api.ReflectionHelper.getPrivateField;

public class CustomEntities
{
    public void registerEntities()
    {
        CUSTOM_ZOMBIE=registerNewEntity("custom_zombie", "zombie", Ocelot_zombie.class, Ocelot_zombie::new);
    }
    public static EntityTypes CUSTOM_ZOMBIE;
    public Entity spawnEntity(EntityTypes entityTypes, Location loc, String name)
    {
        net.minecraft.server.v1_13_R2.Entity nmsEntity = entityTypes.a(
                ((CraftWorld) loc.getWorld()).getHandle(),
                null,
                null,
                null,
                new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()),
                true,
                false);

        Entity custom = nmsEntity.getBukkitEntity();
        custom.setCustomName(name);
        custom.setMetadata("IsOcelotZombie", new FixedMetadataValue(ZombieTest.getInstance(),1));
        return custom;
    }

    private EntityTypes registerNewEntity(String name, String extend_from, Class<? extends net.minecraft.server.v1_13_R2.Entity> clazz, Function<? super World, ? extends net.minecraft.server.v1_13_R2.Entity> function)
    {
        Map<Object, Type<?>> dataTypes = (Map<Object, Type<?>>) DataConverterRegistry.a().getSchema(15190).findChoiceType(DataConverterTypes.n).types();
        dataTypes.put("minecraft:" + name, dataTypes.get("minecraft:" + extend_from));
        return EntityTypes.a(name, EntityTypes.a.a(clazz, function));
    }
}
