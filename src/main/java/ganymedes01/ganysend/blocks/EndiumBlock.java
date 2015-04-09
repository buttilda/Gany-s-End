package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModItems;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EndiumBlock extends Block {

	public EndiumBlock() {
		super(Material.cloth);
		setHardness(0.2F);
		setResistance(0.5F);
		setStepSound(soundTypeMetal);
		setCreativeTab(GanysEnd.enableEndium ? GanysEnd.endTab : null);
		setBlockName(Utils.getUnlocalisedName(Strings.ENDIUM_BLOCK_NAME));
		setBlockTextureName(Utils.getItemTexture(Strings.ENDIUM_BLOCK_NAME));
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new ItemStack(ModItems.endiumIngot, 9));
		return list;
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
		return true;
	}
}