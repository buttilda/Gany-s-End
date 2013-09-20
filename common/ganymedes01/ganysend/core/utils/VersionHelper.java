package ganymedes01.ganysend.core.utils;

public class VersionHelper implements Runnable {

	private static VersionHelper instance = new VersionHelper();

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	public static void execute() {
		new Thread(instance).start();
	}
}
