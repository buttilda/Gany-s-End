package ganymedes01.ganysend.core.utils;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class VersionHelper implements Runnable {

	private static VersionHelper instance = new VersionHelper();
	private static final String REMOTE_VERSION_FILE = "https://raw.github.com/ganymedes01/Gany-s-End/master/Version.txt";
	public static Properties remoteVersionProperties = new Properties();

	public static final byte UNINITIALIZED = 0;
	public static final byte CURRENT = 1;
	public static final byte OUTDATED = 2;
	public static final byte ERROR = 3;
	public static final byte FINAL_ERROR = 4;
	public static final byte MC_VERSION_NOT_FOUND = 5;

	private static byte result = UNINITIALIZED;
	public static String remoteVersion = null;
	public static String remoteUpdateLocation = null;

	public static void checkVersion() {
		InputStream remoteVersionRepoStream = null;
		result = UNINITIALIZED;

		try {
			URL remoteVersionURL = new URL(REMOTE_VERSION_FILE);
			remoteVersionRepoStream = remoteVersionURL.openStream();
			byte[] bytes = new byte[50];
			remoteVersionRepoStream.read(bytes);
			System.out.println("version: " + bytes.toString());

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

	@Override
	public void run() {
		int count = 0;

		try {
			while (count < 10 - 1 && (result == UNINITIALIZED || result == ERROR)) {

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
