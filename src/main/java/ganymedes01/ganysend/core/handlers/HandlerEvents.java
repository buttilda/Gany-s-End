package ganymedes01.ganysend.core.handlers;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.core.utils.InventoryUtils;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class HandlerEvents {

	@SideOnly(Side.CLIENT)
	public static TextureAtlasSprite endium_still, endium_flow;

	@SubscribeEvent
	public void tooltip(ItemTooltipEvent event) {
		Block block = Block.getBlockFromItem(event.itemStack.getItem());
		if (block != Blocks.air) {
			if (block == ModBlocks.creativeInfiniteFluidSource) {
				event.toolTip.add(StatCollector.translateToLocal("string." + Reference.MOD_ID + ".creativeOnly"));
				event.toolTip.add(StatCollector.translateToLocal("string." + Reference.MOD_ID + ".leftClickContainer"));
			} else if (block == ModBlocks.creativeSpeedyHopper)
				event.toolTip.add(StatCollector.translateToLocal("string." + Reference.MOD_ID + ".creativeOnly"));
		} else if (EntityDropEvent.isEndiumTool(event.itemStack)) {
			NBTTagCompound nbt = event.itemStack.getTagCompound();
			if (nbt != null && nbt.hasKey("Position") && nbt.hasKey("Dimension")) {
				String pos = nbt.getIntArray("Position")[0] + ", " + nbt.getIntArray("Position")[1] + ", " + nbt.getIntArray("Position")[2];
				event.toolTip.add(nbt.getInteger("Dimension") + " : " + pos);
			} else
				event.toolTip.add(StatCollector.translateToLocal("string." + Reference.MOD_ID + ".nottagged"));
		}
	}

	@SubscribeEvent
	public void endBonemealEvent(BonemealEvent event) {
		if (!GanysEnd.enableEnderFlower)
			return;

		if (event.world.provider.getDimensionId() == 1 && event.block == Blocks.end_stone)
			if (event.world.isAirBlock(event.pos.up())) {
				event.world.setBlockState(event.pos.up(), ModBlocks.enderFlower.getDefaultState());
				event.setResult(Result.ALLOW);
			}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onBlockHarvested(HarvestDropsEvent event) {
		if (!GanysEnd.enableEndiumTools)
			return;

		if (event.harvester != null)
			if (event.dropChance > 0.0F)
				if (!event.drops.isEmpty()) {
					ItemStack current = event.harvester.getCurrentEquippedItem();
					if (current == null)
						return;
					if (EntityDropEvent.isEndiumTool(current))
						if (current.hasTagCompound() && current.getTagCompound().getBoolean("Tagged")) {
							NBTTagCompound data = current.getTagCompound();
							int x = data.getIntArray("Position")[0];
							int y = data.getIntArray("Position")[1];
							int z = data.getIntArray("Position")[2];
							int dim = data.getInteger("Dimension");

							if (event.world.provider.getDimensionId() != dim)
								return;
							IInventory tile = Utils.getTileEntity(event.world, new BlockPos(x, y, z), IInventory.class);
							if (tile != null) {
								event.dropChance = -1.0F;
								for (ItemStack stack : event.drops)
									if (!InventoryUtils.addStackToInventory(tile, stack))
										InventoryUtils.dropStack(event.world, event.pos, stack);
							}
						}
				}
	}

	@SubscribeEvent
	public void interactEvent(PlayerInteractEvent event) {
		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			World world = event.world;
			EntityPlayer player = event.entityPlayer;
			BlockPos pos = event.pos;

			if (world == null || world.isRemote)
				return;
			if (player.isSneaking())
				return;
			else {
				ItemStack stack = player.getCurrentEquippedItem();
				if (stack != null && EntityDropEvent.isTinkersEndiumTool(stack)) {
					IInventory tile = Utils.getTileEntity(world, pos, IInventory.class);
					if (tile != null) {
						if (!stack.hasTagCompound())
							stack.setTagCompound(new NBTTagCompound());
						NBTTagCompound nbt = stack.getTagCompound();
						nbt.setIntArray("Position", new int[] { pos.getX(), pos.getY(), pos.getZ() });
						nbt.setInteger("Dimension", world.provider.getDimensionId());
						nbt.setBoolean("Tagged", true);
						player.swingItem();
					}
				}
			}
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void loadTextures(TextureStitchEvent.Pre event) {
		//		if (event.map.getTextureType() == 0) {
		//			endium_still = event.map.registerIcon(Utils.getBlockTexture("endium_still"));
		//			endium_flow = event.map.registerIcon(Utils.getBlockTexture("endium_flow"));
		//		}
	}
}