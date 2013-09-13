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
public class ModelPlayerInventory extends ModelBase {

	ModelRenderer model;

	public ModelPlayerInventory() {
		model = new ModelRenderer(this, 0, 0).setTextureSize(16, 16);
		model.addBox(0.0F, 0.0F, 0.0F, 2, 14, 2, 0.0F);
		model.setRotationPoint(0.0F, 0.0F, 0.0F);
		ModelRenderer model0 = new ModelRenderer(this, 0, 0).setTextureSize(16, 16);
		model0.addBox(14.0F, 0.0F, 0.0F, 2, 14, 2, 0.0F);
		model0.setRotationPoint(0.0F, 0.0F, 0.0F);
		model.addChild(model0);
		ModelRenderer model1 = new ModelRenderer(this, 0, 0).setTextureSize(16, 16);
		model1.addBox(0.0F, 0.0F, 14.0F, 2, 14, 2, 0.0F);
		model1.setRotationPoint(0.0F, 0.0F, 0.0F);
		model.addChild(model1);
		ModelRenderer model2 = new ModelRenderer(this, 0, 0).setTextureSize(16, 16);
		model2.addBox(14.0F, 0.0F, 14.0F, 2, 14, 2, 0.0F);
		model2.setRotationPoint(0.0F, 0.0F, 0.0F);
		model.addChild(model2);
		ModelRenderer model3 = new ModelRenderer(this, 0, 0).setTextureSize(16, 16);
		model3.addBox(2.0F, 0.0F, 0.0F, 12, 2, 2, 0.0F);
		model3.setRotationPoint(0.0F, 0.0F, 0.0F);
		model.addChild(model3);
		ModelRenderer model4 = new ModelRenderer(this, 0, 0).setTextureSize(16, 16);
		model4.addBox(2.0F, 0.0F, 14.0F, 12, 2, 2, 0.0F);
		model4.setRotationPoint(0.0F, 0.0F, 0.0F);
		model.addChild(model4);
		ModelRenderer model7 = new ModelRenderer(this, 0, 0).setTextureSize(16, 16);
		model7.addBox(0.0F, 0.0F, 2.0F, 2, 2, 12, 0.0F);
		model7.setRotationPoint(0.0F, 0.0F, 0.0F);
		model.addChild(model7);
		ModelRenderer model8 = new ModelRenderer(this, 0, 0).setTextureSize(16, 16);
		model8.addBox(14.0F, 0.0F, 2.0F, 2, 2, 12, 0.0F);
		model8.setRotationPoint(0.0F, 0.0F, 0.0F);
		model.addChild(model8);
		ModelRenderer model9 = new ModelRenderer(this, 0, 0).setTextureSize(16, 16);
		model9.addBox(0.0F, 14.0F, 0.0F, 16, 2, 16, 0.0F);
		model9.setRotationPoint(0.0F, 0.0F, 0.0F);
		model.addChild(model9);
	}

	public void render() {
		model.render(0.0625F);
	}
}
