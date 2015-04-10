package ganymedes01.ganysend.lib;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class Reference {

	public static final String MOD_ID = "ganysend";
	public static final String MOD_NAME = "Gany's End";
	public static final String DEPENDENCIES = "required-after:Forge@[10.13.3.1360,);after:EnderStorage";
	public static final String CHANNEL = "GanysEnd";
	public static final String MASTER = "GanysMods";
	public static final String VERSION_NUMBER = "1.8.8";
	public static final int RAW_VERSION_NUMBER = 188;
	public static String LATEST_VERSION;
	public static final String ITEM_BLOCK_TEXTURE_PATH = MOD_ID + ":";
	public static final String ARMOUR_TEXTURE_PATH = ITEM_BLOCK_TEXTURE_PATH + "textures/models/armor/";
	public static final String ENTITY_TEXTURE_PATH = ITEM_BLOCK_TEXTURE_PATH + "textures/entities/";
	public static final String GUI_TEXTURE_PATH = ITEM_BLOCK_TEXTURE_PATH + "textures/gui/container/";

	public static final String GUI_FACTORY_CLASS = "ganymedes01.ganysend.configuration.ConfigGuiFactory";
	public static final String SERVER_PROXY_CLASS = "ganymedes01.ganysend.core.proxy.CommonProxy";
	public static final String CLIENT_PROXY_CLASS = "ganymedes01.ganysend.core.proxy.ClientProxy";
	public static final String VERSION_CHECK_FILE = "https://raw.github.com/ganymedes01/Version-Checks/master/Version.xml";
	public static final String USERS_WITH_CAPES_FILE = "https://raw.github.com/ganymedes01/Version-Checks/master/UsersWithCapes.txt";
}