package com.gqx.BMS.dao;

/**
 * @author GQX 图书操作
 * getBookList 书名模糊查询
 * deleteBookInfo 删
 * addBookInfo 增
 * findBookInfo 书号查询
 * updateBookInfo 修改
 * borrowBookInfo 借阅操作（外键 borrow_detail(book_id)）
 */

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

import com.gqx.BMS.entity.BookInfo;
import com.gqx.BMS.util.Databaseconnection;

public class BookDao {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/*调用实体类BookInfo，参数值bookName，返回查询图书集合<BookInfo> bookInfos*/
	public List<BookInfo> getBookList(String bookName) {
		List<BookInfo> bookInfos = new ArrayList<BookInfo>();
		Connection conn = null;
		try {
			conn = Databaseconnection.getConnection();
			Statement s = conn.createStatement();
			String sql = "select book_id,pno,book_name,book_author,book_total_inventory,book_now_inventory,"
					+ "book_location,create_time from book_info";
			ResultSet r;
			if (bookName != null && !"".equals(bookName)) {               // "".equals(A),即字符串A为null
				sql += " where book_name like '%" + bookName + "%'";      // 实现模糊查询
			}
			sql += " order by create_time desc";                          // 以create_time降序排序
			r = s.executeQuery(sql);
			while (r.next()) {                                            // 查询结果
				BookInfo books = new BookInfo();
				books.setBookId(r.getInt(1));
				books.setpNo(r.getInt(2));
				books.setBookName(r.getString(3));
				books.setBookAuthor(r.getString(4));
				books.setBookTotalInventory(r.getInt(5));
				books.setBookNowInventory(r.getInt(6));
				books.setBookLocation(r.getString(7));
				books.setCreateTime(r.getString(8));
				bookInfos.add(books);
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
		return bookInfos;
	}

	
	/*参数值bookId，返回成功执行删除对应图书信息的条数*/
	public int deleteBookInfo(Integer bookId) {
		Connection conn;
		PreparedStatement ps = null;
		int x = 0;
		try {
			String sql = "delete from book_info where book_id=?";
			conn = Databaseconnection.getConnection();
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, bookId);
				x = ps.executeUpdate();                                    // x值记录成功操作delete条数
				return x;
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally {
				try {
					if (ps != null) {
						ps.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return x;
	}

	
	/*增加图书信息*/
	public void addBookInfo(String t1, String t2, String t3, String t4,
			String t5, String t6, String t7,String now) {
		try {

			Connection conn = Databaseconnection.getConnection();
			PreparedStatement s = conn
					.prepareStatement("select * from book_info where book_id=?");
			s.setInt(1, Integer.valueOf(t1));
			ResultSet r = s.executeQuery();
			if (r.next()) {
				// Java消息提示框，.showMessageDialog即只有一个确认键
				JOptionPane.showMessageDialog(null, "你输入的图书编号已存在，请核实!!", "警告",
						0);
				return;
			}
			int t55 = Integer.valueOf(t5);         // 确保总库存>=现库存>=0
			int t66 = Integer.valueOf(t6);
			if (t55 < 0) {
				JOptionPane.showMessageDialog(null, "你输入的图书总库存不能小于0，请核实!!",
						"警告", 0);
				return;
			}
			if (t66 < 0) {
				JOptionPane.showMessageDialog(null, "你输入的图书现库存不能小于0，请核实!!",
						"警告", 0);
				return;
			}
			if (t55 < t66) {
				JOptionPane.showMessageDialog(null, "你输入的图书现库存不能大于总库存，请核实!!",
						"警告", 0);
				return;
			}
			s = conn.prepareStatement("insert into book_info values(?,?,?,?,?,?,?,?)");
			s.setInt(1, Integer.valueOf(t1));
			s.setInt(2, Integer.valueOf(t2));
			s.setString(3, t3);
			s.setString(4, t4);
			s.setInt(5, t55);
			s.setInt(6, t66);
			s.setString(7, t7);
			s.setString(8, now);
			s.execute();
			conn.close();
			JOptionPane.showMessageDialog(null, "已经新增成功", "通知", 1);
		} catch (Exception e) {
		}
	}

	/*参数值bookId，返回对应图书信息bookInfo*/
	public BookInfo findBookInfo(int bookId) {
		BookInfo bookInfo = new BookInfo();
		try {
			Connection conn = Databaseconnection.getConnection();
			PreparedStatement s = conn
					.prepareStatement("select * from book_info where book_id=?");
			s.setInt(1, bookId);
			ResultSet r = s.executeQuery();
			if (r.next()) {
				bookInfo.setBookId(r.getInt(1));
				bookInfo.setpNo(r.getInt(2));
				bookInfo.setBookName(r.getString(3));
				bookInfo.setBookAuthor(r.getString(4));
				bookInfo.setBookTotalInventory(r.getInt(5));
				bookInfo.setBookNowInventory(r.getInt(6));
				bookInfo.setBookLocation(r.getString(7));
			} else {
				JOptionPane.showMessageDialog(null, "你输入的图书编号不存在!!", "通知", 1);
			}
		} catch (Exception e) {

		}
		return bookInfo;
	}

	/*修改图书信息*/
	public void updateBookInfo(String t1, String t2, String t3, String t4,
			String t5, String t6, String t7,String now) {
		try {
			Connection conn = Databaseconnection.getConnection();
			PreparedStatement s = conn
					.prepareStatement("update book_info set pno=?,book_name=?,book_author=?,book_total_inventory=?,"
							+ "book_now_inventory=?,book_location=?,create_time=? where book_id=?");
			s.setInt(1, Integer.valueOf(t2));
			s.setString(2, t3);
			s.setString(3, t4);
			s.setInt(4, Integer.valueOf(t5));
			s.setInt(5, Integer.valueOf(t6));
			s.setString(6, t7);
			s.setString(8, now);
			s.setInt(7, Integer.valueOf(t1));
			s.executeUpdate();
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
		}
	}

	
	/*借阅书籍操作，更新表book_info & borrow_detail，返回成功操作条数*/
	public int borrowBookInfo(int bookId, String studentId,String bookName) {
		int x = 0;
		try {
			Connection conn = Databaseconnection.getConnection();
			PreparedStatement s = conn                                          // 对应图书现库存-1
					.prepareStatement("update book_info set book_now_inventory = book_now_inventory-? "
							+ "where book_now_inventory > 0 and book_id=?");
			s.setInt(1, 1);
			s.setInt(2, bookId);
			x = s.executeUpdate();                                              // x值记录成功操作update的条数
			s = conn.prepareStatement("insert into borrow_detail(book_id,book_name,student_id,"
					+ "borrow_time,return_condition,return_ifovertime) values(?,?,?,?,?,?)");
			s.setInt(1, bookId);                                                // 增加表borrow_detail记录
			s.setString(2, bookName);
			s.setString(3, studentId);
			s.setString(4, sdf.format(new Date()));
			s.setBoolean(5, false);
			s.setBoolean(6, false);
			x = s.executeUpdate();
			conn.close();
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return x;
	}
}
