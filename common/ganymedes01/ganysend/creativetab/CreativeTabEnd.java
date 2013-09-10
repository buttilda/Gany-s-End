package ganymedes01.ganysend.creativetab;

import ganymedes01.ganysend.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabEnd extends CreativeTabs {

	public CreativeTabEnd() {
		super(Reference.MOD_ID);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getTabIconItemIndex() {
		return Block.whiteStone.blockID;
	}
}
