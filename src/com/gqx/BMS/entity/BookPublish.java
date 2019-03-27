package com.gqx.BMS.entity;

/**
 * @author GQX 出版社
 *
 */
public class BookPublish {

	private int pNo;       // 出版社编号,PRI K
	private String pName;  // 出版社名
	private String pLocation; // 出版社地址
	private String pPhone;    // 出版社电话
	
	public int getpNo() {
		return pNo;
	}
	public void setpNo(int pNo) {
		this.pNo = pNo;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getpLocation() {
		return pLocation;
	}
	public void setpLocation(String pLocation) {
		this.pLocation = pLocation;
	}
	public String getpPhone() {
		return pPhone;
	}
	public void setpPhone(String pPhone) {
		this.pPhone = pPhone;
	}
	
	
	
}
