package ganymedes01.ganysend.core.handlers;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.Type;
import ganymedes01.ganysend.core.utils.VersionHelper;
import net.minecraft.client.Minecraft;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class VersionCheckTickHandler {

	private boolean sent = false;

	@SubscribeEvent
	public void tickEnd(ClientTickEvent event) {
		if (!sent)
			if (event.type == Type.CLIENT && event.phase == Phase.END)
				if (FMLClientHandler.instance().getClient().currentScreen == null)
					if (VersionHelper.getResult() != VersionHelper.UNINITIALIZED) {
						if (VersionHelper.getResult() == VersionHelper.OUTDATED)
							Minecraft.getMinecraft().thePlayer.addChatMessage(VersionHelper.getResultMessageForClient());
						sent = true;
					}
	}
}