package com.iteamcn.BTB;

import java.util.Arrays;

import org.bukkit.Material;

public class BTB_Equip {
	private Material material;
	private String property_Type;
	private double property_Range_Min;
	private double property_Range_Max;
	private int duang_Lvl;
	private int duang_Type;
	private int[] special_Lvl;
	private String[] special_Type;
	public BTB_Equip(Material material){
		this.material=material;
	}
	public void setProperty_Type(String property_Type){
		this.property_Type=property_Type;
	}
	public String getProperty_Type(){
		return this.property_Type;
	}
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	public double getProperty_Range_Min() {
		return property_Range_Min;
	}
	public void setProperty_Range_Min(double property_Range_Min) {
		this.property_Range_Min = property_Range_Min;
	}
	public double getProperty_Range_Max() {
		return property_Range_Max;
	}
	public void setProperty_Range_Max(double property_Range_Max) {
		this.property_Range_Max = property_Range_Max;
	}
	public void setProperty_Range(double property_Range_Min,double property_Range_Max){
		this.property_Range_Min=property_Range_Min;
		this.property_Range_Max=property_Range_Max;
	}
	public int getDuang_Lvl() {
		return duang_Lvl;
	}
	public void setDuang_Lvl(int duang_Lvl) {
		this.duang_Lvl = duang_Lvl;
	}
	public int getDuang_Type() {
		return duang_Type;
	}
	public void setDuang_Type(int duang_Type) {
		this.duang_Type = duang_Type;
	}
	public int[] getSpecial_Lvl() {
		return special_Lvl;
	}
	public void setSpecial_Lvl(int[] special_Lvl) {
		this.special_Lvl = special_Lvl;
	}
	public String[] getSpecial_Type() {
		return special_Type;
	}
	public void setSpecial_Type(String[] special_Type) {
		this.special_Type = special_Type;
	}
	@Override
	public String toString() {
		return "BTB_Equip [material=" + material + ", property_Type="
				+ property_Type + ", property_Range_Min=" + property_Range_Min
				+ ", property_Range_Max=" + property_Range_Max + ", duang_Lvl="
				+ duang_Lvl + ", duang_Type=" + duang_Type + ", special_Lvl="
				+ Arrays.toString(special_Lvl) + ", special_Type="
				+ Arrays.toString(special_Type) + "]";
	}

}
