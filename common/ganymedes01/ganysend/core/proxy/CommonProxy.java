package ganymedes01.ganysend.core.proxy;

import ganymedes01.ganysend.client.gui.inventory.GuiAdvancedFilteringHopper;
import ganymedes01.ganysend.client.gui.inventory.GuiBasicFilteringHopper;
import ganymedes01.ganysend.core.handlers.ArmourHandler;
import ganymedes01.ganysend.core.handlers.BlockHarvestEvent;
import ganymedes01.ganysend.core.handlers.BonemealOnTheEndEvent;
import ganymedes01.ganysend.core.handlers.EntityDeathEvent;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.inventory.ContainerAdvancedFilteringHopper;
import ganymedes01.ganysend.inventory.ContainerFilteringHopper;
import ganymedes01.ganysend.lib.GUIsID;
import ganymedes01.ganysend.lib.Strings;
import ganymedes01.ganysend.tileentities.TileEntityAdvancedFilteringHopper;
import ganymedes01.ganysend.tileentities.TileEntityBlockNewSkull;
import ganymedes01.ganysend.tileentities.TileEntityBlockShifter;
import ganymedes01.ganysend.tileentities.TileEntityEntityShifter;
import ganymedes01.ganysend.tileentities.TileEntityFilteringHopper;
import ganymedes01.ganysend.tileentities.TileEntityInfiniteWaterSource;
import ganymedes01.ganysend.tileentities.TileEntityInventoryBinder;
import ganymedes01.ganysend.tileentities.TileEntitySpeedyHopper;
import ganymedes01.ganysend.tileentities.TileEntityTimeManipulator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class CommonProxy implements IGuiHandler {

	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityBlockShifter.class, Utils.getUnlocalizedName(Strings.BLOCK_SHIFTER_NAME));
		GameRegistry.registerTileEntity(TileEntityBlockNewSkull.class, Utils.getUnlocalizedName(Strings.BLOCK_NEW_SKULL_NAME));
		GameRegistry.registerTileEntity(TileEntityFilteringHopper.class, Utils.getUnlocalizedName(Strings.BASIC_FILTERING_HOPPER_NAME));
		GameRegistry.registerTileEntity(TileEntityAdvancedFilteringHopper.class, Utils.getUnlocalizedName(Strings.ADVANCED_FILTERING_HOPPER_NAME));
		GameRegistry.registerTileEntity(TileEntitySpeedyHopper.class, Utils.getUnlocalizedName(Strings.SPEEDY_HOPPER_NAME));
		GameRegistry.registerTileEntity(TileEntityTimeManipulator.class, Utils.getUnlocalizedName(Strings.TIME_MANIPULATOR_NAME));
		GameRegistry.registerTileEntity(TileEntityEntityShifter.class, Utils.getUnlocalizedName(Strings.ENTITY_SHIFTER_NAME));
		GameRegistry.registerTileEntity(TileEntityInventoryBinder.class, Utils.getUnlocalizedName(Strings.INVENTORY_BINDER_NAME));
		GameRegistry.registerTileEntity(TileEntityInfiniteWaterSource.class, Utils.getUnlocalizedName(Strings.INFINITE_WATER_SOURCE_NAME));
	}

	public void registerEventHandlers() {
		MinecraftForge.EVENT_BUS.register(new BonemealOnTheEndEvent());
		MinecraftForge.EVENT_BUS.register(new BlockHarvestEvent());
		MinecraftForge.EVENT_BUS.register(new EntityDeathEvent());
		MinecraftForge.EVENT_BUS.register(new ArmourHandler());
	}

	public void registerRenderers() {
	}

	public void handleTimeManipulatorPacket(int x, int y, int z, boolean revertTime, boolean advanceTime) {
	}

	public void handleInventoryBinderPacket(int x, int y, int z, String playerName) {
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		switch (ID) {
			case GUIsID.BASIC_FILTERING_HOPPER:
				TileEntityFilteringHopper tileBasicHopper = (TileEntityFilteringHopper) tile;
				return new ContainerFilteringHopper(player.inventory, tileBasicHopper);
			case GUIsID.ADVANCED_FILTERING_HOPPER:
				TileEntityAdvancedFilteringHopper tileAdvancedHopper = (TileEntityAdvancedFilteringHopper) tile;
				return new ContainerAdvancedFilteringHopper(player.inventory, tileAdvancedHopper);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		switch (ID) {
			case GUIsID.BASIC_FILTERING_HOPPER:
				TileEntityFilteringHopper tileBasicHopper = (TileEntityFilteringHopper) tile;
				return new GuiBasicFilteringHopper(player.inventory, tileBasicHopper);
			case GUIsID.ADVANCED_FILTERING_HOPPER:
				TileEntityAdvancedFilteringHopper tileAdvancedHopper = (TileEntityAdvancedFilteringHopper) tile;
				return new GuiAdvancedFilteringHopper(player.inventory, tileAdvancedHopper);
		}
		return null;
	}
}
