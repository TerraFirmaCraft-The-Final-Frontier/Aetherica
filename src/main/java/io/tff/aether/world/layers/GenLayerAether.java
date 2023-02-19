package io.tff.aether.world.layers;

import net.minecraft.world.gen.layer.*;

public abstract class GenLayerAether extends GenLayer
{
    public GenLayerAether(long seed)
    {
        super(seed);
    }

    public static GenLayer[] initializeAllBiomeGenerators(long seed)
    {
        GenLayer biomes = new GenLayerAetherBiomes(1L);

        GenLayer noise = GenLayerZoom.magnify(1L, biomes, 4);
        noise = new GenLayerSmooth(2000L, noise);
        noise = new GenLayerVoronoiZoom(2002L, noise);
        noise = new GenLayerSmooth(2003L, noise);

        biomes.initWorldGenSeed(seed);
        noise.initWorldGenSeed(seed);

        return new GenLayer[]{biomes, noise, biomes};
    }
}
