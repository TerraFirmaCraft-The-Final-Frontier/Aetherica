package io.tff.aether.entities.ai.swet;

import io.tff.aether.entities.passive.mountable.EntitySwet;
import net.minecraft.entity.ai.EntityAIBase;

public class SwetAIHop extends EntityAIBase
{
    private final EntitySwet swet;

    public SwetAIHop(EntitySwet swetEntity)
    {
        this.swet = swetEntity;
        this.setMutexBits(5);
    }

    @Override
    public boolean shouldExecute()
    {
        return true;
    }

    @Override
    public void updateTask()
    {
        ((SwetMoveHelper)this.swet.getMoveHelper()).setSpeed(1.0D);
    }
}
