package ganymedes01.ganysend.blocks;

import java.util.List;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.ModBlocks.ISubBlocksBlock;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.items.blocks.ItemEndWalls;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.BlockWall;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EndWalls extends BlockWall implements ISubBlocksBlock, IConfigurable {

	public EndWalls() {
		super(ModBlocks.endstoneBrick);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.END_WALLS_NAME));
		setCreativeTab(GanysEnd.enableDecorativeBlocks ? GanysEnd.endTab : null);
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

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableDecorativeBlocks;
	}
}