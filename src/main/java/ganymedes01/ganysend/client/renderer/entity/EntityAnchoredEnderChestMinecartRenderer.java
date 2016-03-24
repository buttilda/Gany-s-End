package ganymedes01.ganysend.client.renderer.entity;

import ganymedes01.ganysend.client.renderer.block.BlockChestRenderer;
import ganymedes01.ganysend.entities.EntityAnchoredEnderChestMinecart;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class EntityAnchoredEnderChestMinecartRenderer extends RenderMinecart<EntityAnchoredEnderChestMinecart> {

	public EntityAnchoredEnderChestMinecartRenderer(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(EntityAnchoredEnderChestMinecart entity, double x, double y, double z, float f0, float f1) {
		BlockChestRenderer.USE_ON_TEXTURE = entity.isConnected();
		super.doRender(entity, x, y, z, f0, f1);
	}
}