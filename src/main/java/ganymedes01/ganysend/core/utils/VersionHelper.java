package ganymedes01.ganysend.core.utils;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class VersionHelper implements Runnable {

	private static VersionHelper instance = new VersionHelper();
	public static Properties remoteVersionProperties = new Properties();

	public static final byte UNINITIALIZED = 0;
	public static final byte CURRENT = 1;
	public static final byte OUTDATED = 2;
	public static final byte ERROR = 3;
	public static final byte FINAL_ERROR = 4;

	private static byte result = UNINITIALIZED;
	public static String remoteVersion = null;
	public static String updateURL = null;

	public static void checkVersion() {
		InputStream remoteVersionRepoStream = null;
		result = UNINITIALIZED;

		try {
			URL remoteVersionURL = new URL(Reference.VERSION_CHECK_FILE);
			remoteVersionRepoStream = remoteVersionURL.openStream();
			remoteVersionProperties.loadFromXML(remoteVersionRepoStream);
			String remoteVersionProperty = remoteVersionProperties.getProperty(Reference.CHANNEL);

			if (remoteVersionProperty != null) {
				String[] tokens = remoteVersionProperty.split("\\|");
				if (tokens.length >= 3) {
					remoteVersion = tokens[0];
					Reference.LATEST_VERSION = tokens[1];
					updateURL = tokens[2];
				} else
					result = ERROR;

				if (remoteVersion != null) {
					int version = Integer.parseInt(remoteVersion);
					if (version <= Reference.RAW_VERSION_NUMBER)
						result = CURRENT;
					else
						result = OUTDATED;
				}
			} else
				result = ERROR;

		} catch (Exception e) {
		} finally {
			if (result == UNINITIALIZED)
				result = ERROR;
			try {
				if (remoteVersionRepoStream != null)
					remoteVersionRepoStream.close();
			} catch (Exception ex) {
			}
		}
	}

	public static String getResultMessage() {
		switch (result) {
			case UNINITIALIZED:
				return Strings.VERSION_CHECK_FAIL;
			case CURRENT:
				return Strings.CURRENT_MESSAGE;
			case OUTDATED:
				if (remoteVersion != null && updateURL != null)
					return Strings.OUTDATED_MESSAGE;
			case ERROR:
				return Strings.VERSION_CHECK_FAIL_CONNECT;
			case FINAL_ERROR:
				return Strings.VERSION_CHECK_FAIL_CONNECT_FINAL;
			default:
				result = ERROR;
				return Strings.VERSION_CHECK_FAIL_CONNECT;
		}
	}

	public static IChatComponent getResultMessageForClient() {
		EnumChatFormatting white = EnumChatFormatting.WHITE;
		EnumChatFormatting green = EnumChatFormatting.GREEN;

		String text = StatCollector.translateToLocal("versioncheck." + Reference.MOD_ID + ".message");
		String download = String.format(StatCollector.translateToLocal("versioncheck." + Reference.MOD_ID + ".download"), Reference.LATEST_VERSION);
		return IChatComponent.Serializer.func_150699_a("[{\"text\":\"" + text + "\"}," + "{\"text\":\" " + white + "[" + green + download + white + "]\"," + "\"color\":\"green\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":" + "{\"text\":\"Click to download the latest version\",\"color\":\"yellow\"}}," + "\"clickEvent\":{\"action\":\"open_url\",\"value\":\"" + updateURL + "\"}}]");
	}

	public static byte getResult() {
		return result;
	}

	@Override
	public void run() {
		int count = 0;

		try {
			while (count < 3 - 1 && (result == UNINITIALIZED || result == ERROR)) {
				checkVersion();
				count++;

				if (result == UNINITIALIZED || result == ERROR)
					Thread.sleep(10000);
			}
			if (result == ERROR)
				result = FINAL_ERROR;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void execute() {

		new Thread(instance).start();
	}
}