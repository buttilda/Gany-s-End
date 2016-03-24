package ganymedes01.ganysend.blocks;

import java.util.Random;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityAnchoredEnderChest;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class AnchoredEnderChest extends InventoryBinder {

	public AnchoredEnderChest() {
		super();
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ANCHORED_ENDER_CHEST_NAME));
		setCreativeTab(GanysEnd.enableAnchoredEnderChest ? GanysEnd.endTab : null);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote && !world.getBlock(x, y + 1, z).isNormalCube()) {
			TileEntityAnchoredEnderChest tile = Utils.getTileEntity(world, x, y, z, TileEntityAnchoredEnderChest.class);
			if (tile != null && tile.getPlayerInventory() != null)
				player.displayGUIChest(tile);
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityAnchoredEnderChest();
	}

	@Override
	public int getRenderType() {
		return RenderIDs.ANCHORED_ENDER_CHEST;
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return Item.getItemFromBlock(Blocks.obsidian);
	}

	@Override
	public int quantityDropped(Random rand) {
		return 8;
	}

	@Override
	protected boolean canSilkHarvest() {
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, player, stack);
		Blocks.ender_chest.onBlockPlacedBy(world, x, y, z, player, stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		Blocks.ender_chest.randomDisplayTick(world, x, y, z, rand);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return Blocks.ender_chest.getIcon(side, meta);
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableAnchoredEnderChest;
	}
}