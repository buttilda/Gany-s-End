package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.CustomDamageSources;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModIDs;
import ganymedes01.ganysend.lib.ModMaterials;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class EnderScythe extends ItemSword {

	public static final float DAMAGE = 8F;

	public EnderScythe() {
		super(ModIDs.ENDER_SCYTHE_ID, ModMaterials.ENDIUM);
		setMaxStackSize(1);
		setCreativeTab(GanysEnd.endTab);
		setTextureName(Utils.getItemTexture(Strings.ENDER_SCYTHE_NAME));
		setUnlocalizedName(Utils.getUnlocalizedName(Strings.ENDER_SCYTHE_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.epic;
	}

	private void damageItem(ItemStack item, EntityLivingBase player) {
		if (player instanceof EntityPlayer)
			if (((EntityPlayer) player).capabilities.isCreativeMode)
				return;
		item.damageItem(5, player);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack item, EntityPlayer player, Entity target) {
		if (target instanceof EntityLivingBase)
			if (!player.worldObj.isRemote) {
				((EntityLivingBase) target).attackEntityFrom(CustomDamageSources.beheading, DAMAGE);
				damageItem(item, player);
			}
		return false;
	}

	@Override
	public boolean getIsRepairable(ItemStack item, ItemStack material) {
		return material.getItem() == ModItems.endiumIngot;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack item) {
		return true;
	}
}
