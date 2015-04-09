package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.ModBlocks.ISubBlocksBlock;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.items.blocks.ItemEndWalls;
import ganymedes01.ganysend.lib.Strings;

import java.util.List;

import net.minecraft.block.BlockWall;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EndWalls extends BlockWall implements ISubBlocksBlock {

	public EndWalls() {
		super(ModBlocks.endstoneBrick);
		setBlockName(Utils.getUnlocalisedName(Strings.END_WALLS_NAME));
		setCreativeTab(GanysEnd.enableDecorativeBlocks ? GanysEnd.endTab : null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		switch (meta) {
			case 0:
				return ModBlocks.endstoneBrick.getBlockTextureFromSide(0);
			default:
				return ModBlocks.enderpearlBlock.getIcon(0, 1);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubBlocks(Item id, CreativeTabs tab, List list) {
		list.add(new ItemStack(id, 1, 0));
		list.add(new ItemStack(id, 1, 1));
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemEndWalls.class;
	}
}