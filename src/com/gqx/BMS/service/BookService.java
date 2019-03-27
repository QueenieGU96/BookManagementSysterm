package com.gqx.BMS.service;

import java.util.List;
import com.gqx.BMS.dao.BookDao;
import com.gqx.BMS.entity.BookInfo;

public class BookService {

	BookDao bookDao = new BookDao();

	/*调用类BookDao*/
	
	public List<BookInfo> getBookList(String bookName) {
		List<BookInfo> bookInfos = bookDao.getBookList(bookName);

		return bookInfos;
	}

	public Integer deleteBookInfo(Integer bookId) {
		int x = bookDao.deleteBookInfo(bookId);

		return x;
	}

	public void addBookInfo(String t1, String t2, String t3, String t4,
			String t5, String t6, String t7,String now) {
		bookDao.addBookInfo(t1, t2, t3, t4, t5, t6,t7,now);

	}

	public BookInfo findBookInfo(Integer bookId) {
		return bookDao.findBookInfo(bookId);
	}

	public void updateBookInfo(String t1, String t2, String t3, String t4,
			String t5, String t6,String t7,String now) {

		bookDao.updateBookInfo(t1, t2, t3, t4, t5, t6, t7,now);
	}

	public Integer borrowBookInfo(Integer bookId, String studentId,
			String bookName) {

		return bookDao.borrowBookInfo(bookId, studentId, bookName);
	}

}
