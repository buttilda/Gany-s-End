package ganymedes01.ganysend.blocks;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class EnderFlower extends BlockFlower {

	protected EnderFlower() {
		super(0);
		setLightLevel(0.3F);
		setCreativeTab(GanysEnd.endTab);
		setBlockName(Utils.getUnlocalizedName(Strings.ENDER_FLOWER_NAME));
		setBlockTextureName(Utils.getBlockTexture(Strings.ENDER_FLOWER_NAME));
	}

	@Override
	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
		return !(entity instanceof EntityDragon);
	}

	@Override
	protected boolean canPlaceBlockOn(Block soil) {
		return soil == Blocks.end_stone;
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return world.provider.dimensionId == 1 && canPlaceBlockOn(world.getBlock(x, y - 1, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(Utils.getBlockTexture(Strings.ENDER_FLOWER_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
	}
}