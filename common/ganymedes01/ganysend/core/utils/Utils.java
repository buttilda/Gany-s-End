package ganymedes01.ganysend.core.utils;

import ganymedes01.ganysend.lib.Reference;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class Utils {

	public static final String getUnlocalizedName(String name) {
		return Reference.MOD_ID + "." + name;
	}

	public static final String getBlockTexture(String name, boolean hasSubBlocks) {
		if (hasSubBlocks)
			return Reference.ITEM_BLOCK_TEXTURE_PATH + name + "_";
		else
			return Reference.ITEM_BLOCK_TEXTURE_PATH + name;
	}

	public static final String getItemTexture(String name) {
		return Reference.ITEM_BLOCK_TEXTURE_PATH + name;
	}

	public static final String getArmourTexture(String name, int layer) {
		return Reference.ARMOUR_TEXTURE_PATH + name.toLowerCase() + "_layer_" + layer + ".png";
	}

	public static final String getGUITexture(String name) {
		return Reference.GUI_TEXTURE_PATH + name + ".png";
	}

	public static final String getEntityTexture(String name) {
		return Reference.ENTITY_TEXTURE_PATH + name + ".png";
	}

	public static final String getEntityItemTexture(String name) {
		return Reference.ENTITY_ITEM_TEXTURE_PATH + name + ".png";
	}

	public static final String getConainerName(String name) {
		return "container." + Reference.MOD_ID + ":" + name;
	}

	public static final void dropStack(World world, int x, int y, int z, ItemStack stack) {
		if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
			float f = 0.7F;
			double d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			EntityItem entityitem = new EntityItem(world, x + d0, y + d1, z + d2, stack);
			entityitem.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(entityitem);
		}
	}

	public static final int getColour(int R, int G, int B) {
		return R * 65536 + G * 191 + B;
	}

	// Head textures
	private static final String ENDERMAN_HEAD = getEntityTexture("enderman");
	private static final String ENDERMAN_EYES = "textures/entity/enderman/enderman_eyes.png";
	private static final String BLAZE_HEAD = "textures/entity/blaze.png";
	private static final String PIGMAN_HEAD = "textures/entity/zombie_pigman.png";
	private static final String SPIDER_HEAD = "textures/entity/spider/spider.png";
	private static final String CAVE_SPIDER_HEAD = "textures/entity/spider/cave_spider.png";
	private static final String PIG_HEAD = "textures/entity/pig/pig.png";
	private static final String COW_HEAD = "textures/entity/cow/cow.png";
	private static final String MOOSHROOM_HEAD = "textures/entity/cow/mooshroom.png";
	private static final String SHEEP_HEAD = "textures/entity/sheep/sheep.png";
	public static final String SHEEP_FUR_HEAD = "textures/entity/sheep/sheep_fur.png";
	private static final String WOLF_HEAD = "textures/entity/wolf/wolf.png";
	private static final String VILLAGER_HEAD = "textures/entity/villager/villager.png";
	private static final String CHICKEN_HEAD = "textures/entity/chicken.png";
	private static final String WITCH_HEAD = "textures/entity/witch.png";
	private static final String ZOMBIE_VILLAGER_HEAD = "textures/entity/zombie/zombie_villager.png";

	public static final ResourceLocation[] headTextures = new ResourceLocation[] { new ResourceLocation(BLAZE_HEAD), new ResourceLocation(ENDERMAN_HEAD), new ResourceLocation(PIGMAN_HEAD), new ResourceLocation(ENDERMAN_EYES), new ResourceLocation(SPIDER_HEAD),
	new ResourceLocation(CAVE_SPIDER_HEAD), new ResourceLocation(PIG_HEAD), new ResourceLocation(COW_HEAD), new ResourceLocation(MOOSHROOM_HEAD), new ResourceLocation(SHEEP_HEAD), new ResourceLocation(WOLF_HEAD), new ResourceLocation(VILLAGER_HEAD), new ResourceLocation(CHICKEN_HEAD),
	new ResourceLocation(WITCH_HEAD), new ResourceLocation(ZOMBIE_VILLAGER_HEAD) };

}