package io.tff.aether.world;

import io.tff.aether.networking.AetherNetworkingManager;
import io.tff.aether.networking.packets.PacketSendEternalDay;
import io.tff.aether.networking.packets.PacketSendShouldCycle;
import io.tff.aether.networking.packets.PacketSendTime;
import io.tff.aether.AetherConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;

public class AetherWorldProvider extends WorldProvider
{
	private float[] colorsSunriseSunset = new float[4];

	private EternalDayManager eternalDayManager;

	private boolean eternalDay;
	private boolean shouldCycleCatchup;
	private long aetherTime = 6000;

	public AetherWorldProvider()
	{
		super();
	}

	@Override
	protected void init()
	{
		this.hasSkyLight = true;
		this.biomeProvider = new AetherBiomeProvider(this.world.getSeed());

		NBTTagCompound nbttagcompound = this.world.getWorldInfo().getDimensionData(AetherConfig.dimension.aether_dimension_id);
		this.eternalDayManager = this.world instanceof WorldServer ? new EternalDayManager((WorldServer) this.world, nbttagcompound.getCompoundTag("EternalDay")) : null;
	}

	@Override
	public float[] calcSunriseSunsetColors(float f, float f1)
	{
		float f2 = 0.4F;
		float f3 = MathHelper.cos(f * 3.141593F * 2.0F) - 0.0F;
		float f4 = -0F;

		if (f3 >= f4 - f2 && f3 <= f4 + f2)
		{
			float f5 = (f3 - f4) / f2 * 0.5F + 0.5F;
			float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * 3.141593F)) * 0.99F;
			f6 *= f6;
			this.colorsSunriseSunset[0] = f5 * 0.3F + 0.1F;
			this.colorsSunriseSunset[1] = f5 * f5 * 0.7F + 0.2F;
			this.colorsSunriseSunset[2] = f5 * f5 * 0.7F + 0.2F;
			this.colorsSunriseSunset[3] = f6;
			return this.colorsSunriseSunset;
		}
		else
		{
			return null;
		}
	}

	@Override
	public boolean canCoordinateBeSpawn(int i, int j)
	{
		return false;
	}

	@Override
	public boolean canRespawnHere()
	{
		return false;
	}

	@Override
	public int getRespawnDimension(net.minecraft.entity.player.EntityPlayerMP player)
	{
		try
		{
			if (player.dimension == AetherConfig.dimension.aether_dimension_id && EntityPlayer.getBedSpawnLocation(player.world, player.getBedLocation(AetherConfig.dimension.aether_dimension_id), false) != null)
			{
				return AetherConfig.dimension.aether_dimension_id;
			}
		}
		catch (NullPointerException ignore) { }
		return 0;
	}

	@Override
	public WorldSleepResult canSleepAt(net.minecraft.entity.player.EntityPlayer player, BlockPos pos)
	{
		return WorldSleepResult.ALLOW;
	}

	@Override
	public IChunkGenerator createChunkGenerator()
	{
		return new ChunkProviderAether(this.world, this.world.getSeed());
	}

    public boolean canDoLightning(Chunk chunk)
    {
        return false;
    }

    public boolean canDoRainSnowIce(Chunk chunk)
    {
        return false;
    }

	@Override
	public Vec3d getFogColor(float f, float f1)
	{
		int i = 0x9393BC;

		float f2 = MathHelper.cos(f * 3.141593F * 2.0F) * 2.0F + 0.5F;
		if (f2 < 0.0F)
		{
			f2 = 0.0F;
		}
		if (f2 > 1.0F)
		{
			f2 = 1.0F;
		}
		float f3 = (i >> 16 & 0xff) / 255F;
		float f4 = (i >> 8 & 0xff) / 255F;
		float f5 = (i & 0xff) / 255F;
		f3 *= f2 * 0.94F + 0.06F;
		f4 *= f2 * 0.94F + 0.06F;
		f5 *= f2 * 0.91F + 0.09F;

		return new Vec3d(f3, f4, f5);
	}

	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks)
	{
		if (!AetherConfig.gameplay_changes.disable_eternal_day)
		{
			if (this.eternalDayManager != null)
			{
				if (!this.eternalDayManager.isEternalDay())
				{
					if (this.eternalDayManager.shouldCycleCatchup())
					{
						if (this.eternalDayManager.getTime() != (worldTime % 24000L) && this.eternalDayManager.getTime() != (worldTime + 1 % 24000L) && this.eternalDayManager.getTime() != (worldTime - 1 % 24000L))
						{
							this.eternalDayManager.setTime(Math.floorMod(this.eternalDayManager.getTime() - 1, 24000L));
						}
						else
						{
							this.eternalDayManager.setShouldCycleCatchup(false);
						}
					}
					else
					{
						this.eternalDayManager.setTime(worldTime);
					}

					this.aetherTime = this.eternalDayManager.getTime();
					AetherNetworkingManager.sendToAll(new PacketSendTime(this.aetherTime));
					this.eternalDayManager.setTime(this.aetherTime);
				}
			}
		}

		int i = (int)((AetherConfig.gameplay_changes.disable_eternal_day ? worldTime : this.aetherTime) % 24000L);

		float f = ((float)i + partialTicks) / 24000.0F - 0.25F;

		if (f < 0.0F)
		{
			++f;
		}

		if (f > 1.0F)
		{
			--f;
		}

		float f1 = 1.0F - (float)((Math.cos((double)f * Math.PI) + 1.0D) / 2.0D);
		f = f + (f1 - f) / 3.0F;
		return f;
	}

	public void setIsEternalDay(boolean set)
	{
		this.eternalDay = set;
	}

	public boolean getIsEternalDay()
	{
		return this.eternalDay;
	}

	public void setShouldCycleCatchup(boolean set)
	{
		this.shouldCycleCatchup = set;
	}

	public boolean getShouldCycleCatchup()
	{
		return this.shouldCycleCatchup;
	}

	public void setAetherTime(long time)
	{
		this.aetherTime = time;
	}

	public long getAetherTime()
	{
		return this.aetherTime;
	}

	@Override
	public String getSaveFolder()
	{
		return "Dim-Aether";
	}

	@Override
	public double getVoidFogYFactor()
	{
		return 100;
	}

	@Override
	public boolean doesXZShowFog(int x, int z)
	{
		return false;
	}

	@Override
	public boolean isSkyColored()
	{
		return false;
	}

	@Override
	public double getHorizon()
	{
		return 0.0;
	}

    @Override
    public float getCloudHeight()
    {
        return -5F;
    }

	@Override
	public DimensionType getDimensionType() 
	{
		return AetherWorld.aether_dimension_type;
	}

	@Override
	public void onWorldSave()
	{
		NBTTagCompound nbttagcompound = new NBTTagCompound();

		if (this.eternalDayManager != null)
		{
			nbttagcompound.setTag("EternalDay", this.eternalDayManager.getCompound());
		}

		this.world.getWorldInfo().setDimensionData(AetherConfig.dimension.aether_dimension_id, nbttagcompound);
	}

	@Override
	public void onWorldUpdateEntities()
	{
		if (this.eternalDayManager != null)
		{
			this.eternalDay = this.eternalDayManager.isEternalDay();
			AetherNetworkingManager.sendToAll(new PacketSendEternalDay(this.eternalDay));

			this.shouldCycleCatchup = this.eternalDayManager.shouldCycleCatchup();
			AetherNetworkingManager.sendToAll(new PacketSendShouldCycle(this.shouldCycleCatchup));
		}
	}

	public EternalDayManager getEternalDayManager()
	{
		return this.eternalDayManager;
	}
}