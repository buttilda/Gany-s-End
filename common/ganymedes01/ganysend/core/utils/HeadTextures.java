package ganymedes01.ganysend.core.utils;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;

public class HeadTextures {

	public static final ResourceLocation ENDERMAN_HEAD = Utils.getResource("textures/entity/enderman/enderman.png");
	public static final ResourceLocation ENDERMAN_EYES = Utils.getResource("textures/entity/enderman/enderman_eyes.png");
	public static final ResourceLocation BLAZE_HEAD = Utils.getResource("textures/entity/blaze.png");
	public static final ResourceLocation PIGMAN_HEAD = Utils.getResource("textures/entity/zombie_pigman.png");
	public static final ResourceLocation SPIDER_HEAD = Utils.getResource("textures/entity/spider/spider.png");
	public static final ResourceLocation CAVE_SPIDER_HEAD = Utils.getResource("textures/entity/spider/cave_spider.png");
	public static final ResourceLocation PIG_HEAD = Utils.getResource("textures/entity/pig/pig.png");
	public static final ResourceLocation COW_HEAD = Utils.getResource("textures/entity/cow/cow.png");
	public static final ResourceLocation MOOSHROOM_HEAD = Utils.getResource("textures/entity/cow/mooshroom.png");
	public static final ResourceLocation SHEEP_HEAD = Utils.getResource("textures/entity/sheep/sheep.png");
	public static final ResourceLocation SHEEP_FUR_HEAD = Utils.getResource("textures/entity/sheep/sheep_fur.png");
	public static final ResourceLocation WOLF_HEAD = Utils.getResource("textures/entity/wolf/wolf.png");
	public static final ResourceLocation VILLAGER_HEAD = Utils.getResource("textures/entity/villager/villager.png");
	public static final ResourceLocation CHICKEN_HEAD = Utils.getResource("textures/entity/chicken.png");
	public static final ResourceLocation WITCH_HEAD = Utils.getResource("textures/entity/witch.png");
	public static final ResourceLocation ZOMBIE_VILLAGER_HEAD = Utils.getResource("textures/entity/zombie/zombie_villager.png");
	public static final ResourceLocation IRON_GOLEM_HEAD = Utils.getResource("textures/entity/iron_golem.png");

	public static final ResourceLocation[] headTextures = new ResourceLocation[] { BLAZE_HEAD, ENDERMAN_HEAD, PIGMAN_HEAD, null, SPIDER_HEAD, CAVE_SPIDER_HEAD, PIG_HEAD, COW_HEAD, MOOSHROOM_HEAD, SHEEP_HEAD, WOLF_HEAD, VILLAGER_HEAD, CHICKEN_HEAD, WITCH_HEAD, ZOMBIE_VILLAGER_HEAD, IRON_GOLEM_HEAD };

	public static final ResourceLocation getPlayerSkin(String playerName) {
		if (playerName != null && playerName.length() > 0) {
			ResourceLocation resourcelocation = AbstractClientPlayer.getLocationSkull(playerName);
			AbstractClientPlayer.getDownloadImageSkin(resourcelocation, playerName);
			return resourcelocation;
		} else
			return AbstractClientPlayer.locationStevePng;
	}

	public static final ResourceLocation getHeadTexture(int headType, String playerName) {
		if (headType == 3)
			return getPlayerSkin(playerName);
		else
			return headTextures[headType];
	}

	public static final ResourceLocation getSecondTexture(int headType) {
		switch (headType) {
			case 1:
				return ENDERMAN_EYES;
			case 9:
				return SHEEP_FUR_HEAD;
			default:
				return null;
		}
	}
}
