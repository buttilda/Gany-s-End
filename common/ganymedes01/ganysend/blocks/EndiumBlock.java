package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.items.ModItems;
import ganymedes01.ganysend.lib.Strings;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EndiumBlock extends Block {

	public EndiumBlock(int id) {
		super(id, Material.cloth);
		setHardness(0.2F);
		setResistance(0.5F);
		setCreativeTab(GanysEnd.endTab);
		setStepSound(soundMetalFootstep);
		setTextureName(Utils.getItemTexture(Strings.ENDIUM_BLOCK_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDIUM_BLOCK_NAME));
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new ItemStack(ModItems.endiumIngot, 9));
		return list;
	}
}
