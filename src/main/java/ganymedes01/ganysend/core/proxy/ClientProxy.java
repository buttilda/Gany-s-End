package ganymedes01.ganysend.core.proxy;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.ModItems;
import ganymedes01.ganysend.client.renderer.entity.EntityAnchoredEnderChestMinecartRenderer;
import ganymedes01.ganysend.client.renderer.tileentity.TileEntityAnchoredEnderChestRender;
import ganymedes01.ganysend.client.renderer.tileentity.TileEntityInfiniteWaterSourceRender;
import ganymedes01.ganysend.client.renderer.tileentity.TileEntityInventoryBinderRender;
import ganymedes01.ganysend.core.handlers.RenderCapeHandler;
import ganymedes01.ganysend.core.handlers.VersionCheckTickHandler;
import ganymedes01.ganysend.core.utils.VersionHelper;
import ganymedes01.ganysend.entities.EntityAnchoredEnderChestMinecart;
import ganymedes01.ganysend.tileentities.TileEntityAnchoredEnderChest;
import ganymedes01.ganysend.tileentities.TileEntityInfiniteWaterSource;
import ganymedes01.ganysend.tileentities.TileEntityInventoryBinder;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;

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
			MinecraftForge.EVENT_BUS.register(new VersionCheckTickHandler());
		}

		if (!Loader.isModLoaded("ganysnether"))
			MinecraftForge.EVENT_BUS.register(new RenderCapeHandler());
	}

	@Override
	public void registerTileEntities() {
		super.registerTileEntities();
		if (GanysEnd.enableInventoryBinder)
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityInventoryBinder.class, new TileEntityInventoryBinderRender());
		if (GanysEnd.enableInfiniteWaterSource)
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityInfiniteWaterSource.class, new TileEntityInfiniteWaterSourceRender());
		if (GanysEnd.enableAnchoredEnderChest)
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnchoredEnderChest.class, new TileEntityAnchoredEnderChestRender());
	}

	@Override
	public void registerRenderers() {
		if (GanysEnd.enableAnchoredEnderChest) {
			MinecraftForgeClient.registerItemRenderer(ModItems.anchoredEnderChestMinecart, new AnchoredEnderChestMinecartRender());
			RenderingRegistry.registerEntityRenderingHandler(EntityAnchoredEnderChestMinecart.class, new EntityAnchoredEnderChestMinecartRenderer());
		}
	}
}