package com.iteamcn.BTB.Listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.iteamcn.BTB.Duang;
import com.iteamcn.BTB.Intensify;

public class PlayerInteractListener implements Listener{
	@EventHandler(priority = EventPriority.NORMAL,ignoreCancelled=false)
	public void openIntensify(PlayerInteractEvent event){
		Block block = event.getClickedBlock();
		Player player = event.getPlayer();
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(player.hasPermission("btb.use")){
				if(block.getType().equals(Material.WORKBENCH)){
					if(block.getRelative(0, 1, 0).getType().equals(Material.ANVIL)){
						if(player.isSneaking()){
							//player.sendMessage("Open");
							Intensify.open(player);
							event.setCancelled(true);
						}
					}
				}
			}
		}
		//Duang.checkDuang(player);
		//Bukkit.broadcastMessage(event.useItemInHand().toString());
		//Bukkit.broadcastMessage("click");
	}
}
