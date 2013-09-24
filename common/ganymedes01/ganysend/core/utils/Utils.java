package ganymedes01.ganysend.core.utils;

import ganymedes01.ganysend.lib.Reference;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

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
		return new Color(R, G, B).getRGB() & 0x00ffffff;
	}

	public static final ResourceLocation getResource(String path) {
		return new ResourceLocation(path);
	}

	public static final ArrayList<Integer> getRandomizedList(int min, int max) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = min; i < max; i++)
			list.add(i);
		Collections.shuffle(list);
		return list;
	}

	public static final String CHAT_COLOUR_BLACK = "¤0";
	public static final String CHAT_COLOUR_DARKBLUE = "¤1";
	public static final String CHAT_COLOUR_DARKGREEN = "¤2";
	public static final String CHAT_COLOUR_DARKAQUA = "¤3";
	public static final String CHAT_COLOUR_DARKRED = "¤4";
	public static final String CHAT_COLOUR_DARKPURPLE = "¤5";
	public static final String CHAT_COLOUR_GOLD = "¤6";
	public static final String CHAT_COLOUR_GREY = "¤7";
	public static final String CHAT_COLOUR_DARKGREY = "¤8";
	public static final String CHAT_COLOUR_BLUE = "¤9";
	public static final String CHAT_COLOUR_GREEN = "¤a";
	public static final String CHAT_COLOUR_AQUA = "¤b";
	public static final String CHAT_COLOUR_RED = "¤c";
	public static final String CHAT_COLOUR_PUEPLE = "¤d";
	public static final String CHAT_COLOUR_YELLOW = "¤e";
	public static final String CHAT_COLOUR_WHITE = "¤f";

	// Head textures
	public static final String ENDERMAN_HEAD = "textures/entity/enderman/enderman.png";
	public static final String ENDERMAN_EYES = "textures/entity/enderman/enderman_eyes.png";
	public static final String BLAZE_HEAD = "textures/entity/blaze.png";
	public static final String PIGMAN_HEAD = "textures/entity/zombie_pigman.png";
	public static final String SPIDER_HEAD = "textures/entity/spider/spider.png";
	public static final String CAVE_SPIDER_HEAD = "textures/entity/spider/cave_spider.png";
	public static final String PIG_HEAD = "textures/entity/pig/pig.png";
	public static final String COW_HEAD = "textures/entity/cow/cow.png";
	public static final String MOOSHROOM_HEAD = "textures/entity/cow/mooshroom.png";
	public static final String SHEEP_HEAD = "textures/entity/sheep/sheep.png";
	public static final String SHEEP_FUR_HEAD = "textures/entity/sheep/sheep_fur.png";
	public static final String WOLF_HEAD = "textures/entity/wolf/wolf.png";
	public static final String VILLAGER_HEAD = "textures/entity/villager/villager.png";
	public static final String CHICKEN_HEAD = "textures/entity/chicken.png";
	public static final String WITCH_HEAD = "textures/entity/witch.png";
	public static final String ZOMBIE_VILLAGER_HEAD = "textures/entity/zombie/zombie_villager.png";
	public static final String IRON_GOLEM_HEAD = "textures/entity/iron_golem.png";

	public static final ResourceLocation[] headTextures = new ResourceLocation[] { getResource(BLAZE_HEAD), getResource(ENDERMAN_HEAD), getResource(PIGMAN_HEAD), getResource("NOT USED: PLAYER HEAD"), getResource(SPIDER_HEAD), getResource(CAVE_SPIDER_HEAD), getResource(PIG_HEAD),
	getResource(COW_HEAD), getResource(MOOSHROOM_HEAD), getResource(SHEEP_HEAD), getResource(WOLF_HEAD), getResource(VILLAGER_HEAD), getResource(CHICKEN_HEAD), getResource(WITCH_HEAD), getResource(ZOMBIE_VILLAGER_HEAD), getResource(IRON_GOLEM_HEAD) };

}