package ganymedes01.ganysend.core.proxy;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModItems;
import ganymedes01.ganysend.client.renderer.block.BlockChestRenderer;
import ganymedes01.ganysend.client.renderer.block.BlockEnderFlowerRenderer;
import ganymedes01.ganysend.client.renderer.block.BlockFilteringHopperRender;
import ganymedes01.ganysend.client.renderer.block.BlockInfiniteWaterSourceRender;
import ganymedes01.ganysend.client.renderer.block.BlockInventoryBinderRender;
import ganymedes01.ganysend.client.renderer.block.BlockRawEndiumRender;
import ganymedes01.ganysend.client.renderer.block.BlockTimeManipulatorRender;
import ganymedes01.ganysend.client.renderer.item.ItemSkullRender;
import ganymedes01.ganysend.client.renderer.tileentity.TileEntityAnchoredEnderChestRender;
import ganymedes01.ganysend.client.renderer.tileentity.TileEntityBlockSkullRender;
import ganymedes01.ganysend.client.renderer.tileentity.TileEntityInfiniteWaterSourceRender;
import ganymedes01.ganysend.client.renderer.tileentity.TileEntityInventoryBinderRender;
import ganymedes01.ganysend.core.handlers.RenderCapeHandler;
import ganymedes01.ganysend.core.handlers.RenderPlayerHandler;
import ganymedes01.ganysend.core.handlers.VersionCheckTickHandler;
import ganymedes01.ganysend.core.utils.VersionHelper;
import ganymedes01.ganysend.tileentities.TileEntityAnchoredEnderChest;
import ganymedes01.ganysend.tileentities.TileEntityBlockSkull;
import ganymedes01.ganysend.tileentities.TileEntityInfiniteWaterSource;
import ganymedes01.ganysend.tileentities.TileEntityInventoryBinder;
import net.minecraft.init.Items;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class ClientProxy extends CommonProxy {

	@Override
	public void registerEvents() {
		super.registerEvents();
		if (GanysEnd.shouldDoVersionCheck) {
			VersionHelper.execute();
			FMLCommonHandler.instance().bus().register(new VersionCheckTickHandler());
		}

		MinecraftForge.EVENT_BUS.register(new RenderPlayerHandler());

		if (!Loader.isModLoaded("ganysnether"))
			MinecraftForge.EVENT_BUS.register(new RenderCapeHandler());
	}

	@Override
	public void registerTileEntities() {
		super.registerTileEntities();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockSkull.class, new TileEntityBlockSkullRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityInventoryBinder.class, new TileEntityInventoryBinderRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityInfiniteWaterSource.class, new TileEntityInfiniteWaterSourceRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnchoredEnderChest.class, new TileEntityAnchoredEnderChestRender());
	}

	@Override
	public void registerRenderers() {
		MinecraftForgeClient.registerItemRenderer(ModItems.skull, new ItemSkullRender());
		MinecraftForgeClient.registerItemRenderer(Items.skull, new ItemSkullRender());

		RenderingRegistry.registerBlockHandler(new BlockFilteringHopperRender());
		RenderingRegistry.registerBlockHandler(new BlockInventoryBinderRender());
		if (GanysEnd.enableTimeManipulator)
			RenderingRegistry.registerBlockHandler(new BlockTimeManipulatorRender());
		RenderingRegistry.registerBlockHandler(new BlockRawEndiumRender());
		RenderingRegistry.registerBlockHandler(new BlockInfiniteWaterSourceRender());
		RenderingRegistry.registerBlockHandler(new BlockChestRenderer());
		RenderingRegistry.registerBlockHandler(new BlockEnderFlowerRenderer());
	}
}