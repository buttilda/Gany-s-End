package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.enchantment.ModEnchants;
import ganymedes01.ganysend.lib.ModMaterials;

import java.util.Map;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import thaumcraft.api.IRepairable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public abstract class EndiumArmour extends ItemArmor implements IRepairable {

	private final int type;
	private int coolDown;
	private final int MAX_COOL_DOWN = 10;

	public EndiumArmour(int type) {
		super(ModMaterials.ENDIUM_ARMOUR, 0, type);
		this.type = type;
		setMaxStackSize(1);
		setCreativeTab(GanysEnd.endTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.epic;
	}

	@Override
	public boolean getIsRepairable(ItemStack item, ItemStack material) {
		return material.getItem() == ModItems.endiumIngot && material.getItemDamage() == 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		switch (type) {
			case 0:
				return Utils.getArmourTexture(ModMaterials.ENDIUM_ARMOUR.name(), 1);
			case 1:
				return Utils.getArmourTexture(ModMaterials.ENDIUM_ARMOUR.name(), 1);
			case 2:
				return Utils.getArmourTexture(ModMaterials.ENDIUM_ARMOUR.name(), 2);
			case 3:
				return Utils.getArmourTexture(ModMaterials.ENDIUM_ARMOUR.name(), 1);
			default:
				return null;
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if (stack == null)
			return;

		if (getDamage(stack) >= this.getMaxDamage()) {
			stack.stackSize = 0;
			player.renderBrokenItemStack(stack);
			int armourIndex = 0;
			if (stack.getItem() == ModItems.endiumHelmet)
				armourIndex = 4;
			else if (stack.getItem() == ModItems.endiumChestplate)
				armourIndex = 3;
			else if (stack.getItem() == ModItems.endiumLeggings)
				armourIndex = 2;
			else if (stack.getItem() == ModItems.endiumBoots)
				armourIndex = 1;

			player.setCurrentItemOrArmor(armourIndex, null);
			return;
		}

		boolean isWaterproof = false;
		Map enchs = EnchantmentHelper.getEnchantments(stack);
		if (!enchs.isEmpty() && enchs.get(ModEnchants.imperviousness.effectId) != null)
			isWaterproof = true;

		if (!isWaterproof) {
			int xCoord = MathHelper.floor_double(player.posX);
			int yCoord = MathHelper.floor_double(player.posY) + 1;
			int zCoord = MathHelper.floor_double(player.posZ);
			if (world.canLightningStrikeAt(xCoord, yCoord, zCoord))
				coolDown--;
			if (coolDown <= 0) {
				stack.damageItem(1, player);
				coolDown = MAX_COOL_DOWN;
			}
		}

		handleInWater(player, stack, isWaterproof);
	}

	abstract void handleInWater(EntityPlayer player, ItemStack stack, boolean isWaterproof);
}