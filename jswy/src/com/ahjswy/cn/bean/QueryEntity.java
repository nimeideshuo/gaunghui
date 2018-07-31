package com.ahjswy.cn.bean;

public class QueryEntity {
	private int index;
	private int length;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return "QueryEntity [index=" + index + ", length=" + length + "]";
	}

}
