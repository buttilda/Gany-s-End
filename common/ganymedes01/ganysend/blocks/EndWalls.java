package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.lib.ModIDs;

import java.util.List;

import net.minecraft.block.BlockWall;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EndWalls extends BlockWall {

	public EndWalls() {
		super(ModIDs.END_WALLS_ID, ModBlocks.endstoneBrick);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		switch (meta) {
			case 0:
				return ModBlocks.endstoneBrick.getBlockTextureFromSide(0);
			default:
				return ModBlocks.enderpearlBlock.getIcon(0, 1);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs tab, List list) {
		list.add(new ItemStack(id, 1, 0));
		list.add(new ItemStack(id, 1, 1));
	}
}
