package com.iteamcn.BTB.Listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.iteamcn.BTB.BTB;
import com.iteamcn.BTB.Intensify;

public class EntityDamageByEntityListener implements Listener {
	
	@EventHandler(priority = EventPriority.NORMAL,ignoreCancelled=true)
	public void hit(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof Player){
			Player player = Bukkit.getPlayer(event.getDamager().getUniqueId());
			ItemStack t =player.getItemInHand();
			/*if(Intensify.getPropertyType(t).equalsIgnoreCase("attack")){
				double dmg = event.getDamage();
				double p = Intensify.getProperty(t) ;
				double fin = event.getFinalDamage();
				double tmp = fin/dmg;
				if(tmp<=0|tmp>=1){
					tmp=1;
				}
				if(fin!=dmg){
					event.setDamage(p+(p)/3+dmg);
				}else{
					event.setDamage(p+dmg);
				}
				player.sendMessage("≥ı º…À∫¶:"+dmg+"‘≠ º…À∫¶:"+(p+dmg)+"≤π≥•∫Û…À∫¶:"+event.getDamage());

			}*/
			//Bukkit.broadcastMessage(BTB.getNBTManager().read(t).toString());
		}
		if(event.getDamager() instanceof Projectile){
			Projectile projectile = (Projectile)event.getDamager();
			if(projectile.getShooter() instanceof Entity){
				if(projectile.hasMetadata("BTB_DMG")){
					double dmg = event.getDamage();
					double p = projectile.getMetadata("BTB_DMG").get(0).asDouble();
					double fin = event.getFinalDamage();
					double tmp = fin/dmg;
					if(tmp<=0|tmp>=1){
						tmp=1;
					}
					if(fin!=dmg){
						event.setDamage(p+(p/tmp)/3+dmg);
					}else{
						event.setDamage(p+dmg);
					}
				}
			}
		}
		if(event.getEntity() instanceof Player){
			Player player = Bukkit.getPlayer(event.getEntity().getUniqueId());
			if(player!=null){
				double p1 = 0;
				double p2 = 0;
				double p3 = 0;
				double p4 = 0;
				if(Intensify.getPropertyType(player.getEquipment().getHelmet()).equalsIgnoreCase("armor")){
					p1 = Intensify.getProperty(player.getEquipment().getHelmet());
				}
				if(Intensify.getPropertyType(player.getEquipment().getChestplate()).equalsIgnoreCase("armor")){
					p2 = Intensify.getProperty(player.getEquipment().getChestplate());
				}
				if(Intensify.getPropertyType(player.getEquipment().getLeggings()).equalsIgnoreCase("armor")){
					p3 = Intensify.getProperty(player.getEquipment().getLeggings());
				}
				if(Intensify.getPropertyType(player.getEquipment().getBoots()).equalsIgnoreCase("armor")){
					p4 = Intensify.getProperty(player.getEquipment().getBoots());
				}
				double dmg = event.getDamage();
				event.setDamage(dmg*(1d-(p1+p2+p3+p4)/100));
				//player.sendMessage(dmg+" "+event.getDamage());
			}
		}

		
	}
}
