package com.gqx.BMS.service;

import java.util.List;
import com.gqx.BMS.dao.PublishDao;
import com.gqx.BMS.entity.BookPublish;

public class PublishService {

	PublishDao publishDao = new PublishDao();
	
	/*调用类PublishDao*/
	
	public List<BookPublish> getPublishList(String pName) {
		List<BookPublish> bookPublishs = publishDao.getPublishList(pName);

		return bookPublishs;
	}

	public Integer deleteBookPublish(Integer pNo) {
		int x = publishDao.deleteBookPublish(pNo);

		return x;
	}

	public void addBookPublish(String t1, String t2, String t3, String t4) {
		publishDao.addBookPublish(t1, t2, t3, t4);

	}

	public BookPublish findBookPublish(Integer pNo) {
		return publishDao.findBookPublish(pNo);
	}

	public void updateBookPublish(String t1, String t2, String t3, String t4) {

		publishDao.updateBookPublish(t1, t2, t3, t4);
	}
	
}
