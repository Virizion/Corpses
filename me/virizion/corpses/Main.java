package me.virizion.corpses;

import java.util.List;

import me.virizion.corpses.cmds.SpawnCorpse;
import me.virizion.corpses.listeners.PlayerChangedWorld;
import me.virizion.corpses.listeners.PlayerDeath;
import me.virizion.corpses.listeners.PlayerJoin;
import me.virizion.corpses.listeners.PlayerRespawn;
import me.virizion.corpses.nms.Corpses;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

public class Main extends JavaPlugin {

	private static Main plugin;

	public Corpses corpses;
	public boolean cont = true;

	public void onEnable() {
		plugin = this;
		saveDefaultConfig();
		getServer().getLogger().info("Loading corpses creator...");
		loadCorpsesCreator();
		ConfigData.load();
		if (!cont) {
			return;
		}
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerJoin(), this);
		pm.registerEvents(new PlayerRespawn(), this);
		pm.registerEvents(new PlayerChangedWorld(), this);
		pm.registerEvents(new PlayerDeath(), this);
		getCommand("spawncorpse").setExecutor(new SpawnCorpse());
	}

	private String getServerVersion() {
		return getServer().getClass().getName().split("\\.")[3];
	}

	private boolean isVersionSupported(String version) {
		try {
			Class.forName("me.virizion.corpses.nms.nmsclasses.NMSCorpses_"
					+ version, false, getClassLoader());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private void loadCorpsesCreator() {
		String version = getServerVersion();
		if (isVersionSupported(version)) {
			try {
				Class<?> subClass = Class
						.forName("me.virizion.corpses.nms.nmsclasses.NMSCorpses_"
								+ version);
				corpses = (Corpses) subClass.getConstructor().newInstance();
				getServer().getLogger().info("Corpses creator loaded.");
			} catch (Exception e) {
				getServer().getLogger().severe(
						"================================");
				getServer()
						.getLogger()
						.severe("There was a problem with loading the corpses creator!");
				getServer().getLogger().severe(
						"================================");
				cont = false;
				getServer().getPluginManager().disablePlugin(this);
			}
		} else {
			getServer().getLogger().severe("================================");
			getServer().getLogger().severe("Server version is not supported!");
			getServer().getLogger().severe("You are running " + version + ".");
			getServer().getLogger().severe(
					"The supported versions are: "
							+ getAppendedVersionsSupported() + ".");
			getServer().getLogger().severe("================================");
			cont = false;
			getServer().getPluginManager().disablePlugin(this);
		}
	}

	private String getAppendedVersionsSupported() {
		try {
			StringBuilder sb = new StringBuilder();
			List<ClassInfo> classes = ClassPath.from(getClassLoader())
					.getTopLevelClasses("me.virizion.corpses.nms.nmsclasses")
					.asList();
			for (int i = 0; i < classes.size(); i++) {
				if (classes.get(i).getSimpleName().startsWith("NMSCorpses_")) {
					sb.append(classes.get(i).getSimpleName()
							.replace("NMSCorpses_", ""));
					if (i != classes.size() - 1) {
						sb.append(", ");
					}
				}
			}
			return sb.toString();
		} catch (Exception e) {
			return "Error finding versions";
		}
	}

	public static Main getPlugin() {
		return plugin;
	}
}
