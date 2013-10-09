package ganymedes01.ganysend.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

@SideOnly(Side.CLIENT)
public class ModelInventoryBinderInside extends ModelBase {

	ModelRenderer model;

	public ModelInventoryBinderInside() {
		model = new ModelRenderer(this, 0, 0).setTextureSize(48, 24);
		model.addBox(2.0F, 2.0F, 2.0F, 12, 12, 12, 0.0F);
		model.setRotationPoint(0.0F, 0.0F, 0.0F);
	}

	public void render() {
		model.render(0.0625F);
	}
}
