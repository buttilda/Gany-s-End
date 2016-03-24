package ganymedes01.ganysend.core.proxy;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.client.gui.inventory.GuiAdvancedFilteringHopper;
import ganymedes01.ganysend.client.gui.inventory.GuiBasicFilteringHopper;
import ganymedes01.ganysend.client.gui.inventory.GuiEnderFurnace;
import ganymedes01.ganysend.client.gui.inventory.GuiVoidCrate;
import ganymedes01.ganysend.configuration.ConfigurationHandler;
import ganymedes01.ganysend.core.handlers.ArmourHandler;
import ganymedes01.ganysend.core.handlers.EntityDropEvent;
import ganymedes01.ganysend.core.handlers.HandlerEvents;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.entities.EntityAnchoredEnderChestMinecart;
import ganymedes01.ganysend.inventory.ContainerAdvancedFilteringHopper;
import ganymedes01.ganysend.inventory.ContainerEnderFurnace;
import ganymedes01.ganysend.inventory.ContainerFilteringHopper;
import ganymedes01.ganysend.inventory.ContainerVoidCrate;
import ganymedes01.ganysend.lib.GUIsID;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityAdvancedFilteringHopper;
import ganymedes01.ganysend.tileentities.TileEntityAnchoredEnderChest;
import ganymedes01.ganysend.tileentities.TileEntityBlockShifter;
import ganymedes01.ganysend.tileentities.TileEntityCreativeSpeedyHopper;
import ganymedes01.ganysend.tileentities.TileEntityEnderFurnace;
import ganymedes01.ganysend.tileentities.TileEntityEntityShifter;
import ganymedes01.ganysend.tileentities.TileEntityFilteringHopper;
import ganymedes01.ganysend.tileentities.TileEntityInfiniteWaterSource;
import ganymedes01.ganysend.tileentities.TileEntityInventoryBinder;
import ganymedes01.ganysend.tileentities.TileEntitySpeedyHopper;
import ganymedes01.ganysend.tileentities.TileEntityTimeManipulator;
import ganymedes01.ganysend.tileentities.TileEntityVoidCrate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class CommonProxy implements IGuiHandler {

	public void registerEvents() {
		MinecraftForge.EVENT_BUS.register(ConfigurationHandler.INSTANCE);

		MinecraftForge.EVENT_BUS.register(new HandlerEvents());
		MinecraftForge.EVENT_BUS.register(new EntityDropEvent());
		MinecraftForge.EVENT_BUS.register(new ArmourHandler());
	}

	public void registerTileEntities() {
		if (GanysEnd.enableShifters) {
			GameRegistry.registerTileEntity(TileEntityBlockShifter.class, Utils.getUnlocalisedName(Strings.BLOCK_SHIFTER_NAME));
			GameRegistry.registerTileEntity(TileEntityEntityShifter.class, Utils.getUnlocalisedName(Strings.ENTITY_SHIFTER_NAME));
		}
		if (GanysEnd.enableHoppers) {
			GameRegistry.registerTileEntity(TileEntityFilteringHopper.class, Utils.getUnlocalisedName(Strings.BASIC_FILTERING_HOPPER_NAME));
			GameRegistry.registerTileEntity(TileEntityAdvancedFilteringHopper.class, Utils.getUnlocalisedName(Strings.ADVANCED_FILTERING_HOPPER_NAME));
			GameRegistry.registerTileEntity(TileEntitySpeedyHopper.class, Utils.getUnlocalisedName(Strings.SPEEDY_HOPPER_NAME));
			GameRegistry.registerTileEntity(TileEntityCreativeSpeedyHopper.class, Utils.getUnlocalisedName(Strings.CREATIVE_SPEEDY_HOPPER_NAME));
		}
		if (GanysEnd.enableTimeManipulator)
			GameRegistry.registerTileEntity(TileEntityTimeManipulator.class, Utils.getUnlocalisedName(Strings.TIME_MANIPULATOR_NAME));
		if (GanysEnd.enableInventoryBinder)
			GameRegistry.registerTileEntity(TileEntityInventoryBinder.class, Utils.getUnlocalisedName(Strings.INVENTORY_BINDER_NAME));
		if (GanysEnd.enableInfiniteWaterSource)
			GameRegistry.registerTileEntity(TileEntityInfiniteWaterSource.class, Utils.getUnlocalisedName(Strings.INFINITE_WATER_SOURCE_NAME));
		if (GanysEnd.enableVoidCrate)
			GameRegistry.registerTileEntity(TileEntityVoidCrate.class, Utils.getUnlocalisedName(Strings.VOID_CRATE_NAME));
		if (GanysEnd.enableEnderFurnace)
			GameRegistry.registerTileEntity(TileEntityEnderFurnace.class, Utils.getUnlocalisedName(Strings.ENDER_FURNACE_NAME));
		if (GanysEnd.enableAnchoredEnderChest)
			GameRegistry.registerTileEntity(TileEntityAnchoredEnderChest.class, Utils.getUnlocalisedName(Strings.ANCHORED_ENDER_CHEST_NAME));
	}

	public void registerEntities() {
		if (GanysEnd.enableAnchoredEnderChest)
			EntityRegistry.registerModEntity(EntityAnchoredEnderChestMinecart.class, Strings.ANCHORED_ENDER_CHEST_MINECART_NAME, 0, GanysEnd.instance, 80, 3, true);
	}

	public void registerRenderers() {
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		switch (ID) {
			case GUIsID.BASIC_FILTERING_HOPPER:
				return new ContainerFilteringHopper(player.inventory, (TileEntityFilteringHopper) tile);
			case GUIsID.ADVANCED_FILTERING_HOPPER:
				return new ContainerAdvancedFilteringHopper(player.inventory, (TileEntityAdvancedFilteringHopper) tile);
			case GUIsID.VOID_CRATE:
				return new ContainerVoidCrate(player.inventory, (TileEntityVoidCrate) tile);
			case GUIsID.ENDER_FURNACE:
				return new ContainerEnderFurnace(player.inventory, (TileEntityEnderFurnace) tile);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		switch (ID) {
			case GUIsID.BASIC_FILTERING_HOPPER:
				return new GuiBasicFilteringHopper(player.inventory, (TileEntityFilteringHopper) tile);
			case GUIsID.ADVANCED_FILTERING_HOPPER:
				return new GuiAdvancedFilteringHopper(player.inventory, (TileEntityAdvancedFilteringHopper) tile);
			case GUIsID.VOID_CRATE:
				return new GuiVoidCrate(player.inventory, (TileEntityVoidCrate) tile);
			case GUIsID.ENDER_FURNACE:
				return new GuiEnderFurnace(player.inventory, (TileEntityEnderFurnace) tile);
		}
		return null;
	}
}