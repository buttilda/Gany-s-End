package thaumcraft.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumHelper;


/**
 * @author Azanor
 *
 *
 * IMPORTANT: If you are adding your own aspects to items it is a good idea to do it AFTER Thaumcraft adds its aspects, otherwise odd things may happen.
 *
 */
public class ThaumcraftApi {
	
	//Materials	
	public static EnumToolMaterial toolMatThaumium = EnumHelper.addToolMaterial("THAUMIUM", 3, 400, 7F, 2, 22);
	public static EnumToolMaterial toolMatElemental = EnumHelper.addToolMaterial("THAUMIUM_ELEMENTAL", 3, 1500, 10F, 3, 18);
	public static EnumArmorMaterial armorMatThaumium = EnumHelper.addArmorMaterial("THAUMIUM", 25, new int[] { 2, 6, 5, 2 }, 25);
	public static EnumArmorMaterial armorMatSpecial = EnumHelper.addArmorMaterial("SPECIAL", 25, new int[] { 1, 3, 2, 1 }, 25);
	
	//Enchantment references
	public static int enchantFrugal;
	public static int enchantPotency;
	public static int enchantWandFortune;
	public static int enchantHaste;
	public static int enchantRepair;
	
	//Miscellaneous
	/**
	 * Portable Hole Block-id Blacklist. 
	 * Simply add the block-id's of blocks you don't want the portable hole to go through.
	 */
	public static ArrayList<Integer> portableHoleBlackList = new ArrayList<Integer>();
	
	//RECIPES/////////////////////////////////////////	
	private static HashMap<List,ItemStack> smeltingBonus = new HashMap<List,ItemStack>();
	
	/**
	 * This method is used to determine what bonus items are generated when the infernal furnace smelts items
	 * @param in The result (not input) of the smelting operation. e.g. new ItemStack(ingotGold)
	 * @param out The bonus item that can be produced from the smelting operation e.g. new ItemStack(nuggetGold,0,0).
	 * Stacksize should be 0 unless you want to guarantee that at least 1 item is always produced.
	 */
	public static void addSmeltingBonus(ItemStack in, ItemStack out) {
		smeltingBonus.put(
				Arrays.asList(in.itemID,in.getItemDamage()), 
				new ItemStack(out.itemID,0,out.getItemDamage()));
	}
}