package ganymedes01.ganysend.items;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.entities.EntityAnchoredEnderChestMinecart;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class ItemAnchoredEnderChestMinecart extends ItemMinecart implements IConfigurable {

	public ItemAnchoredEnderChestMinecart() {
		super(null);
		setCreativeTab(GanysEnd.enableAnchoredEnderChest ? GanysEnd.endTab : null);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ANCHORED_ENDER_CHEST_MINECART_NAME));
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (BlockRailBase.isRailBlock(world.getBlockState(pos))) {
			if (!world.isRemote) {
				EntityAnchoredEnderChestMinecart minecart = new EntityAnchoredEnderChestMinecart(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
				minecart.setPlayerName(player.getName());
				world.spawnEntityInWorld(minecart);
			}
			stack.stackSize--;
			return true;
		} else
			return false;
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableAnchoredEnderChest;
	}
}