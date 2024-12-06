package me.mchiappinam.pdghtodayzh;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class Main extends JavaPlugin implements Listener {
	final HashMap<String, Integer> taskIDs = new HashMap<String, Integer>();

	  public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	    getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		getServer().getConsoleSender().sendMessage("§3[PDGHToDayZH] §2ativado - Plugin by: mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHToDayZH] §2Acesse: http://pdgh.com.br/");
	  }

	  public void onDisable() {
		getServer().getConsoleSender().sendMessage("§3[PDGHToDayZH] §2desativado - Plugin by: mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHToDayZH] §2Acesse: http://pdgh.com.br/");
	  }
		
	public void startTask(final Player p) {
		taskIDs.put(p.getName(), getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				if(p.isOnline()) {
					sendDayZHardcore(p);
					getServer().broadcastMessage("§a"+p.getName()+" está usando o cliente AntiHack PDGH.");
				}
			}
		}, 7*20L));
	}
	
	public void cancelTask(Player p) {
		Bukkit.getScheduler().cancelTask(taskIDs.get(p.getName()));
	}
	  
	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent e) {
		startTask(e.getPlayer());
		getServer().broadcastMessage("§e"+e.getPlayer().getName()+" entrou no servidor. Iniciando a verificação do cliente AntiHack PDGH...");
	    Location loc1 = new Location(getServer().getWorld("world"), 1071.5, 71, -840.5);
        loc1.setPitch(0);
        loc1.setYaw(0);
		e.getPlayer().teleport(loc1);
		e.getPlayer().sendMessage("§bTeleportado para o centro.");
	}
	  
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		cancelTask(e.getPlayer());
		//getServer().broadcastMessage("§c"+e.getPlayer().getName()+" desconectou-se antes do término da verificação do cliente AntiHack PDGH.");
	}
		
	@EventHandler
	public void onPlayerKick(PlayerKickEvent e) {
		cancelTask(e.getPlayer());
		getServer().broadcastMessage("§4"+e.getPlayer().getName()+" provavelmente está sem o cliente AntiHack PDGH.");
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent e) {
		if(e.getPlayer().isOp()) {
			e.getPlayer().sendMessage("§aVocê tem permissões e pode QUEBRAR BLOCOS.");
		}else{
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPlace(BlockPlaceEvent e) {
		if(e.getPlayer().isOp()) {
			e.getPlayer().sendMessage("§aVocê tem permissões e pode COLOCAR BLOCOS.");
		}else{
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	private void onPCmd(PlayerCommandPreprocessEvent e) {
		if(e.getPlayer().isOp()) {
			e.getPlayer().sendMessage("§aVocê tem permissões e pode DIGITAR COMANDOS.");
		}else{
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onHungerChange(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(e.getPlayer().isOp()) {
			e.getPlayer().sendMessage("§aVocê tem permissões e pode INTERAGIR.");
		}else{
			e.setCancelled(true);
		}
	}

	public void sendDayZHardcore(Player p) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("dayzhardcore");
        p.sendPluginMessage(this, "BungeeCord", out.toByteArray());
	}
	  
}