package me.virizion.corpses.nms;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface Corpses {
	
	public void spawnCorpse(Player p, Inventory items);
	
	public void removeCorpse(CorpseData data);
	
	public int getNextEntityId();
	
	public List<CorpseData> getAllCorpses();
	
	public void registerPacketListener(Player p);
	
	public interface CorpseData {
		
		public void resendCorpseToEveryone();
		
		public void resendCorpseToPlayer(Player p);
		
		public void destroyCorpseFromEveryone();
		
		public void destroyCorpseFromPlayer(Player p);

		public boolean mapContainsPlayer(Player p);

		public void setCanSee(Player p, boolean b);

		public boolean canSee(Player p);

		public Set<Player> getPlayersWhoSee();

		public void removeAllFromMap(Collection<Player> toRemove);

		public Location getTrueLocation();
		
		public Location getOrigLocation();

		public int getTicksLeft();
		
		public void setTicksLeft(int ticksLeft);
		
		public void tickPlayerLater(int ticks, Player p);
		
		public int getPlayerTicksLeft(Player p);
		
		public boolean isTickingPlayer(Player p);
		
		public void stopTickingPlayer(Player p);
		
		public Set<Player> getPlayersTicked();
		
		public int getEntityId();
		
		public Inventory getLootInventory();
		
	}
}
