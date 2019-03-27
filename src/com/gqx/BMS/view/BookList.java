package com.gqx.BMS.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.gqx.BMS.entity.BookInfo;
import com.gqx.BMS.service.BookService;

public class BookList extends JPanel {

	/**
	 * 界面监听事件：图书列表
	 */
	private static final long serialVersionUID = 1L;

	JFrame jf = new JFrame();

	JTable table;

	JScrollPane jsc;

	DefaultTableModel model = new DefaultTableModel();

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	BookService bookService = new BookService();

	public BookList() {
		jf.getContentPane().setLayout(null);                               // 图书列表界面设置
		model.addColumn("图书编号");
		model.addColumn("出版社编号");
		model.addColumn("书名");
		model.addColumn("作者");
		model.addColumn("总库存");
		model.addColumn("现库存");
		model.addColumn("存放位置");
		model.addColumn("创建时间");
		table = new JTable(model);
		jsc = new JScrollPane(table);
		this.setLayout(null);
		table.setGridColor(Color.magenta);
		table.setRowHeight(30);                                            // 表格设置
		table.setSelectionForeground(Color.orange);
		table.setFont(new Font(null, Font.PLAIN, 18));
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, r);
		table.getTableHeader().setFont(new Font(null, Font.BOLD, 25));
		jsc.setBounds(0, 0, 1000, 480);
		this.setBounds(0, 0, 1000, 480);
		this.add(jsc, BorderLayout.CENTER);
		JPanel jp = new JPanel();
		jp.setBounds(10, 520, 1000, 100);
		jp.setBackground(Color.pink);
		JLabel l1 = new JLabel("图书书名");                                 // 下方菜单栏设置
		final JTextField jtf1 = new JTextField(10);
		JButton serach = new JButton("搜索");
		JButton add = new JButton("添加");
		JButton update = new JButton("修改");
		JButton delete = new JButton("删除");
		jp.add(l1);
		jp.add(jtf1);
		jp.add(serach);
		jp.add(add);
		jp.add(update);
		jp.add(delete);
		this.add(jp, BorderLayout.SOUTH);
		jf.getContentPane().add(this);
		List<BookInfo> bookInfos = bookService.getBookList(null);          // 打印全部书籍记录
		for (int i = 0; i < bookInfos.size(); i++) {
			BookInfo book = bookInfos.get(i);
			Object b[] = new Object[] { book.getBookId(), book.getpNo(), book.getBookName(),
					book.getBookAuthor(), book.getBookTotalInventory(),
					book.getBookNowInventory(), book.getBookLocation(),
					book.getCreateTime() };
			model.addRow(b);
		}
		serach.addActionListener(new ActionListener() {                    // 监听事件：查询书籍

			@Override
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
				String bookName = jtf1.getText();                           
				List<BookInfo> bookInfos = bookService.getBookList(bookName);
				for (int i = 0; i < bookInfos.size(); i++) {
					BookInfo book = bookInfos.get(i);
					Object b[] = new Object[] { book.getBookId(),
							book.getpNo(),
							book.getBookName(), book.getBookAuthor(),
							book.getBookTotalInventory(),
							book.getBookNowInventory(), 
							book.getBookLocation(),
							book.getCreateTime() };
					model.addRow(b);
				}
			}
		});
		add.addActionListener(new ActionListener() {                   // 监听事件：增加书籍

			@Override
			public void actionPerformed(ActionEvent e) {
				new Add();
			}
		});

		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {                // 监听事件：点击目的数据修改操作
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(jf, "您未选择数据修改!!", "通知", 1);
					return;
				}
				Integer bookId = (Integer) table.getValueAt(row, 0);
				new Update(row, bookId);
			}
		});
		delete.addActionListener(new ActionListener() {                 // 监听事件：点击目的数据删除操作    

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(jf, "您未选中数据删除!!", "通知", 1);
					return;
				} else {
					int a = JOptionPane.showConfirmDialog(jf,
							"您确定要删除选择的图书，删除不可恢复!!", "通知", 2);
					if (a == 1 || a == 2) {

						return;
					}
				}
				Integer bookId = (Integer) table.getValueAt(row, 0);
				Integer x = bookService.deleteBookInfo(bookId);
				if (x >= 1) {
					JOptionPane.showMessageDialog(null, "已经删除成功!!", "通知", 1);

				} else {
					JOptionPane.showMessageDialog(null,
							"你选择的图书编号有误，删除未成功，请核实!!", "通知", 0);
				}
				model.removeRow(row);
			}
		});
	}

	class Update extends JPanel {                                      

		/**
		 * 修改书籍窗体设置
		 */
		private static final long serialVersionUID = 1L;

		JFrame jf = new JFrame("修改图书");

		JLabel label[] = new JLabel[12];

		JButton b1, b2, b3, b4, b5, b6;

		JTextField text1 = new JTextField();

		JTextField text2 = new JTextField();

		JTextField text3 = new JTextField();

		JTextField text4 = new JTextField();

		JTextField text5 = new JTextField();

		JTextField text6 = new JTextField();
		
		JTextField text7 = new JTextField();
		
		JTextField text8 = new JTextField();

		Label text = new Label();

		Thread mm = null;

		Date date;

		SimpleDateFormat m = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");

		Label label3;

		@SuppressWarnings("deprecation")
		public Update(final int row, final Integer bookId) {              // 根据鼠标点击的目标行来确定修改的目标书籍
			label[2] = new JLabel("图书编号:");
			label[2].setBounds(100, 50, 70, 30);
			this.add(label[2]);

			text1.setBounds(170, 55, 100, 25);
			this.add(text1);
			
			label[3] = new JLabel("出版社编号:");
			label[3].setBounds(300, 50, 70, 30);
			this.add(label[3]);

			text2.setBounds(370, 55, 100, 25);
			this.add(text2);

			
			label[4] = new JLabel("书名:");
			label[4].setBounds(500, 50, 70, 30);
			this.add(label[4]);

			text3.setBounds(570, 55, 100, 25);
			this.add(text3);

			label[5] = new JLabel("作者:");
			label[5].setBounds(100, 150, 70, 30);
			this.add(label[5]);

			text4.setBounds(170,155, 120, 25);
			this.add(text4);

			label[6] = new JLabel("总库存:");
			label[6].setBounds(300, 150, 70, 30);
			this.add(label[6]);

			text5.setBounds(370, 155, 100, 25);
			this.add(text5);

			label[7] = new JLabel("现库存:");
			label[7].setBounds(500, 150, 70, 30);
			this.add(label[7]);

			text6.setBounds(570, 155, 100, 25);
			this.add(text6);
			
			label[8] = new JLabel("存放位置:");
			label[8].setBounds(100, 250, 70, 30);
			this.add(label[8]);

			text7.setBounds(170, 255, 100, 25);
			this.add(text7);

			label[9] = new JLabel("创建日期:");
			label[9].setBounds(300, 250, 70, 30);
			this.add(label[9]);

			text8.setBounds(370, 255, 120, 25);
			text8.setText(new Date().toLocaleString());
			text8.setBackground(Color.yellow);
			this.add(text8);
			

			b2 = new JButton("确认修改");
			b2.setBounds(230, 330, 100, 30);
			this.add(b2);
			BookInfo bookInfo = bookService.findBookInfo(bookId);             // 调用BookService，修改图书列表
			text1.setText(String.valueOf(bookInfo.getBookId()));
			text2.setText(String.valueOf(bookInfo.getpNo()));
			text3.setText(bookInfo.getBookName());
			text4.setText(bookInfo.getBookAuthor());
			text5.setText(String.valueOf(bookInfo.getBookTotalInventory()));
			text6.setText(String.valueOf(bookInfo.getBookNowInventory()));
			text7.setText(bookInfo.getBookLocation());
			b2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {                 // 添加监听事件

					try {
						if ((text1.getText().equals(""))) {
							JOptionPane.showMessageDialog(jf,
									"图书编号为空,请用户必须输入图书编号!!", "警告", 0);

						} else if (text2.getText().equals("")) {
							JOptionPane.showMessageDialog(jf, "出版社号不能为空,请从新输入!!",
									"警告", 0);
						} else if (text3.getText().equals("")) {
							JOptionPane.showMessageDialog(jf, "书名不能为空,请从新输入!!",
									"警告", 0);
						} else {
							String now = sdf.format(new Date());
							String t1 = text1.getText();
							String t2 = text2.getText();
							String t3 = text3.getText();
							String t4 = text4.getText();
							String t5 = text5.getText();
							String t6 = text6.getText();
							String t7 = text7.getText();
							bookService.updateBookInfo(t1, t2, t3, t4, t5, t6,t7,now);
							JOptionPane
									.showMessageDialog(jf, "已经修改成功", "通知", 1);
							jf.dispose();
							model.setValueAt(t1, row, 0);
							model.setValueAt(t2, row, 1);
							model.setValueAt(t3, row, 2);
							model.setValueAt(t4, row, 3);
							model.setValueAt(t5, row, 4);
							model.setValueAt(t6, row, 5);
							model.setValueAt(t7, row, 6);
							model.setValueAt(sdf.format(new Date()), row, 6);
						}
					} catch (Exception e1) {
						JOptionPane
								.showMessageDialog(jf, "图书编号不能重复!!", "警告", 0);
						e1.printStackTrace();
					}
				}
			});

			b6 = new JButton("退出修改");
			b6.setBounds(400, 330, 100, 30);
			this.add(b6);
			b6.addActionListener(new ActionListener() {                         // 关闭修改书籍窗体

				@Override
				public void actionPerformed(ActionEvent e) {
					jf.dispose();
				}
			});
			jf.setBounds(500, 350, 800, 500);
			jf.setVisible(true);
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jf.getContentPane().setLayout(null);
			this.setLayout(null);
			this.setBounds(10, 10, 865, 480);
			jf.getContentPane().add(this);
		}
	}

	class Add extends JPanel {                                                 
		/**
		 * 添加图书窗口设置
		 */
		private static final long serialVersionUID = 1L;


		JFrame jf = new JFrame("添加新图书");

		JLabel label[] = new JLabel[12];

		JButton b1, b2, b3, b4, b5, b6;

		JTextField text1 = new JTextField();

		JTextField text2 = new JTextField();

		JTextField text3 = new JTextField();

		JTextField text4 = new JTextField();

		JTextField text5 = new JTextField();

		JTextField text6 = new JTextField();
		
		JTextField text7 = new JTextField();
		
		JTextField text8 = new JTextField();

		Label text = new Label();

		Thread mm = null;

		Date date;

		SimpleDateFormat m = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");

		Label label3;

		@SuppressWarnings("deprecation")
		public Add() {
			label[2] = new JLabel("图书编号:");
			label[2].setBounds(100, 50, 70, 30);
			this.add(label[2]);

			text1.setBounds(170, 55, 100, 25);
			this.add(text1);
			
			label[3] = new JLabel("出版社编号:");
			label[3].setBounds(300, 50, 70, 30);
			this.add(label[3]);

			text2.setBounds(370, 55, 100, 25);
			this.add(text2);

			
			label[4] = new JLabel("书名:");
			label[4].setBounds(500, 50, 70, 30);
			this.add(label[4]);

			text3.setBounds(570, 55, 100, 25);
			this.add(text3);

			label[5] = new JLabel("作者:");
			label[5].setBounds(100, 150, 70, 30);
			this.add(label[5]);

			text4.setBounds(170,155, 120, 25);
			this.add(text4);

			label[6] = new JLabel("总库存:");
			label[6].setBounds(300, 150, 70, 30);
			this.add(label[6]);

			text5.setBounds(370, 155, 100, 25);
			this.add(text5);

			label[7] = new JLabel("现库存:");
			label[7].setBounds(500, 150, 70, 30);
			this.add(label[7]);

			text6.setBounds(570, 155, 100, 25);
			this.add(text6);
			
			label[8] = new JLabel("存放位置:");
			label[8].setBounds(100, 250, 70, 30);
			this.add(label[8]);

			text7.setBounds(170, 255, 100, 25);
			this.add(text7);

			label[9] = new JLabel("创建日期:");
			label[9].setBounds(300, 250, 70, 30);
			this.add(label[9]);

			text8.setBounds(370, 255, 120, 25);
			text8.setText(new Date().toLocaleString());
			text8.setBackground(Color.yellow);
			this.add(text8);

			b2 = new JButton("确认添加");
			b2.setBounds(230, 330, 100, 30);
			this.add(b2);
			b2.addActionListener(new ActionListener() {                            // 添加监听事件

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						if ((text1.getText().equals(""))) {
							JOptionPane.showMessageDialog(jf,
									"图书编号为空,请用户必须输入图书编号!!", "警告", 0);

						} else if (text2.getText().equals("")) {
							JOptionPane.showMessageDialog(jf, "出版社号不能为空,请从新输入!!",
									"警告", 0);
						} else if (text3.getText().equals("")) {
							JOptionPane.showMessageDialog(jf, "书名不能为空,请从新输入!!",
									"警告", 0);
						} else {
							String now = sdf.format(new Date());
							String t1 = text1.getText();
							String t2 = text2.getText();
							String t3 = text3.getText();
							String t4 = text4.getText();
							String t5 = text5.getText();
							String t6 = text6.getText();
							String t7 = text7.getText();
							bookService.addBookInfo(t1, t2, t3, t4, t5, t6, t7, now);
							jf.dispose();
							Vector<String> v = new Vector<String>();
							v.add(t1);
							v.add(t2);
							v.add(t3);
							v.add(t4);
							v.add(t5);
							v.add(t6);
							v.add(t7);
							v.add(now);
							model.addRow(v);
						}
					} catch (Exception e1) {
						JOptionPane
								.showMessageDialog(jf, "图书编号不能重复!!", "警告", 0);
						e1.printStackTrace();
					}
				}
			});

			b6 = new JButton("退出添加");
			b6.setBounds(400, 330, 100, 30);
			this.add(b6);
			b6.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {                         // 关闭添加书籍窗体
					jf.dispose();
				}
			});
			jf.setBounds(500, 350, 800, 500);
			jf.setVisible(true);
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jf.getContentPane().setLayout(null);
			this.setLayout(null);
			this.setBounds(10, 10, 865, 480);
			jf.getContentPane().add(this);
		}
	}
}
