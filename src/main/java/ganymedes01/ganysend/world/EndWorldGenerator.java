package ganymedes01.ganysend.world;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModBlocks;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;
import net.minecraftforge.event.terraingen.TerrainGen;
import cpw.mods.fml.common.IWorldGenerator;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EndWorldGenerator implements IWorldGenerator {

	private final WorldGenMinable endiumGenerator = new WorldGenMinable(ModBlocks.rawEndium, 0, 7, Blocks.end_stone);

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.dimensionId == 1) {
			if (GanysEnd.enableEnderFlower)
				for (int x = 0; x < 16; x++)
					for (int y = 40; y < 120; y++)
						for (int z = 0; z < 16; z++) {
							int blockX = chunkX * 16 + x;
							int blockY = y + 1;
							int blockZ = chunkZ * 16 + z;
							if (world.getBlock(blockX, blockY - 1, blockZ) == Blocks.end_stone)
								if (world.isAirBlock(blockX, blockY, blockZ))
									if (!isSurrounded(world, blockX, blockY, blockZ))
										if (rand.nextInt(60) == 30)
											world.setBlock(blockX, blockY, blockZ, ModBlocks.enderFlower);
						}

			if (GanysEnd.enableEndiumGen)
				for (int i = 0; i < 15; i++)
					if (TerrainGen.generateOre(world, rand, endiumGenerator, chunkX, chunkZ, GenerateMinable.EventType.CUSTOM))
						endiumGenerator.generate(world, rand, chunkX * 16 + rand.nextInt(16), 20 + rand.nextInt(100), chunkZ * 16 + rand.nextInt(16));
		}
	}

	private boolean isSurrounded(World world, int x, int y, int z) {
		return !(world.isAirBlock(x + 1, y, z) && world.isAirBlock(x - 1, y, z) && world.isAirBlock(x, y, z + 1) && world.isAirBlock(x, y, z - 1));
	}
}