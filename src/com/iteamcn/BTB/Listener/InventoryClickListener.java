package com.iteamcn.BTB.Listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.iteamcn.BTB.Intensify;
import com.iteamcn.BTB.InvSet;

public class InventoryClickListener implements Listener{
	@EventHandler(priority = EventPriority.NORMAL,ignoreCancelled=true)
	public void onIntensifying(InventoryClickEvent event){
		if(event.getWhoClicked() instanceof Player){
			Inventory inv = event.getInventory();
			//event.getWhoClicked().sendMessage("Click" + event.getRawSlot() + ":" +inv.getItem(event.getRawSlot()).getTypeId() + " "  +inv.getItem(event.getRawSlot()).getAmount());
			if(inv.getTitle().equalsIgnoreCase("强化面板")){
				if(event.getRawSlot()==1|event.getRawSlot()==7){
					event.setCancelled(true);
					Intensify.update(inv);
				}else if(event.getRawSlot()==8){
					event.setCancelled(true);
					Intensify.ItemIntensify(event.getWhoClicked(),inv);
				}
				
				ItemStack eq = inv.getItem(0);
				ItemStack[] mat=new ItemStack[5];
				for(int i=0;i<5;i++){
					mat[i]=inv.getItem(2+i);
				}
				if(event.getSlot()==0){
					eq = event.getCursor();
				}
				if(event.getSlot()==2){
					mat[0] = event.getCursor();
				}
				if(event.getSlot()==3){
					mat[1] = event.getCursor();
				}
				if(event.getSlot()==4){
					mat[2] = event.getCursor();
				}
				if(event.getSlot()==5){
					mat[3] = event.getCursor();
				}
				if(event.getSlot()==6){
					mat[4] = event.getCursor();
				}
				InvSet invSet=new InvSet(eq, mat);
				List<String> lore=new ArrayList<String>();
				int check=Intensify.check(invSet);
				if(check!=0){
					lore=Intensify.getCheckCodeString(check);
				}else{
					double chance = Intensify.getChance(invSet);
					lore.add("§e成功概率:"+chance*100+"%");
				}
				ItemStack s8 = new ItemStack(Material.ANVIL,1);
				ItemMeta s8Meta = s8.getItemMeta();
				s8Meta.setLore(lore);
				s8Meta.setDisplayName("§b强化");
				s8.setItemMeta(s8Meta);
				inv.setItem(8, s8);
			}
		}

	}
}
