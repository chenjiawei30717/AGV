package com.dear.bean;

import java.sql.Timestamp;

public class recordDetails {
	private String agvid;
	private Timestamp otime;
	private String carid;
	public String getAgvid() {
		return agvid;
	}
	public void setAgvid(String agvid) {
		this.agvid = agvid;
	}
	public Timestamp getOtime() {
		return otime;
	}
	public void setOtime(Timestamp otime) {
		this.otime = otime;
	}
	public String getCarid() {
		return carid;
	}
	public void setCarid(String carid) {
		this.carid = carid;
	}
	@Override
	public String toString() {
		return "recordDetails [agvid=" + agvid + ", otime=" + otime + ", carid=" + carid + "]";
	}
	
}
