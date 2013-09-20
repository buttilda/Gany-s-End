package ganymedes01.ganysend.core.utils;

import ganymedes01.ganysend.lib.Reference;
import ganymedes01.ganysend.lib.Strings;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class VersionHelper implements Runnable {
	private static VersionHelper instance = new VersionHelper();
	private static final String REMOTE_VERSION_XML_FILE = "https://raw.github.com/ganymedes01/Gany-s-End/master/Version.xml";
	public static Properties remoteVersionProperties = new Properties();

	public static final byte UNINITIALIZED = 0;
	public static final byte CURRENT = 1;
	public static final byte OUTDATED = 2;
	public static final byte ERROR = 3;
	public static final byte FINAL_ERROR = 4;

	private static byte result = UNINITIALIZED;
	public static String remoteVersion = null;
	public static String remoteUpdateLocation = null;

	public static void checkVersion() {
		InputStream remoteVersionRepoStream = null;
		result = UNINITIALIZED;

		try {
			URL remoteVersionURL = new URL(REMOTE_VERSION_XML_FILE);
			remoteVersionRepoStream = remoteVersionURL.openStream();
			remoteVersionProperties.loadFromXML(remoteVersionRepoStream);
			String remoteVersionProperty = remoteVersionProperties.getProperty(Reference.CHANNEL_NAME);

			if (remoteVersionProperty != null) {
				String[] remoteVersionTokens = remoteVersionProperty.split("\\|");
				if (remoteVersionTokens.length >= 3) {
					remoteVersion = remoteVersionTokens[0];
					Reference.LATEST_VERSION = remoteVersionTokens[1];
					remoteUpdateLocation = remoteVersionTokens[2];
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

	public static void logResult() {
		if (result == CURRENT || result == OUTDATED)
			LogHelper.info(getResultMessage());
		else
			LogHelper.warning(getResultMessage());
	}

	public static String getResultMessage() {
		switch (result) {
			case UNINITIALIZED:
				return Strings.VERSION_CHECK_FAIL;
			case CURRENT:
				return Strings.CURRENT_MESSAGE;
			case OUTDATED:
				if (remoteVersion != null && remoteUpdateLocation != null)
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

	public static String getResultMessageForClient() {
		return Utils.CHAT_COLOUR_GREEN + "Gany's End" + Utils.CHAT_COLOUR_WHITE + " is outdated. New version available at: " + Utils.CHAT_COLOUR_DARKGREEN + remoteUpdateLocation;
		// return
		// StatCollector.translateToLocalFormatted(Strings.OUTDATED_MESSAGE,
		// Utils.getColour(0, 255, 255) + Reference.MOD_NAME +
		// Utils.getColour(255, 255, 255), Utils.getColour(0, 255, 255) +
		// VersionHelper.remoteVersion + Utils.getColour(255, 255, 255),
		// Utils.getColour(0, 255, 255) +
		// Loader.instance().getMCVersionString() + Utils.getColour(255, 255,
		// 255), Utils.getColour(0, 255, 255) +
		// VersionHelper.remoteUpdateLocation + Utils.getColour(255, 255, 255));
	}

	public static byte getResult() {
		return result;
	}

	@Override
	public void run() {
		int count = 0;
		LogHelper.info(Strings.VERSION_CHECK_INIT);

		try {
			while (count < 3 - 1 && (result == UNINITIALIZED || result == ERROR)) {
				checkVersion();
				count++;
				logResult();

				if (result == UNINITIALIZED || result == ERROR)
					Thread.sleep(10000);
			}
			if (result == ERROR) {
				result = FINAL_ERROR;
				logResult();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void execute() {

		new Thread(instance).start();
	}
}