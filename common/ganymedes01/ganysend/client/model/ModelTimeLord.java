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
public class ModelTimeLord extends ModelBase {

	private ModelRenderer middle;
	private ModelRenderer top;

	public ModelTimeLord(int meta) {
		top = new ModelRenderer(this, 0, 16).setTextureSize(64, 64);
		middle = new ModelRenderer(this, 0, -12).setTextureSize(64, 64);

		if (meta < 4) {
			top.addBox(0F, 14F, 0F, 16, 2, 16, 0.0F);
			middle.addBox(2.0F, 0.0F, 2.0F, 12, 16, 12, 0.0F);
		} else {
			top.addBox(0F, 0F, 0F, 16, 2, 16, 0.0F);
			middle.addBox(2.0F, 0.0F, 2.0F, 12, 16, 12, 0.0F);
		}
		middle.rotateAngleY = (float) (meta * Math.PI / 2);

		if (meta == 0 || meta == 4) {
			middle.rotationPointX = 0;
			middle.rotationPointZ = 0;
		} else if (meta == 1 || meta == 5) {
			middle.rotationPointX = 0;
			middle.rotationPointZ = 16;
		} else if (meta == 2 || meta == 6) {
			middle.rotationPointX = 16;
			middle.rotationPointZ = 16;
		} else if (meta == 3 || meta == 7) {
			middle.rotationPointX = 16;
			middle.rotationPointZ = 0;
		}
	}

	public void renderAll() {
		top.render(0.0625F);
		middle.render(0.0625F);
	}
}
