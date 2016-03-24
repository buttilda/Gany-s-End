package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.api.IEndiumTool;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModMaterials;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EndiumAxe extends ItemAxe implements IEndiumTool, IConfigurable {

	public EndiumAxe() {
		this(ModMaterials.ENDIUM_TOOLS);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ENDIUM_AXE_NAME));
	}

	protected EndiumAxe(ToolMaterial material) {
		super(material);
		setHarvestLevel("axe", 3);
		setCreativeTab(GanysEnd.enableEndiumTools ? GanysEnd.endTab : null);
	}

	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setBoolean("Tagged", false);
	}

	@Override
	public boolean getIsRepairable(ItemStack stack, ItemStack material) {
		return Utils.isStackOre(material, "ingotEndium");
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		IInventory tile = Utils.getTileEntity(world, pos, IInventory.class);
		if (tile != null)
			if (stack.getItem() == this) {
				if (!stack.hasTagCompound())
					stack.setTagCompound(new NBTTagCompound());
				NBTTagCompound nbt = stack.getTagCompound();
				nbt.setIntArray("Position", new int[] { pos.getX(), pos.getY(), pos.getZ() });
				nbt.setInteger("Dimension", world.provider.getDimensionId());
				nbt.setBoolean("Tagged", true);
				player.swingItem();
				return false;
			}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.UNCOMMON;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return stack.hasTagCompound() && stack.getTagCompound().hasKey("Position");
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableEndiumTools;
	}
}