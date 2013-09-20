package ganymedes01.ganysend.core.utils;

import ganymedes01.ganysend.lib.Reference;

import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

public class LogHelper {

	private static Logger endLogger = Logger.getLogger(Reference.MOD_ID.toUpperCase());

	public static void init() {
		endLogger.setParent(FMLLog.getLogger());
	}

	public static void log(Level logLevel, Object object) {
		endLogger.log(logLevel, object.toString());
	}

	public static void warning(Object object) {
		log(Level.WARNING, object.toString());
	}

	public static void info(Object object) {
		log(Level.INFO, object.toString());
	}
}
