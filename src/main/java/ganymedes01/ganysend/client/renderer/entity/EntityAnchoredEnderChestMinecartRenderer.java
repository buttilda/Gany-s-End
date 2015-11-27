package ganymedes01.ganysend.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.ganysend.client.renderer.block.BlockChestRenderer;
import ganymedes01.ganysend.entities.EntityAnchoredEnderChestMinecart;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraft.entity.item.EntityMinecart;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class EntityAnchoredEnderChestMinecartRenderer extends RenderMinecart {

	@Override
	public void doRender(EntityMinecart entity, double x, double y, double z, float f0, float f1) {
		if (entity instanceof EntityAnchoredEnderChestMinecart)
			BlockChestRenderer.USE_ON_TEXTURE = ((EntityAnchoredEnderChestMinecart) entity).isConnected();
		super.doRender(entity, x, y, z, f0, f1);
	}
}