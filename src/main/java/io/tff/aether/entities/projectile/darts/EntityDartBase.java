package io.tff.aether.entities.projectile.darts;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public abstract class EntityDartBase extends EntityArrow implements IProjectile
{
    private int ticksInAir;

    private boolean hasNoGravity;

    public EntityDartBase(World worldIn)
    {
        super(worldIn);
    }

	public EntityDartBase(World worldIn, EntityLivingBase shooter)
	{
		super(worldIn, shooter);
	}

    public void onUpdate()
    {
        super.onUpdate();

        if (this.ticksInAir == 500)
        {
        	this.setDead();
        }

        if (!this.onGround)
        {
        	++this.ticksInAir;
        }
    }

    @Override
    public boolean hasNoGravity()
    {
        return this.hasNoGravity;
    }

    @Override
    public void setNoGravity(boolean flight)
    {
        this.hasNoGravity = flight;
    }
}