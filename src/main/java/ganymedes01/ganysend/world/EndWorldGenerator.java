package ganymedes01.ganysend.world;

import java.util.Random;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModBlocks;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.IWorldGenerator;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EndWorldGenerator implements IWorldGenerator {

	private final WorldGenMinable endiumGenerator = new WorldGenMinable(ModBlocks.raw_endium.getDefaultState(), 7, BlockHelper.forBlock(Blocks.end_stone));

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.getDimensionId() != 1)
			return;

		if (GanysEnd.enableEnderFlower)
			for (int x = 0; x < 16; x++)
				for (int y = 40; y < 120; y++)
					for (int z = 0; z < 16; z++) {
						int blockX = chunkX * 16 + x;
						int blockY = y + 1;
						int blockZ = chunkZ * 16 + z;
						BlockPos pos = new BlockPos(blockX, blockY, blockZ);
						if (rand.nextInt(60) == 30)
							if (world.getBlockState(pos.down()).getBlock() == Blocks.end_stone)
								if (world.isAirBlock(pos) && !isSurrounded(world, pos))
									world.setBlockState(pos, ModBlocks.ender_flower.getDefaultState());
					}

		if (GanysEnd.enableEndiumGen)
			for (int i = 0; i < 15; i++)
				if (TerrainGen.generateOre(world, rand, endiumGenerator, new BlockPos(chunkX, 0, chunkZ), GenerateMinable.EventType.CUSTOM))
					endiumGenerator.generate(world, rand, new BlockPos(chunkX * 16 + rand.nextInt(16), 20 + rand.nextInt(100), chunkZ * 16 + rand.nextInt(16)));
	}

	private boolean isSurrounded(World world, BlockPos pos) {
		return !(world.isAirBlock(pos.offset(EnumFacing.EAST)) && world.isAirBlock(pos.offset(EnumFacing.WEST)) && world.isAirBlock(pos.offset(EnumFacing.NORTH)) && world.isAirBlock(pos.offset(EnumFacing.SOUTH)));
	}
}