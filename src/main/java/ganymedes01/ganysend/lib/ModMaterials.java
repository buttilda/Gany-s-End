package ganymedes01.ganysend.lib;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class ModMaterials {

	// Armour
	public static final ArmorMaterial ENDIUM_ARMOUR = EnumHelper.addArmorMaterial("ENDIUM", 10, new int[] { 2, 4, 2, 2 }, 12);

	// Item
	public final static ToolMaterial ENDIUM_TOOLS = EnumHelper.addToolMaterial("ENDIUM", 3, 131, 12.0F, 1.5F, 0);
	public final static ToolMaterial REIN_ENDIUM_TOOLS = EnumHelper.addToolMaterial("REIN_ENDIUM", 3, 1561, 12.0F, 1.5F, 0);
}
