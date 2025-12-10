package com.example.demo.modelo;

public class Rol {
	
	private int id;
	private Permission permission;
	private String description;
	
	
	public Rol(int id, Permission permission, String description) {
		this.id = id;
		this.permission = permission;
		this.description = description;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	
	public Permission getPermission() {
		return permission;
	}


	public void setPermission(Permission permission) {
		this.permission = permission;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
