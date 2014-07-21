package ganymedes01.ganysend.core.proxy;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModBlocks;
import ganymedes01.ganysend.ModItems;
import ganymedes01.ganysend.client.renderer.block.BlockFilteringHopperRender;
import ganymedes01.ganysend.client.renderer.block.BlockInventoryBinderRender;
import ganymedes01.ganysend.client.renderer.block.BlockRawEndiumRender;
import ganymedes01.ganysend.client.renderer.block.BlockTimeManipulatorRender;
import ganymedes01.ganysend.client.renderer.item.ItemInfiniteWaterSourceRender;
import ganymedes01.ganysend.client.renderer.item.ItemSkullRender;
import ganymedes01.ganysend.client.renderer.tileentity.TileEntityBlockNewSkullRender;
import ganymedes01.ganysend.client.renderer.tileentity.TileEntityInfiniteWaterSourceRender;
import ganymedes01.ganysend.client.renderer.tileentity.TileEntityInventoryBinderRender;
import ganymedes01.ganysend.core.handlers.RenderCapeHandler;
import ganymedes01.ganysend.core.handlers.RenderPlayerHandler;
import ganymedes01.ganysend.core.handlers.VersionCheckTickHandler;
import ganymedes01.ganysend.core.utils.VersionHelper;
import ganymedes01.ganysend.tileentities.TileEntityBlockNewSkull;
import ganymedes01.ganysend.tileentities.TileEntityInfiniteWaterSource;
import ganymedes01.ganysend.tileentities.TileEntityInventoryBinder;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockNewSkull.class, new TileEntityBlockNewSkullRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityInventoryBinder.class, new TileEntityInventoryBinderRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityInfiniteWaterSource.class, new TileEntityInfiniteWaterSourceRender());
	}

	@Override
	public void registerRenderers() {
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.infiniteWaterSource), ItemInfiniteWaterSourceRender.INSTANCE);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.creativeInfiniteFluidSource), ItemInfiniteWaterSourceRender.INSTANCE);
		MinecraftForgeClient.registerItemRenderer(ModItems.itemNewSkull, new ItemSkullRender());
		MinecraftForgeClient.registerItemRenderer(Items.skull, new ItemSkullRender());

		RenderingRegistry.registerBlockHandler(new BlockFilteringHopperRender());
		RenderingRegistry.registerBlockHandler(new BlockInventoryBinderRender());
		if (GanysEnd.enableTimeManipulator)
			RenderingRegistry.registerBlockHandler(new BlockTimeManipulatorRender());
		RenderingRegistry.registerBlockHandler(new BlockRawEndiumRender());
	}
}