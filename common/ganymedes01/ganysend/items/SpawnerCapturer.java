package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.Strings;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class SpawnerCapturer extends Item {

	public SpawnerCapturer() {
		super(ModIDs.SPAWNER_CAPTURER_ID);
		setMaxStackSize(1);
		setCreativeTab(GanysEnd.endTab);
		setTextureName(Utils.getItemTexture(Strings.SPAWNER_CAPTURER_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.SPAWNER_CAPTURER_NAME));
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if (stack.hasTagCompound())
			if (stack.getTagCompound().hasKey("mobID"))
				list.add(Utils.CHAT_COLOUR_RED + stack.getTagCompound().getString("mobID"));
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		if (stack.getTagCompound().hasKey("mobID")) {
			if (side == 0)
				y--;
			if (side == 1)
				y++;
			if (side == 2)
				z--;
			if (side == 3)
				z++;
			if (side == 4)
				x--;
			if (side == 5)
				x++;
			if (world.isAirBlock(x, y, z)) {
				world.setBlock(x, y, z, Block.mobSpawner.blockID);
				TileEntity tile = world.getBlockTileEntity(x, y, z);
				if (tile instanceof TileEntityMobSpawner) {
					TileEntityMobSpawner spawner = (TileEntityMobSpawner) tile;
					spawner.getSpawnerLogic().setMobID(stack.getTagCompound().getString("mobID"));
					stack.stackSize--;
				}
			}
		}
		return true;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
		World world = player.worldObj;
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		else if (stack.getTagCompound().hasKey("mobID"))
			return super.onBlockStartBreak(stack, x, y, z, player);

		if (world.getBlockId(x, y, z) == Block.mobSpawner.blockID) {
			TileEntity tile = world.getBlockTileEntity(x, y, z);
			if (tile instanceof TileEntityMobSpawner) {
				TileEntityMobSpawner spawner = (TileEntityMobSpawner) tile;
				stack.getTagCompound().setString("mobID", spawner.getSpawnerLogic().getEntityNameToSpawn());
			}
		}

		return super.onBlockStartBreak(stack, x, y, z, player);
	}

	@Override
	public boolean canHarvestBlock(Block block) {
		return block == Block.mobSpawner;
	}

	@Override
	public float getStrVsBlock(ItemStack stack, Block block) {
		return stack.hasTagCompound() && stack.getTagCompound().hasKey("mobID") ? super.getStrVsBlock(stack, block) : block == Block.mobSpawner ? 25.0F : super.getStrVsBlock(stack, block);
	}
}