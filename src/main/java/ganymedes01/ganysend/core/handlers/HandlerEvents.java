package ganymedes01.ganysend.core.handlers;

import java.util.LinkedList;
import java.util.List;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.api.IEndiumScythe;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.PlayerInvWrapper;

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
		if (block != Blocks.air && block != null) {
			if (block == ModBlocks.creative_infinite_fluid_source) {
				event.toolTip.add(StatCollector.translateToLocal("string." + Reference.MOD_ID + ".creativeOnly"));
				event.toolTip.add(StatCollector.translateToLocal("string." + Reference.MOD_ID + ".leftClickContainer"));
			} else if (block == ModBlocks.creative_speedy_hopper)
				event.toolTip.add(StatCollector.translateToLocal("string." + Reference.MOD_ID + ".creativeOnly"));
		} else if (EntityDropEvent.isEndiumTool(event.itemStack)) {
			NBTTagCompound nbt = event.itemStack.getTagCompound();
			if (nbt != null && nbt.getBoolean("Tagged")) {
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
				event.world.setBlockState(event.pos.up(), ModBlocks.ender_flower.getDefaultState());
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
							TileEntity tile = event.world.getTileEntity(new BlockPos(x, y, z));
							if (tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
								IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

								List<ItemStack> inserted = new LinkedList<ItemStack>();
								label: for (ItemStack stack : event.drops) {
									ItemStack old = stack;
									for (int i = 0; i < itemHandler.getSlots(); i++) {
										stack = itemHandler.insertItem(i, stack, false);
										if (stack == null) {
											inserted.add(old);
											continue label;
										} else
											old.stackSize = stack.stackSize;
									}
								}
								event.drops.removeAll(inserted);
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

	@SubscribeEvent
	public void onLightningStrike(EntityStruckByLightningEvent event) {
		Entity entity = event.entity;
		if (entity.worldObj.isRemote)
			return;

		IItemHandler itemHandler = null;
		if (entity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP))
			itemHandler = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
		else if (entity instanceof EntityPlayer) // TODO remove when forge properly integrates it
			itemHandler = new PlayerInvWrapper(((EntityPlayer) entity).inventory);

		if (entity.worldObj.rand.nextFloat() > 0.5F)
			for (int i = 0; i < itemHandler.getSlots(); i++) {
				ItemStack stack = itemHandler.getStackInSlot(i);
				if (stack != null && stack.getItem() instanceof IEndiumScythe) {
					ItemStack copy = stack.copy();

					itemHandler.extractItem(i, stack.stackSize, false);
					((IEndiumScythe) stack.getItem()).addCharge(copy);
					itemHandler.insertItem(i, copy, false);
				}
			}
	}
}