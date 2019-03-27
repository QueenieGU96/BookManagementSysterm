package com.gqx.BMS.entity;

/**
 * @author GQX 借阅情况
 */

public class BorrowDetail {

	private int borrowId; // 借阅书单序号,PRI K
	private int bookId; // 借阅图书编号,FK(book_info bookId)
	private String bookName; // 借阅书名
	private String studentId; // 借阅对应学生学号
	private String borrowTime; // 借阅时间
	private String returnTime; // 归还时间
	private boolean returnCondition; // 是否归还
	private boolean returnIfovertime; // 是否超时归还

	public int getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(int borrowId) {
		this.borrowId = borrowId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getBorrowTime() {
		return borrowTime;
	}

	public void setBorrowTime(String borrowTime) {
		this.borrowTime = borrowTime;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public Boolean getReturnCondition() {
		return returnCondition;
	}

	public void setReturnCondition(Boolean returnCondition) {
		this.returnCondition = returnCondition;
	}

	public Boolean getReturnIfovertime() {
		return returnIfovertime;
	}

	public void setReturnIfovertime(Boolean returnIfovertime) {
		this.returnIfovertime = returnIfovertime;
	}

}
