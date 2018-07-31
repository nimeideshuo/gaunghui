package com.ahjswy.cn.model;

import java.io.Serializable;

public class Warehouse implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}