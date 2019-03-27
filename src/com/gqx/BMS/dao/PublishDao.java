package com.gqx.BMS.dao;

/**
 * @author GQX 图书操作
 * getPublishList 书名模糊查询
 * deleteBookPublish 删
 * addBookPublish 增
 * findBookPublish 书号查询
 * updateBookInfo 修改
 * updateBookPublish 借阅操作（外键 borrow_detail(book_id)）
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.gqx.BMS.entity.BookPublish;
import com.gqx.BMS.util.Databaseconnection;

public class PublishDao {

	/*调用实体类BookPublish，参数值pName，返回查询图书出版社集合<BookInfo>*/
	public List<BookPublish> getPublishList(String pName) {
		List<BookPublish> bookPublishs = new ArrayList<BookPublish>();
		Connection conn = null;
		try {
			conn = Databaseconnection.getConnection();
			Statement s = conn.createStatement();
			String sql = "select * from book_publish";
			ResultSet r;
			if (pName != null && !"".equals(pName)) {               // "".equals(A),即字符串A为null
				sql += " where pname like '%" + pName + "%'";      // 实现模糊查询
			}                         // 以create_time降序排序
			r = s.executeQuery(sql);
			while (r.next()) {                                            // 查询结果
				BookPublish publishs = new BookPublish();
				publishs.setpNo(r.getInt(1));
				publishs.setpName(r.getString(2));
				publishs.setpLocation(r.getString(3));
				publishs.setpPhone(r.getString(4));
				bookPublishs.add(publishs);
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
		return bookPublishs;
	}
	
	/*参数值pNo，返回成功执行删除对应图书出版社信息的条数*/
	public int deleteBookPublish(Integer pNo) {
		Connection conn;
		PreparedStatement ps = null;
		int x = 0;
		try {
			String sql = "delete from book_publish where pno=?";
			conn = Databaseconnection.getConnection();
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, pNo);
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
	
	/*增加图书出版社信息*/
	public void addBookPublish(String t1, String t2, String t3, String t4) {
		try {

			Connection conn = Databaseconnection.getConnection();
			PreparedStatement s = conn
					.prepareStatement("select * from book_publish where pno=?");
			s.setInt(1, Integer.valueOf(t1));
			ResultSet r = s.executeQuery();
			if (r.next()) {
				// Java消息提示框，.showMessageDialog即只有一个确认键
				JOptionPane.showMessageDialog(null, "你输入的出版社编号已存在，请核实!!", "警告",
						0);
				return;
			}
			s = conn.prepareStatement("insert into book_publish(pno,pname,plocation,pphone) "
					+ "values(?,?,?,?)");
			s.setInt(1, Integer.valueOf(t1));
			s.setString(2, t2);
			s.setString(3, t3);
			s.setString(4, t4);
			s.execute();
			conn.close();
			JOptionPane.showMessageDialog(null, "已经新增成功", "通知", 1);
		} catch (Exception e) {
		}
	}
	
	/*参数值pNo，返回对应出版社信息bookPublish*/
	public BookPublish findBookPublish(int pNo) {
		BookPublish bookPublish = new BookPublish();
		try {
			Connection conn = Databaseconnection.getConnection();
			PreparedStatement s = conn
					.prepareStatement("select * from book_publish where pno=?");
			s.setInt(1, pNo);
			ResultSet r = s.executeQuery();
			if (r.next()) {
				bookPublish.setpNo(r.getInt(1));
				bookPublish.setpName(r.getString(2));
				bookPublish.setpLocation(r.getString(3));
				bookPublish.setpPhone(r.getString(4));
			} else {
				JOptionPane.showMessageDialog(null, "你输入的出版社编号不存在!!", "通知", 1);
			}
		} catch (Exception e) {

		}
		return bookPublish;
	}
	
	/*修改出版社信息*/
	public void updateBookPublish(String t1, String t2, String t3, String t4) {
		try {
			Connection conn = Databaseconnection.getConnection();
			PreparedStatement s = conn
					.prepareStatement("update book_publish set pname=?,plocation=?,"
							+ "pphone=? where pno=?");
			s.setString(1, t2);
			s.setString(2, t3);
			s.setString(3, t4);
			s.setInt(4, Integer.valueOf(t1));
			s.executeUpdate();
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
		}
	}
	
	
}
