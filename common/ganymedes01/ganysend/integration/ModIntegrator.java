package ganymedes01.ganysend.integration;

import java.util.ArrayList;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

/**
 * Gany's End
 * 
 * @author ganymedes01
 * 
 */

public class ModIntegrator {

	public static ArrayList<Integration> modIntegrations;

	public static void preInit() {
		modIntegrations = new ArrayList<Integration>();

		try {
			for (ClassInfo clazzInfo : ClassPath.from(ModIntegrator.class.getClassLoader()).getTopLevelClasses(ModIntegrator.class.getPackage().getName())) {
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