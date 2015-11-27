package ganymedes01.ganysend.integration;

import java.util.ArrayList;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

import ganymedes01.ganysend.lib.Reference;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class ModIntegrator {

	public static ArrayList<Integration> modIntegrations;

	@SuppressWarnings("rawtypes")
	public static void preInit() {
		modIntegrations = new ArrayList<Integration>();

		try {
			for (ClassInfo clazzInfo : ClassPath.from(ModIntegrator.class.getClassLoader()).getTopLevelClasses("ganymedes01." + Reference.MOD_ID + ".integration")) {
				Class clazz = clazzInfo.load();
				if (clazz != Integration.class && Integration.class.isAssignableFrom(clazz))
					modIntegrations.add((Integration) clazz.newInstance());
			}
		} catch (Exception e) {
		}
	}

	public static void init() {
		for (Integration integration : modIntegrations)
			if (integration.shouldIntegrate())
				integration.init();
	}

	public static void postInit() {
		for (Integration integration : modIntegrations)
			if (integration.shouldIntegrate())
				integration.postInit();
	}
}