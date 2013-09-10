package ganymedes01.ganysend.lib;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraftforge.common.EnumHelper;

public class ModMaterials {

	// Armour
	public static final EnumArmorMaterial ENDIUM_ARMOUR = EnumHelper.addArmorMaterial("ENDIUM", 10, new int[] { 2, 4, 2, 2 }, 0);

	// Item
	public final static EnumToolMaterial ENDIUM = EnumHelper.addToolMaterial("ENDIUM", 0, 10, 2F, -3F, 0);
}
