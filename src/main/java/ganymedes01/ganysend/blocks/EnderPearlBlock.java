package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.ModBlocks.ISubBlocksBlock;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.items.blocks.ItemEnderPearlBlock;
import ganymedes01.ganysend.lib.Strings;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EnderPearlBlock extends Block implements ISubBlocksBlock, IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon[] blockIcon;

	public EnderPearlBlock() {
		super(Material.iron);
		setHardness(1.5F);
		setResistance(10.0F);
		setBlockName(Utils.getUnlocalisedName(Strings.ENDERPEARL_BLOCK_NAME));
		setCreativeTab(GanysEnd.enableDecorativeBlocks ? GanysEnd.endTab : null);
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return blockIcon[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = new IIcon[2];
		blockIcon[0] = reg.registerIcon(Utils.getBlockTexture(Strings.ENDERPEARL_BLOCK_NAME));
		blockIcon[1] = reg.registerIcon(Utils.getBlockTexture(Strings.ENDERPEARL_BRICK_NAME));
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemEnderPearlBlock.class;
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableDecorativeBlocks;
	}
}