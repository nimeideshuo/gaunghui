package com.ahjswy.cn.print;

public class BTPrinter {
	public String address;
	public String name;

	public BTPrinter(String name, String address) {
		super();
		this.name = name;
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(BTPrinter btprinter) {
		return this.address.equals(btprinter.getAddress());
	}

	public String toString() {
		return this.name + "\n" + this.address;
	}

}
