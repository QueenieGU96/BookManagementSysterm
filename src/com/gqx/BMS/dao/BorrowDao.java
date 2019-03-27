package com.gqx.BMS.dao;

/**
 * @author GQX 借阅操作
 * getRecordList 查询
 * returnBookDetail 归还操作，归还时间限制10min
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gqx.BMS.entity.BorrowDetail;
import com.gqx.BMS.util.Databaseconnection;

public class BorrowDao {

	/*调用实体类BorrowDetail，参数值bookId、studentId，返回集合<BorrowDetail>*/
	public List<BorrowDetail> getRecordList(String bookName, String studentId) {
		List<BorrowDetail> BorrowDetails = new ArrayList<BorrowDetail>();
		Connection conn = null;
		try {
			conn = Databaseconnection.getConnection();
			Statement s = conn.createStatement();
			String sql = "select borrow_id,book_id,book_name,student_id,borrow_time,return_time,"
					+ "return_condition,return_ifovertime from borrow_detail";
			ResultSet r;
			if (bookName != null && !"".equals(bookName)) {
				sql += " where book_name like '%" + bookName + "%'";
			}
			if (studentId != null && !"".equals(studentId)) {
				sql += " where student_id = '" + studentId + "'";
			}
			sql += " order by return_time desc";      // 以create_time降序排序
			r = s.executeQuery(sql);
			while (r.next()) {
				BorrowDetail detail = new BorrowDetail();
				detail.setBorrowId(r.getInt(1));
				detail.setBookId(r.getInt(2));
				detail.setBookName(r.getString(3));
				detail.setStudentId(r.getString(4));
				detail.setBorrowTime(r.getString(5));
				detail.setReturnTime(r.getString(6));
				detail.setReturnCondition(r.getBoolean(7));
				detail.setReturnIfovertime(r.getBoolean(8));
				BorrowDetails.add(detail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return BorrowDetails;
	}

	
	/*归还书籍操作，更新表book_info & borrow_detail*/
	public int returnBookDetail(Integer borrowId, Integer bookId) {
		int result = 0;
		try {
			Connection conn = Databaseconnection.getConnection();
			PreparedStatement ps = conn                                       // 现库存+1
					.prepareStatement("update book_info set book_now_inventory = book_now_inventory+? where book_id=?");
			ps.setInt(1, 1);
			ps.setInt(2, bookId);
			result = ps.executeUpdate();

			Statement s = conn.createStatement();
			String sql = "select borrow_time from borrow_detail where book_id="
					+ bookId;

			ResultSet r = s.executeQuery(sql);         // 查询borrow_time结果                                        
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String borrowTime = null;
			Date endDate = new Date();
			if (r.next()) {
				borrowTime = r.getString(1);                              
			} else {
				borrowTime = sdf.format(endDate);                             // 若没有结果，添加borrow_time
			}
			Date beginDate = sdf.parse(borrowTime);                           // 设borrow_time为起始时间
			// aa值为时间差，单位为分钟（毫秒/(1000 * 60)）
			int aa = (int) ((endDate.getTime() - beginDate.getTime()) / (1000 * 60));
			boolean ifovertime = false;                                       // aa>10,即10分钟以上超时
			if (aa > 10) {
				ifovertime = true;
			}
			ps = conn                                                         // return_time为结束时间，更新归还、超时状态
					.prepareStatement("update borrow_detail set return_time=?,return_condition=?,return_ifovertime=? where borrow_id=?");
			ps.setString(1, sdf.format(endDate));
			ps.setBoolean(2, true);
			ps.setBoolean(3, ifovertime);
			ps.setInt(4, borrowId);
			result = ps.executeUpdate();                                      // result值记录成功操作update的条数
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {

		}
		return result;
	}

}
