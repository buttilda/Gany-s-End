package ganymedes01.ganysend.world;

import ganymedes01.ganysend.blocks.ModBlocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class EnderFlowerGenerator implements IWorldGenerator {

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.dimensionId == 1)
			for (int x = 0; x < 16; x++)
				for (int y = 40; y < 120; y++)
					for (int z = 0; z < 16; z++) {
						int blockX = chunkX * 16 + x;
						int blockY = y + 1;
						int blockZ = chunkZ * 16 + z;
						if (world.getBlockId(blockX, blockY - 1, blockZ) == Block.whiteStone.blockID)
							if (world.isAirBlock(blockX, blockY, blockZ))
								if (!isSurrounded(world, blockX, blockY, blockZ))
									if (rand.nextInt(60) == 30)
										world.setBlock(blockX, blockY, blockZ, ModBlocks.enderFlower.blockID);
					}
	}

	private boolean isSurrounded(World world, int x, int y, int z) {
		return !(world.isAirBlock(x + 1, y, z) && world.isAirBlock(x - 1, y, z) && world.isAirBlock(x, y, z + 1) && world.isAirBlock(x, y, z - 1));

	}
}
