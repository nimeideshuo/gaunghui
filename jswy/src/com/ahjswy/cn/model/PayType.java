package com.ahjswy.cn.model;

import java.io.Serializable;

public class PayType implements Serializable {
	private static final long serialVersionUID = 1L;
	public String id;
	public String name;

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setId(String paramString) {
		this.id = paramString;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	@Override
	public String toString() {
		return "PayType [id=" + id + ", name=" + name + "]";
	}
	
}