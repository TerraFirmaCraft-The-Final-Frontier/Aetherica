package io.tff.aether.client.audio.music;

import java.util.Random;

import io.tff.aether.registry.sounds.SoundsAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import io.tff.aether.AetherConfig;

@SideOnly(Side.CLIENT)
public class AetherMusicTicker implements ITickable
{
    private final Random rand = new Random();
    private final Minecraft mc;
    private ISound currentMusic, currentRecord, menuMusic, minecraftMusic;
    private int timeUntilNextMusic = 100;

    public AetherMusicTicker(Minecraft mcIn)
    {
        this.mc = mcIn;
    }

    public void update()
    {
       TrackType tracktype = this.getRandomTrack();

        if (this.mc.player != null)
        {
           	if (this.mc.player.dimension != AetherConfig.dimension.aether_dimension_id)
           	{
           		this.stopMusic();
           	}
           	else if (this.mc.player.dimension == AetherConfig.dimension.aether_dimension_id)
           	{
                if (this.currentMusic != null)
                {
                    if (!this.mc.getSoundHandler().isSoundPlaying(this.currentMusic))
                    {
                        this.currentMusic = null;
                        this.timeUntilNextMusic = Math.min(MathHelper.getInt(this.rand, tracktype.getMinDelay(), tracktype.getMaxDelay()), this.timeUntilNextMusic);
                    }
                }

                this.timeUntilNextMusic = Math.min(this.timeUntilNextMusic, tracktype.getMaxDelay());

                if (this.currentMusic == null && this.timeUntilNextMusic-- <= 0)
                {
                    this.playMusic(tracktype);
                }
           	}
        }

        if (!this.mc.getSoundHandler().isSoundPlaying(this.menuMusic))
        {
            this.menuMusic = null;
        }
    }

    public boolean playingMusic()
    {
    	return this.currentMusic != null;
    }

    public boolean playingRecord()
    {
        return this.currentRecord != null;
    }

    public boolean playingMenuMusic()
    {
        return this.menuMusic != null;
    }

    public boolean playingMinecraftMusic()
    {
        return this.minecraftMusic != null;
    }

    public ISound getRecord()
    {
        return this.currentRecord;
    }

    public AetherMusicTicker.TrackType getRandomTrack()
    {
    	int num = this.rand.nextInt(4);

    	return num == 0 ? TrackType.TRACK_ONE : num == 1 ? TrackType.TRACK_TWO : num == 2 ? TrackType.TRACK_THREE : TrackType.TRACK_FOUR;
    }

    public void playMusic(TrackType requestedMusicType)
    {
        this.currentMusic = PositionedSoundRecord.getMusicRecord(requestedMusicType.getMusicLocation());
        this.mc.getSoundHandler().playSound(this.currentMusic);
        this.timeUntilNextMusic = Integer.MAX_VALUE;
    }

    public void trackRecord(ISound record)
    {
        this.currentRecord = record;
    }

    public void trackMinecraftMusic(ISound record)
    {
        this.minecraftMusic = record;
    }

    public void playMenuMusic()
    {
        this.menuMusic = PositionedSoundRecord.getMusicRecord(TrackType.TRACK_MENU.getMusicLocation());
        this.mc.getSoundHandler().playSound(this.menuMusic);
    }

    public void stopMusic()
    {
        if (this.currentMusic != null)
        {
            this.mc.getSoundHandler().stopSound(this.currentMusic);
            this.currentMusic = null;
            this.timeUntilNextMusic = 0;
        }
    }

    public void stopMenuMusic()
    {
        if (this.menuMusic != null)
        {
            this.mc.getSoundHandler().stopSound(this.menuMusic);
            this.menuMusic = null;
        }
    }

    public void stopMinecraftMusic()
    {
        if (this.minecraftMusic != null)
        {
            this.mc.getSoundHandler().stopSound(this.minecraftMusic);
            this.minecraftMusic = null;
        }
    }

    @SideOnly(Side.CLIENT)
    public static enum TrackType
    {
    	TRACK_ONE(SoundsAether.aether1, 1200, 1500),
    	TRACK_TWO(SoundsAether.aether2, 1200, 1500),
    	TRACK_THREE(SoundsAether.aether3, 1200, 1500),
    	TRACK_FOUR(SoundsAether.aether4, 1200, 1500),
        TRACK_MENU(SoundsAether.aether_menu, 1200, 1500);

        private final SoundEvent musicLocation;
        private final int minDelay;
        private final int maxDelay;

        private TrackType(SoundEvent musicLocationIn, int minDelayIn, int maxDelayIn)
        {
            this.musicLocation = musicLocationIn;
            this.minDelay = minDelayIn;
            this.maxDelay = maxDelayIn;
        }

        public SoundEvent getMusicLocation()
        {
            return this.musicLocation;
        }

        public int getMinDelay()
        {
            return this.minDelay;
        }

        public int getMaxDelay()
        {
            return this.maxDelay;
        }
    }

}