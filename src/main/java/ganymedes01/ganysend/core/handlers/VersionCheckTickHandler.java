package ganymedes01.ganysend.core.handlers;

import ganymedes01.ganysend.core.utils.VersionHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.Type;

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