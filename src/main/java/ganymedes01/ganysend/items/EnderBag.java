package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EnderBag extends Item implements IConfigurable {

	public EnderBag() {
		setMaxStackSize(1);
		setCreativeTab(GanysEnd.enableEnderBag ? GanysEnd.endTab : null);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ENDER_BAG_NAME));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (world.isRemote)
			return stack;

		player.displayGUIChest(player.getInventoryEnderChest());
		return stack;
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableEnderBag;
	}
}