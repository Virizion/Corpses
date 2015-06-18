package me.virizion.corpses.listeners;

import me.virizion.corpses.Main;
import me.virizion.corpses.nms.Corpses.CorpseData;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn implements Listener {

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		for (CorpseData data : Main.getPlugin().corpses.getAllCorpses()) {
			if (data.getOrigLocation().getWorld()
					.equals(e.getRespawnLocation().getWorld())) {
				data.setCanSee(e.getPlayer(), false);
				data.tickPlayerLater(35, e.getPlayer());
			}
		}
	}

}
