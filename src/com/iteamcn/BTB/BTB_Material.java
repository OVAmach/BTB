package com.iteamcn.BTB;

import java.util.Arrays;

import org.bukkit.Material;

public class BTB_Material {
	private Material mat;
	private double[] chance;
	public BTB_Material(Material mat){
		this.mat=mat;
		chance=new double[BTB.getLevel().size()+1];
		for(int i=0;i<chance.length;i++){
			chance[i]=-1;
		}
	}
	public void setMaterial(Material mat){
		this.mat=mat;
	}
	public Material getMaterial(){
		return this.mat;
	}
	public void setChance(double[] chance){
		this.chance=chance;
	}
	public void setChance(double chance,int level){
		if(level<0||level>this.chance.length-1) return;
		this.chance[level]=chance;
	}
	public double[] getChance(){
		return this.chance;
	}
	public double getChance(int level){
		if(level<=0||level>this.chance.length) return -1;
		return this.chance[level];
	}
	@Override
	public String toString() {
		return "BTB_Material [mat=" + mat + ", chance="
				+ Arrays.toString(chance) + "]";
	}
	
}
