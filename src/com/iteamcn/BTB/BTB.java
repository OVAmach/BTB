package com.iteamcn.BTB;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.dpohvar.powernbt.PowerNBT;
import me.dpohvar.powernbt.api.NBTManager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.iteamcn.BTB.Listener.*;

public class BTB extends JavaPlugin {
	private static FileConfiguration config;
	private static Map<Material,BTB_Material> material_Map=new HashMap<Material,BTB_Material>();
	private static Map<Material,BTB_Equip> equip_Map=new HashMap<Material, BTB_Equip>();
	private static Map<String,Object> settings=new HashMap<String,Object>();
	private static Map<String,Object> intensifySettings=new HashMap<String,Object>();
	private static List<Map<?, ?>> level;
	private static ItemStack breakPrice;
	private static BTB btb;
	private static NBTManager NBTmanager = PowerNBT.getApi();
	
	public static NBTManager getNBTManager() {
		return NBTmanager;
	}
	public static FileConfiguration getSelfConfig() {
		return config;
	}
	public static Map<Material, BTB_Equip> getEquip_Map() {
		return equip_Map;
	}
	public static Map<String, Object> getSettings() {
		return settings;
	}
	public static Map<String, Object> getIntensifySettings() {
		return intensifySettings;
	}
	public static BTB getBtb() {
		return btb;
	}
	public void onEnable(){
		getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
		getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
		getServer().getPluginManager().registerEvents(new InventoryDragListener(), this);
		getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
		getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
		getServer().getPluginManager().registerEvents(new EntityShootBowListener(), this);
		getServer().getPluginManager().registerEvents(new InventoryMoveItemListener(), this);
		init();
		btb=this;
		new Duang().runTaskTimer(this, 0L, 5L);
		new Special().runTaskTimer(this,80L,0L);
	}
	public void init(){
		File fconfig=new File(getDataFolder(),"config.yml");
		if (!(fconfig.exists())) {saveDefaultConfig();}
		reloadConfig();
		config = getConfig();
		settings.clear();
		settings.put("debug", config.getBoolean("settings.debug"));
		settings.put("log", config.getBoolean("settings.log")); 
		//强化设置加载
		intensifySettings.clear();
		intensifySettings.put("maxLevel", config.getInt("intensifySettings.maxLevel"));
		intensifySettings.put("maxMaterials", config.getInt("intensifySettings.maxMaterials"));
		intensifySettings.put("duangMode", config.getBoolean("intensifySettings.duangMode"));
		intensifySettings.put("userMaxDuang", config.getInt("intensifySettings.userMaxDuang"));
		intensifySettings.put("breakPriceEnable", config.getBoolean("intensifySettings.breakPriceEnable"));
		
		level = config.getMapList("intensifySettings.level");
		//材料加载
		List<Map<?, ?>> mat = config.getMapList("intensifySettings.material");
		material_Map.clear();
		for(int i=0;i<mat.size();i++){
			BTB_Material temp=new BTB_Material(Material.getMaterial((String)mat.get(i).get("item_Name")));
			double[] chance=new double[level.size()+1];
			for(int j=1;j<chance.length;j++){
				double t=-1;
				if(mat.get(i).containsKey("lvl_"+j+"_Full_Chance")){
					t=(double)mat.get(i).get("lvl_"+j+"_Full_Chance");
				}
				if(t==-1&&mat.get(i).containsKey("lvl_other_Full_Chance")){
					t=(double)mat.get(i).get("lvl_other_Full_Chance");
				}
				chance[j]=t;
			}
			temp.setChance(chance);
			material_Map.put(temp.getMaterial(), temp);
			//Bukkit.broadcastMessage(material_Map.toString());
		}
		//装备加载
		List<Map<?, ?>> equ = config.getMapList("intensifySettings.equip");
		equip_Map.clear();
		for(int i=0;i<equ.size();i++){
			BTB_Equip temp=new BTB_Equip(Material.getMaterial((String)equ.get(i).get("item_Name")));
			temp.setProperty_Type((String)equ.get(i).get("property_Type"));
			if(equ.get(i).containsKey("property_Base")){
				temp.setProperty_Base((double)equ.get(i).get("property_Base"));
			}
			
			String[] t = ((String)equ.get(i).get("property_Range")).split("~");
			temp.setProperty_Range(Double.valueOf(t[0]), Double.valueOf(t[1]));
			temp.setDuang_Lvl((int)equ.get(i).get("duang_Lvl"));
			temp.setDuang_Type((int)equ.get(i).get("duang_Type"));
			ArrayList<String> a = (ArrayList<String>)equ.get(i).get("special_Type");
			temp.setSpecial_Type(a.toArray(new String[a.size()]));
			ArrayList<Integer> b = (ArrayList<Integer>)equ.get(i).get("special_Lvl");
			int[] c=new int[b.size()];
			for(int j=0;j<b.size();j++){
				c[j]=b.get(j);
			}
			temp.setSpecial_Lvl(c);
			equip_Map.put(temp.getMaterial(), temp);
		}
		//Bukkit.broadcastMessage(equip_Map.toString());
		//爆装备奖励
		breakPrice=new ItemStack(Material.getMaterial(config.getString("intensifySettings.breakPrice.item_Name")));
		List<String> tmp=java.util.Arrays.asList(config.getString("intensifySettings.breakPrice.lore").split("\\|"));
		ItemMeta itemMeta = breakPrice.getItemMeta();
		itemMeta.setLore(tmp);
		itemMeta.setDisplayName("§r"+config.getString("intensifySettings.breakPrice.name"));
		breakPrice.setItemMeta(itemMeta);
	}
	public void onDisable(){
	}
	public static Map<Material,BTB_Material> getMaterial_Map(){
		return BTB.material_Map;
	}
	public static List<Map<?, ?>> getLevel(){
		return BTB.level;
	}
	public static ItemStack getBreakPrice() {
		return breakPrice;
	}
	
}
