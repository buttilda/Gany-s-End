package ganymedes01.ganysend.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.RenderIDs;
import ganymedes01.ganysend.lib.Strings;
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

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EnderFlower extends BlockFlower implements IConfigurable {

	public EnderFlower() {
		super(0);
		setLightLevel(0.3F);
		setStepSound(soundTypeGrass);
		setBlockName(Utils.getUnlocalisedName(Strings.ENDER_FLOWER_NAME));
		setCreativeTab(GanysEnd.enableEnderFlower ? GanysEnd.endTab : null);
		setBlockTextureName(Utils.getBlockTexture(Strings.ENDER_FLOWER_NAME));
	}

	@Override
	public int getRenderType() {
		return RenderIDs.ENDER_FLOWER;
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

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableEnderFlower;
	}
}