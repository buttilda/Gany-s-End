package ganymedes01.ganysend.lib;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraftforge.common.EnumHelper;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class ModMaterials {

	// Armour
	public static final EnumArmorMaterial ENDIUM_ARMOUR = EnumHelper.addArmorMaterial("ENDIUM", 10, new int[] { 2, 4, 2, 2 }, 12);

	// Item
	public final static EnumToolMaterial ENDIUM_TOOLS = EnumHelper.addToolMaterial("ENDIUM", 3, 131, 12.0F, 1.5F, 0);
	public final static EnumToolMaterial REIN_ENDIUM_TOOLS = EnumHelper.addToolMaterial("REIN_ENDIUM", 3, 1561, 12.0F, 1.5F, 0);
}
