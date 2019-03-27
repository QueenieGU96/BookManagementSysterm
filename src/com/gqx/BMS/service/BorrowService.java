package com.gqx.BMS.service;

import java.util.List;

import com.gqx.BMS.dao.BorrowDao;
import com.gqx.BMS.entity.BorrowDetail;

public class BorrowService {

	BorrowDao borrowDao = new BorrowDao();

	/*调用类BorrowDao*/
	
	public List<BorrowDetail> getRecordList(String bookNameStr,String studentIdStr) {
		List<BorrowDetail> borrowInfos = borrowDao.getRecordList(bookNameStr, studentIdStr);
		return borrowInfos;
	}

	public int returnBookDetail(Integer borrowId, Integer bookId) {

		return borrowDao.returnBookDetail(borrowId, bookId);
	}

}
