package com.gqx.BMS.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class BorrowList extends JPanel {

	/**
	 * 界面监听事件：借阅列表
	 */
	private static final long serialVersionUID = 1L;

	JFrame jf = new JFrame();

	JTable table;

	JScrollPane jsc;

	DefaultTableModel model = new DefaultTableModel();

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	BookService bookService = new BookService();

	// 正则规则 
	Pattern pattern = Pattern.compile("3(1|2)1\\d{1}0\\d{5}");

	public BorrowList() {
		jf.getContentPane().setLayout(null);                            // 借阅列表界面设置
		model.addColumn("图书编号");
		model.addColumn("书名");
		model.addColumn("作者");
		model.addColumn("总库存");
		model.addColumn("现库存");
		model.addColumn("创建时间");
		table = new JTable(model);
		jsc = new JScrollPane(table);
		this.setLayout(null);
		table.setGridColor(Color.magenta);
		table.setRowHeight(30);
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
		JLabel l1 = new JLabel("图书书名");
		final JTextField jtf1 = new JTextField(10);
		JButton serach = new JButton("搜索");
		JButton borrowBook = new JButton("借阅");
		jp.add(l1);
		jp.add(jtf1);
		jp.add(serach);
		jp.add(borrowBook);
		this.add(jp, BorderLayout.SOUTH);
		jf.getContentPane().add(this);
		List<BookInfo> bookInfos = bookService.getBookList(null);
		for (int i = 0; i < bookInfos.size(); i++) {
			BookInfo book = bookInfos.get(i);
			Object b[] = new Object[] { book.getBookId(), book.getBookName(),
					book.getBookAuthor(), book.getBookTotalInventory(),
					book.getBookNowInventory(), book.getCreateTime() };
			model.addRow(b);
		}
		serach.addActionListener(new ActionListener() {                            // 监听事件：查询书籍

			@Override
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
				String bookName = jtf1.getText();
				List<BookInfo> bookInfos = bookService.getBookList(bookName);
				for (int i = 0; i < bookInfos.size(); i++) {
					BookInfo book = bookInfos.get(i);
					Object b[] = new Object[] { book.getBookId(),
							book.getBookName(), book.getBookAuthor(),
							book.getBookTotalInventory(),
							book.getBookNowInventory(), book.getCreateTime() };
					model.addRow(b);
				}
			}
		});

		borrowBook.addActionListener(new ActionListener() {                        // 监听事件：添加借阅书籍

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(jf, "您未选中数据借阅!!", "通知", 1);
					return;
				} else {
					int a = JOptionPane.showConfirmDialog(jf, "您确定要借阅选择的图书吗!!",
							"通知", 2);
					if (a == 1 || a == 2) {

						return;
					}
				}
				Integer bookId = (Integer) table.getValueAt(row, 0);
				Integer nowStock = (Integer) table.getValueAt(row, 4);
				if (nowStock <= 0) {
					JOptionPane.showMessageDialog(null, "你输入的图书现库存为0，不能借阅!!",
							"警告", 0);
					return;
				}
				String studentId = JOptionPane.showInputDialog("请输入借阅学生编号");
				if (studentId == null || "".equals(studentId)) {
					JOptionPane.showMessageDialog(null, "借阅学生编号为空");
					return;
				}// 被校验的字符串
				Matcher match = pattern.matcher(studentId);                       // 正则表达式判别学号合法格式
				if (!match.matches()) {
					JOptionPane.showMessageDialog(null, "借阅学生编号格式有误");
					return;
				}
				String bookName = (String) table.getValueAt(row, 1);
				Integer x = bookService.borrowBookInfo(bookId, studentId,bookName);         // 调用BookService，返回成功操作update两表的条数
						                                                                    // 可判断是否借阅步骤完成（见BookDao.borrowBookInfo）
				if (x >= 1) {
					JOptionPane.showMessageDialog(null, "已经借阅成功!!", "通知", 1);

				} else {
					JOptionPane.showMessageDialog(null,
							"你选择的图书编号有误，借阅未成功，请核实!!", "通知", 0);
				}
				model.setRowCount(0);
				List<BookInfo> bookInfos = bookService.getBookList(null);                   // 显示全部书籍信息
				for (int i = 0; i < bookInfos.size(); i++) {
					BookInfo book = bookInfos.get(i);
					Object b[] = new Object[] { book.getBookId(),
							book.getBookName(), book.getBookAuthor(),
							book.getBookTotalInventory(),
							book.getBookNowInventory(), book.getCreateTime() };
					model.addRow(b);
				}
			}
		});
	}
}
