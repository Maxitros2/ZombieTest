package me.maxitros.zombie.npc;

import net.minecraft.server.v1_13_R2.*;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Predicate;

import static me.maxitros.zombie.api.ReflectionHelper.getPrivateField;

public class Ocelot_zombie extends EntityZombie
{

    public Ocelot_zombie(World world)
    {
        super(world);
        LinkedHashSet goalB = (LinkedHashSet)getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
        LinkedHashSet goalC = (LinkedHashSet)getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
        LinkedHashSet targetB = (LinkedHashSet)getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
        LinkedHashSet targetC = (LinkedHashSet)getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        if (this.world.spigotConfig.zombieAggressiveTowardsVillager)
        {
            this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityVillager.class, false));
        }
        this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityIronGolem.class, true));
        this.targetSelector.a(5, new PathfinderGoalNearestAttackableTarget(this, EntityTurtle.class, 10, true, false, EntityTurtle.bC));
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(7, new PathfinderGoalLeapAtTarget(this, 0.3F));
        this.goalSelector.a(8, new PathfinderGoalOcelotAttack(this));
        this.goalSelector.a(10, new PathfinderGoalRandomStrollLand(this, 0.8D, 1.0000001E-5F));
        this.goalSelector.a(11, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 10.0F));
        this.goalSelector.a(4, new PathfinderGoalAvoidTarget(this, EntityHuman.class, 16.0F, 0.8D, 1.33D));

    }
    @Override
    public void mobTick()
    {
        super.mobTick();
        if (this.getControllerMove().b())
        {
            double d0 = this.getControllerMove().c();
            if (d0 == 0.6D)
            {
                this.setSneaking(true);
                this.setSprinting(false);
            }
            else if (d0 == 1.33D)
            {
                this.setSneaking(false);
                this.setSprinting(true);
            }
            else
            {
                this.setSneaking(false);
                this.setSprinting(false);
            }
        }
        else
        {
            this.setSneaking(false);
            this.setSprinting(false);
        }

    }
}
