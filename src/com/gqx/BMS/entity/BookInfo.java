package com.gqx.BMS.entity;

/**
 * @author GQX 图书
 */

public class BookInfo {

	private int bookId; // 图书编号,PRI K
	
	private int pNo; // 图书出版社编号,FK(book_publish pno)

	private String bookName; // 图书书名

	private String bookAuthor; // 图书作者

	private int bookTotalInventory; // 图示总库存

	private int bookNowInventory; // 图书现库存
	
	private String bookLocation; // 图书存放位置

	private String createTime;

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	
	public int getpNo() {
		return pNo;
	}

	public void setpNo(int pNo) {
		this.pNo = pNo;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public int getBookTotalInventory() {
		return bookTotalInventory;
	}

	public void setBookTotalInventory(int bookTotalInventory) {
		this.bookTotalInventory = bookTotalInventory;
	}

	public int getBookNowInventory() {
		return bookNowInventory;
	}

	public void setBookNowInventory(int bookNowInventory) {
		this.bookNowInventory = bookNowInventory;
	}

	public String getBookLocation() {
		return bookLocation;
	}

	public void setBookLocation(String bookLocation) {
		this.bookLocation = bookLocation;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
