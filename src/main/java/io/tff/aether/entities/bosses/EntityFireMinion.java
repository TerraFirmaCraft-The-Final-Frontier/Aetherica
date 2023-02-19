package io.tff.aether.entities.bosses;

import javax.annotation.Nullable;

import io.tff.aether.registry.AetherLootTables;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityFireMinion extends EntityMob
{

    public EntityFireMinion(World world)
    {
        super(world);
        this.setSize(0.8F, 1.95F);
        this.isImmuneToFire = true;
    }

	@Override
    protected void initEntityAI()
    {
        this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.5D, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(25.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        for(int i = 0; i < 1; i++)
        {
            double d = rand.nextFloat() - 0.5F;
            double d1 = rand.nextFloat();
            double d2 = rand.nextFloat() - 0.5F;
            double d3 = posX + d * d1;
            double d4 = (this.getEntityBoundingBox().minY + d1) + 0.5D;
            double d5 = posZ + d2 * d1;

            if (this.hasCustomName() && "JorgeQ".equals(this.getCustomNameTag()))
            {
            	 this.world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, d3, d4, d5, 0.0D, -0.075000002980232239D, 0.0D);
            }
            else
            {
            	this.world.spawnParticle(EnumParticleTypes.FLAME, d3, d4, d5, 0.0D, -0.075000002980232239D, 0.0D);
            }
        }
    }
    
    @Override
    public float getEyeHeight()
    {
        return 2.0F;
    }
    
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return AetherLootTables.fire_minion;
    }

}