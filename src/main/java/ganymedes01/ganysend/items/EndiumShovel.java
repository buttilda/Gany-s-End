package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.IEndiumTool;
import ganymedes01.ganysend.lib.ModMaterials;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EndiumShovel extends ItemSpade implements IEndiumTool {

	@SideOnly(Side.CLIENT)
	private IIcon overlay;

	public EndiumShovel() {
		this(ModMaterials.ENDIUM_TOOLS);
		setTextureName(Utils.getItemTexture(Strings.ENDIUM_SHOVEL_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ENDIUM_SHOVEL_NAME));
	}

	protected EndiumShovel(ToolMaterial material) {
		super(material);
		setHarvestLevel("shovel", 3);
		setCreativeTab(GanysEnd.enableEndiumTools ? GanysEnd.endTab : null);
	}

	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		if (stack.stackTagCompound == null)
			stack.setTagCompound(new NBTTagCompound());
		stack.stackTagCompound.setBoolean("Tagged", false);
	}

	@Override
	public boolean getIsRepairable(ItemStack item, ItemStack material) {
		return Utils.isStackOre(material, "ingotEndium");
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		IInventory tile = Utils.getTileEntity(world, x, y, z, IInventory.class);
		if (tile != null && tile instanceof IInventory)
			if (stack.getItem() == this) {
				if (stack.stackTagCompound == null)
					stack.setTagCompound(new NBTTagCompound());
				stack.stackTagCompound.setIntArray("Position", new int[] { x, y, z });
				stack.stackTagCompound.setInteger("Dimension", world.provider.dimensionId);
				stack.stackTagCompound.setBoolean("Tagged", true);
				player.swingItem();
				return false;
			}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.uncommon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack, int pass) {
		return pass == 0 && stack.hasTagCompound() && stack.stackTagCompound.hasKey("Position");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		overlay = reg.registerIcon(getIconString() + "_overlay");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
		return pass == 0 ? itemIcon : overlay;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
}