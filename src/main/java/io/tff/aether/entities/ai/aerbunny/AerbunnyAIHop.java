package io.tff.aether.entities.ai.aerbunny;

import io.tff.aether.entities.passive.mountable.EntityAerbunny;
import net.minecraft.entity.ai.EntityAIBase;

public class AerbunnyAIHop extends EntityAIBase
{

    private EntityAerbunny aerbunny;

    public AerbunnyAIHop(EntityAerbunny aerbunny)
    {
        this.aerbunny = aerbunny;
        this.setMutexBits(8);
    }

    public boolean shouldExecute()
    {
        return this.aerbunny.motionZ > 0.0D || this.aerbunny.motionX > 0.0D || this.aerbunny.onGround;
    }

    public void updateTask()
    {
    	if(aerbunny.moveForward != 0.0F)
    	{
    	this.aerbunny.getJumpHelper().setJumping();
    	}    }

}