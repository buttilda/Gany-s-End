package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.api.IEndiumTool;
import ganymedes01.ganysend.core.utils.InventoryUtils;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.ModMaterials;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EndiumBow extends ItemBow implements IEndiumTool, IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon[] overlays;
	@SideOnly(Side.CLIENT)
	private IIcon standby;

	public EndiumBow() {
		setMaxDamage(ModMaterials.ENDIUM_TOOLS.getMaxUses());
		setTextureName(Utils.getItemTexture(Strings.ENDIUM_BOW_NAME));
		setCreativeTab(GanysEnd.enableEndiumTools ? GanysEnd.endTab : null);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ENDIUM_BOW_NAME));
	}

	private IInventory getTaggedInventory(World world, ItemStack stack) {
		if (stack.stackTagCompound != null)
			if (stack.getTagCompound().getBoolean("Tagged")) {
				NBTTagCompound data = stack.getTagCompound();
				int x = data.getIntArray("Position")[0];
				int y = data.getIntArray("Position")[1];
				int z = data.getIntArray("Position")[2];
				int dim = data.getInteger("Dimension");

				if (world.provider.dimensionId == dim)
					return Utils.getTileEntity(world, x, y, z, IInventory.class);
			}
		return null;
	}

	private boolean consumeArrow(EntityPlayer player, ItemStack stack) {
		IInventory inventory = getTaggedInventory(player.worldObj, stack);
		if (inventory != null)
			for (int i = 0; i < inventory.getSizeInventory(); i++) {
				ItemStack invtStack = inventory.getStackInSlot(i);
				if (invtStack != null && invtStack.getItem() == Items.arrow && invtStack.stackSize > 0) {
					invtStack.stackSize--;
					if (invtStack.stackSize <= 0)
						inventory.setInventorySlotContents(i, null);
					return true;
				}
			}

		return player.inventory.consumeInventoryItem(Items.arrow);
	}

	private boolean hasArrowsAvailable(EntityPlayer player, ItemStack stack) {
		IInventory inventory = getTaggedInventory(player.worldObj, stack);

		return inventory != null && (InventoryUtils.inventoryContains(inventory, new ItemStack(Items.arrow), false) || player.worldObj.isRemote) || player.inventory.hasItem(Items.arrow);
	}

	private boolean consumesArrow(EntityPlayer player, ItemStack stack) {
		return player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
	}

	private boolean canFire(EntityPlayer player, ItemStack stack) {
		return consumesArrow(player, stack) || hasArrowsAvailable(player, stack);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		ArrowNockEvent event = new ArrowNockEvent(player, stack);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
			return event.result;

		if (canFire(player, stack))
			player.setItemInUse(stack, getMaxItemUseDuration(stack));

		return stack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int itemUseCount) {
		if (world.isRemote)
			return;

		ArrowLooseEvent event = new ArrowLooseEvent(player, stack, getMaxItemUseDuration(stack) - itemUseCount);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
			return;

		if (canFire(player, stack)) {
			float f = event.charge / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;

			if (f < 0.1D)
				return;

			if (f > 1.0F)
				f = 1.0F;

			EntityArrow arrow = new EntityArrow(world, player, f * 2.0F);

			boolean consumedArrow = false;
			if (consumesArrow(player, stack)) {
				arrow.canBePickedUp = 2;
				consumedArrow = true;
			} else
				consumedArrow = consumeArrow(player, stack);

			if (!consumedArrow) {
				arrow.setDead();
				return;
			}

			if (f == 1.0F)
				arrow.setIsCritical(true);

			int power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
			if (power > 0)
				arrow.setDamage(arrow.getDamage() + power * 0.5D + 0.5D);

			int punch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
			if (punch > 0)
				arrow.setKnockbackStrength(punch);

			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0)
				arrow.setFire(100);

			stack.damageItem(1, player);
			world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

			world.spawnEntityInWorld(arrow);
		}
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
		if (tile != null)
			if (stack.getItem() == this) {
				if (stack.stackTagCompound == null)
					stack.setTagCompound(new NBTTagCompound());
				stack.stackTagCompound.setIntArray("Position", new int[] { x, y, z });
				stack.stackTagCompound.setInteger("Dimension", world.provider.dimensionId);
				stack.stackTagCompound.setBoolean("Tagged", true);
				player.swingItem();
				return true;
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
	public int getItemEnchantability() {
		return ModMaterials.ENDIUM_TOOLS.getEnchantability();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int pass) {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		int useRemaining = player.getItemInUseCount();
		ItemStack usingItem = player.getItemInUse();

		if (usingItem != null && usingItem == stack) {
			int charge = stack.getMaxItemUseDuration() - useRemaining;

			if (charge >= 18)
				return pass != 0 ? overlays[2] : getItemIconForUseDuration(2);
			if (charge > 13)
				return pass != 0 ? overlays[1] : getItemIconForUseDuration(1);
			if (charge > 0)
				return pass != 0 ? overlays[0] : getItemIconForUseDuration(0);
		} else
			return pass == 0 ? itemIcon : standby;

		return pass != 0 ? standby : super.getIcon(stack, pass, player, stack, useRemaining);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		standby = reg.registerIcon(getIconString() + "_standby_overlay");
		overlays = new IIcon[bowPullIconNameArray.length];

		for (int i = 0; i < overlays.length; i++)
			overlays[i] = reg.registerIcon(getIconString() + "_" + bowPullIconNameArray[i] + "_overlay");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableEndiumTools;
	}
}