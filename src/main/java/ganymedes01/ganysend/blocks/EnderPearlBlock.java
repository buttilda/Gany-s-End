package ganymedes01.ganysend.blocks;

import java.util.List;

import ganymedes01.ganysend.GanysEnd;
import ganymedes01.ganysend.IConfigurable;
import ganymedes01.ganysend.ModBlocks.ISubBlocksBlock;
import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.items.blocks.ItemEnderPearlBlock;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class EnderPearlBlock extends Block implements ISubBlocksBlock, IConfigurable {

	public static enum Type implements IStringSerializable {
		BLOCK,
		BRICKS;

		@Override
		public String getName() {
			return name();
		}
	}

	public static final PropertyEnum<EnderPearlBlock.Type> VARIANTS = PropertyEnum.<EnderPearlBlock.Type> create("variant", Type.class);

	public EnderPearlBlock() {
		super(Material.iron);
		setHardness(1.5F);
		setResistance(10.0F);
		setCreativeTab(GanysEnd.enableDecorativeBlocks ? GanysEnd.endTab : null);
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.ENDERPEARL_BLOCK_NAME));
		setDefaultState(blockState.getBaseState().withProperty(VARIANTS, EnderPearlBlock.Type.BLOCK));
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(VARIANTS).ordinal();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
		for (EnderPearlBlock.Type type : EnderPearlBlock.Type.values())
			list.add(new ItemStack(item, 1, type.ordinal()));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANTS, EnderPearlBlock.Type.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANTS).ordinal();
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { VARIANTS });
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemEnderPearlBlock.class;
	}

	@Override
	public boolean isEnabled() {
		return GanysEnd.enableDecorativeBlocks;
	}
}