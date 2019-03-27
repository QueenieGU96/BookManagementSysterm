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

import com.gqx.BMS.entity.BookPublish;
import com.gqx.BMS.service.PublishService;

public class PublishList extends JPanel {

	/**
	 * 监听事件：出版社列表
	 */


		private static final long serialVersionUID = 1L;

		JFrame jf = new JFrame();

		JTable table;

		JScrollPane jsc;

		DefaultTableModel model = new DefaultTableModel();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		PublishService publishService = new PublishService();
		
		
		public PublishList() {
		jf.getContentPane().setLayout(null);                               // 图书列表界面设置
		model.addColumn("出版社编号");
		model.addColumn("出版社名");
		model.addColumn("出版社地址");
		model.addColumn("出版社电话");
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
		JLabel l1 = new JLabel("出版社名");                                 // 下方菜单栏设置
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
		List<BookPublish> bookPublishs = publishService.getPublishList(null);          // 打印全部书籍记录
		for (int i = 0; i < bookPublishs.size(); i++) {
			BookPublish publish = bookPublishs.get(i);
			Object b[] = new Object[] { publish.getpNo(), publish.getpName(), 
					publish.getpLocation(),
					publish.getpPhone()};
			model.addRow(b);
		}
		serach.addActionListener(new ActionListener() {                    // 监听事件：查询书籍

			@Override
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
				String pName = jtf1.getText();                           
				List<BookPublish> bookPublishs = publishService.getPublishList(pName); 
				for (int i = 0; i < bookPublishs.size(); i++) {
					BookPublish publish = bookPublishs.get(i);
					Object b[] = new Object[] { publish.getpNo(), publish.getpName(), 
							publish.getpLocation(),
							publish.getpPhone() };
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
				Integer pNo = (Integer) table.getValueAt(row, 0);
				new Update(row, pNo);
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
							"您确定要删除选择的出版社，删除不可恢复!!", "通知", 2);
					if (a == 1 || a == 2) {

						return;
					}
				}
				Integer pNo = (Integer) table.getValueAt(row, 0);
				Integer x = publishService.deleteBookPublish(pNo);
				if (x >= 1) {
					JOptionPane.showMessageDialog(null, "已经删除成功!!", "通知", 1);

				} else {
					JOptionPane.showMessageDialog(null,
							"你选择的出版社编号有误，删除未成功，请核实!!", "通知", 0);
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

			JFrame jf = new JFrame("修改出版社");

			JLabel label[] = new JLabel[12];

			JButton b1, b2, b3, b4, b5, b6;

			JTextField text1 = new JTextField();

			JTextField text2 = new JTextField();

			JTextField text3 = new JTextField();

			JTextField text4 = new JTextField();

			Label text = new Label();

			Thread mm = null;

			Date date;

			SimpleDateFormat m = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");

			Label label3;

			public Update(final int row, final Integer pNo) {              // 根据鼠标点击的目标行来确定修改的目标书籍
				label[2] = new JLabel("出版社编号:");
				label[2].setBounds(100, 50, 70, 30);
				this.add(label[2]);

				text1.setBounds(170, 55, 100, 25);
				this.add(text1);
				

				label[3] = new JLabel("出版社名:");
				label[3].setBounds(300, 50, 70, 30);
				this.add(label[3]);

				text2.setBounds(370, 55, 100, 25);
				this.add(text2);

				label[4] = new JLabel("出版社地址:");
				label[4].setBounds(100, 150, 70, 30);
				this.add(label[4]);

				text3.setBounds(170, 155, 120, 25);
				this.add(text3);

				label[5] = new JLabel("出版社电话:");
				label[5].setBounds(300, 150, 70, 30);
				this.add(label[5]);

				text4.setBounds(370, 155, 100, 25);
				this.add(text4);

				

				b2 = new JButton("确认修改");
				b2.setBounds(230, 330, 100, 30);
				this.add(b2);
				BookPublish bookPublish = publishService.findBookPublish(pNo);             // 调用BookService，修改图书列表
				text1.setText(String.valueOf(bookPublish.getpNo()));
				text2.setText(bookPublish.getpName());
				text3.setText(bookPublish.getpLocation());
				text4.setText(bookPublish.getpPhone());
				b2.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {                 // 添加监听事件

						try {
							if ((text1.getText().equals(""))) {
								JOptionPane.showMessageDialog(jf,
										"出版社编号为空,请用户必须输入图书编号!!", "警告", 0);

							} else if (text2.getText().equals("")) {
								JOptionPane.showMessageDialog(jf, "出版社名不能为空,请从新输入!!",
										"警告", 0);
							} else {
								String t1 = text1.getText();
								String t2 = text2.getText();
								String t3 = text3.getText();
								String t4 = text4.getText();
								publishService.updateBookPublish(t1, t2, t3, t4);
								JOptionPane
										.showMessageDialog(jf, "已经修改成功", "通知", 1);
								jf.dispose();
								model.setValueAt(t1, row, 0);
								model.setValueAt(t2, row, 1);
								model.setValueAt(t3, row, 2);
								model.setValueAt(t4, row, 3);
							}
						} catch (Exception e1) {
							JOptionPane
									.showMessageDialog(jf, "出版社编号不能重复!!", "警告", 0);
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


			JFrame jf = new JFrame("添加新出版社");

			JLabel label[] = new JLabel[12];

			JButton b1, b2, b3, b4, b5, b6;

			JTextField text1 = new JTextField();

			JTextField text2 = new JTextField();

			JTextField text3 = new JTextField();

			JTextField text4 = new JTextField();

			Label text = new Label();

			Thread mm = null;

			Date date;

			SimpleDateFormat m = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");

			Label label3;

			public Add() {
				label[2] = new JLabel("出版社编号:");
				label[2].setBounds(100, 50, 70, 30);
				this.add(label[2]);

				text1.setBounds(170, 55, 100, 25);
				this.add(text1);
				

				label[3] = new JLabel("出版社名:");
				label[3].setBounds(300, 50, 70, 30);
				this.add(label[3]);

				text2.setBounds(370, 55, 100, 25);
				this.add(text2);

				label[4] = new JLabel("出版社地址:");
				label[4].setBounds(100, 150, 70, 30);
				this.add(label[4]);

				text3.setBounds(170, 155, 120, 25);
				this.add(text3);

				label[5] = new JLabel("出版社电话:");
				label[5].setBounds(300, 150, 70, 30);
				this.add(label[5]);

				text4.setBounds(370, 155, 100, 25);
				this.add(text4);

				b2 = new JButton("确认添加");
				b2.setBounds(230, 330, 100, 30);
				this.add(b2);
				b2.addActionListener(new ActionListener() {                            // 添加监听事件

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							if ((text1.getText().equals(""))) {
								JOptionPane.showMessageDialog(jf,
										"出版社编号为空,请用户必须输入图书编号!!", "警告", 0);

							} else if (text2.getText().equals("")) {
								JOptionPane.showMessageDialog(jf, "出版社名不能为空,请从新输入!!",
										"警告", 0);
							} else {
								String t1 = text1.getText();
								String t2 = text2.getText();
								String t3 = text3.getText();
								String t4 = text4.getText();
								publishService.addBookPublish(t1, t2, t3, t4);
								jf.dispose();
								Vector<String> v = new Vector<String>();
								v.add(t1);
								v.add(t2);
								v.add(t3);
								v.add(t4);
								model.addRow(v);
							}
						} catch (Exception e1) {
							JOptionPane
									.showMessageDialog(jf, "出版社编号不能重复!!", "警告", 0);
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
