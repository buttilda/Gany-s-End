package ganymedes01.ganysend.lib;

import ganymedes01.ganysend.core.utils.Utils;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.Loader;

class Lib {

}

public enum SkullTypes {
	// @formatter:off
	blaze(Strings.MC_PREFIX + "blaze"),
	enderman(Strings.MC_PREFIX + "enderman/enderman"),
	pigman(Strings.MC_PREFIX + "zombie_pigman"),
	player(null, null),
	spider(Strings.MC_PREFIX + "spider/spider"),
	caveSpider(Strings.MC_PREFIX + "spider/cave_spider"),
	pig(Strings.MC_PREFIX + "pig/pig"),
	cow(Strings.MC_PREFIX + "cow/cow"),
	mooshroom(Strings.MC_PREFIX + "cow/mooshroom"),
	sheep(Strings.MC_PREFIX + "sheep/sheep"),
	wolf(Strings.MC_PREFIX + "wolf/wolf"),
	villager(Strings.MC_PREFIX + "villager/villager"),
	chicken(Strings.MC_PREFIX + "chicken"),
	witch(Strings.MC_PREFIX + "witch"),
	zombieVillager(Strings.MC_PREFIX + "zombie/zombie_villager"),
	ironGolem(Strings.MC_PREFIX + "iron_golem"),
	squid(Strings.MC_PREFIX + "squid"),
	wither(Strings.MC_PREFIX + "wither/wither"),
	bunnyDutch(Strings.TF_PREFIX + "bunnydutch", "TwilightForest"),
	penguin(Strings.TF_PREFIX + "penguin", "TwilightForest"),
	bighorn(Strings.TF_PREFIX + "bighorn", "TwilightForest"),
	wildDeer(Strings.TF_PREFIX + "wilddeer", "TwilightForest"),
	wildBoar(Strings.TF_PREFIX + "wildboar", "TwilightForest"),
	redcap(Strings.TF_PREFIX + "redcap", "TwilightForest"),
	druid(Strings.TF_PREFIX + "skeletondruid", "TwilightForest"),
	hedgeSpider(Strings.TF_PREFIX + "hedgespider", "TwilightForest"),
	ghast(Strings.MC_PREFIX + "ghast/ghast"),
	blizz(Strings.TE_PREFIX + "Blizz", "ThermalExpansion"),
	lich(Strings.TF_PREFIX + "twilightlich64", "TwilightForest"),
	mistWolf(Strings.TF_PREFIX + "mistwolf", "TwilightForest"),
	miniGhast(Strings.TF_PREFIX + "towerghast", "TwilightForest"),
	guardGhast(Strings.TF_PREFIX + "towerghast_openeyes", "TwilightForest"),
	kingSpider(Strings.TF_PREFIX + "kingspider", "TwilightForest"),
	kobold(Strings.TF_PREFIX + "kobold", "TwilightForest"),
	slimeBeetle(Strings.TF_PREFIX + "slimebeetle", "TwilightForest"),
	fireBeetle(Strings.TF_PREFIX + "firebeetle", "TwilightForest"),
	pinchBeetle(Strings.TF_PREFIX + "pinchbeetle", "TwilightForest"),
	towerGolem(Strings.TF_PREFIX + "carminitegolem", "TwilightForest"),
	enderDragon(Strings.MC_PREFIX + "enderdragon/dragon"),
	hostileWolf(Strings.MC_PREFIX + "wolf/wolf_angry", "TwilightForest"),
	bunnyBrown(Strings.TF_PREFIX + "bunnybrown", "TwilightForest"),
	bunnyWhite(Strings.TF_PREFIX + "bunnywhite", "TwilightForest"),
	squirrel(Strings.TF_PREFIX + "squirrel2", "TwilightForest");
	// @formatter:on

	private final String mod;
	private final ResourceLocation texture;
	private static final ResourceLocation ENDERMAN_EYES = Utils.getResource(Strings.MC_PREFIX + "enderman/enderman_eyes.png");
	private static final ResourceLocation SHEEP_FUR_HEAD = Utils.getResource(Strings.MC_PREFIX + "sheep/sheep_fur.png");

	SkullTypes(String texture, String mod) {
		this.mod = mod;
		this.texture = Utils.getResource(texture + ".png");
	}

	SkullTypes(String texture) {
		this(texture, null);
	}

	public boolean canShow() {
		return mod == null || Loader.isModLoaded(mod);
	}

	public String getUnlocalisedName() {
		return "item." + Utils.getUnlocalizedName(Strings.ITEM_NEW_SKULL_NAME) + name() + "Head";
	}

	public ResourceLocation getTexture(String name) {
		if (this == player)
			return getPlayerSkin(name);
		else
			return texture;
	}

	public ResourceLocation getSecondTexture() {
		switch (this) {
			case enderman:
				return ENDERMAN_EYES;
			case sheep:
			case bighorn:
				return SHEEP_FUR_HEAD;
			case enderDragon:
				return Utils.getResource(Strings.MC_PREFIX + "enderdragon/dragon_eyes.png");
			case mooshroom:
				return TextureMap.locationBlocksTexture;
			default:
				return null;
		}
	}

	private ResourceLocation getPlayerSkin(String name) {
		if (name != null && name.length() > 0) {
			ResourceLocation texture = AbstractClientPlayer.getLocationSkull(name);
			AbstractClientPlayer.getDownloadImageSkin(texture, name);
			return texture;
		} else
			return AbstractClientPlayer.locationStevePng;
	}
}