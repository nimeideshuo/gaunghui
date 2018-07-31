package com.ahjswy.cn.response;

public class RespServiceInfor {
	public String Info;
	public RespJson Json;
	public boolean Result;

	public static class RespJson {

		public String Status;
		public String Data;
		public String Desc;
		public String Code;
		public String Page;
		public int PageSize;
		public int MaxRow;
		public int PageIndex;
		public String TitleList;
		public String StartTime;
		public double Sum;
		public String DocID;

		@Override
		public String toString() {
			return "RespJson [Status=" + Status + ", Data=" + Data + ", Desc=" + Desc + ", Code=" + Code + ", Page="
					+ Page + ", PageSize=" + PageSize + ", MaxRow=" + MaxRow + ", PageIndex=" + PageIndex
					+ ", StartTime=" + StartTime + ", Sum=" + Sum + ", DocID=" + DocID + "]";
		}

	}

	@Override
	public String toString() {
		return "RespServiceInfor [Info=" + Info + ", Json=" + Json + ", Result=" + Result + "]";
	}

}
