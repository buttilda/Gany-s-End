package ganymedes01.ganysend.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.entities.EntityAnchoredEnderChestMinecart;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class ItemAnchoredEnderChestMinecart extends ItemMinecart implements IConfigurable {

	public ItemAnchoredEnderChestMinecart() {
		super(0);
		setCreativeTab(GanysEnd.enableAnchoredEnderChest ? GanysEnd.endTab : null);
		setTextureName(Utils.getItemTexture(Strings.ANCHORED_ENDER_CHEST_MINECART_NAME));
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ANCHORED_ENDER_CHEST_MINECART_NAME));
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (BlockRailBase.func_150051_a(world.getBlock(x, y, z))) {
			if (!world.isRemote) {
				EntityAnchoredEnderChestMinecart minecart = new EntityAnchoredEnderChestMinecart(world, x + 0.5F, y + 0.5F, z + 0.5F);
				minecart.setPlayerName(player.getCommandSenderName());
				world.spawnEntityInWorld(minecart);
			}
			stack.stackSize--;
			return true;
		} else
			return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableAnchoredEnderChest;
	}
}