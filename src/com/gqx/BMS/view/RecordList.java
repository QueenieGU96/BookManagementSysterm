package com.gqx.BMS.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

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

import com.gqx.BMS.entity.BorrowDetail;
import com.gqx.BMS.service.BorrowService;

public class RecordList extends JPanel {                                       // 归还列表界面设置

	/**
	 * 界面监听事件：归还列表
	 */
	private static final long serialVersionUID = 1L;

	JFrame jf = new JFrame();

	JTable table;

	JScrollPane jsc;

	DefaultTableModel model = new DefaultTableModel();

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	BorrowService borrowService = new BorrowService();

	
	public static void main(String[] args) {
		new RecordList();
	}
	
	public RecordList() {
		jf.getContentPane().setLayout(null);
		model.addColumn("书单序号");
		model.addColumn("图书编号");
		model.addColumn("借阅书名");
		model.addColumn("学生学号");
		model.addColumn("借阅时间");
		model.addColumn("归还时间");
		model.addColumn("是否归还");
		model.addColumn("是否超时");
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
		this.setBounds(0, 0, 1000, 500);
		this.add(jsc, BorderLayout.CENTER);
		JPanel jp = new JPanel();
		jp.setBounds(10, 520, 1000, 100);
		jp.setBackground(Color.pink);

		JLabel l1 = new JLabel("图书书名");
		final JTextField jtf1 = new JTextField(10);
		JLabel l2 = new JLabel("学生编号");
		final JTextField jtf2 = new JTextField(10);
		JButton serach = new JButton("搜索");
		JButton bookReturn = new JButton("归还");
		jp.add(l1);
		jp.add(jtf1);
		jp.add(l2);
		jp.add(jtf2);
		jp.add(serach);
		jp.add(bookReturn);
		this.add(jp, BorderLayout.SOUTH);
		jf.getContentPane().add(this);
		List<BorrowDetail> borrowInfos = borrowService
				.getRecordList(null, null);
		for (int i = 0; i < borrowInfos.size(); i++) {
			BorrowDetail detail = borrowInfos.get(i);
			Object b[] = new Object[] { detail.getBorrowId(),
					detail.getBookId(), detail.getBookName(),
					detail.getStudentId(), detail.getBorrowTime(),
					detail.getReturnTime(),
					(detail.getReturnCondition() ? "是" : "否"),
					(detail.getReturnIfovertime() ? "是" : "否") };
			model.addRow(b);
		}

		serach.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
				String bookNameStr = jtf1.getText();
				String studentIdStr = jtf2.getText();
				List<BorrowDetail> borrowInfos = borrowService.getRecordList(bookNameStr, studentIdStr);
				for (int i = 0; i < borrowInfos.size(); i++) {
					BorrowDetail detail = borrowInfos.get(i);
					Object b[] = new Object[] { detail.getBorrowId(),
							detail.getBookId(), detail.getBookName(),
							detail.getStudentId(), detail.getBorrowTime(),
							detail.getReturnTime(),
							(detail.getReturnCondition() ? "是" : "否"),            // return_condition,return_ifovertime值为1或0，对应取值“是”或“否”
							(detail.getReturnIfovertime() ? "是" : "否") };
					model.addRow(b);
				}
			}
		});
		bookReturn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(jf, "您未选中书籍归还!!", "通知", 1);
					return;
				} else {
					int a = JOptionPane.showConfirmDialog(jf, "您确定要归还选择的书籍吗!!",
							"通知", 2);
					if (a == 1 || a == 2) {

						return;
					}
				}
				Integer borrowId = (Integer) table.getValueAt(row, 0);
				Integer bookId = (Integer) table.getValueAt(row, 1);
				try {
					int result = borrowService.returnBookDetail(borrowId,
							bookId);
					if (result >= 1) {
						JOptionPane.showMessageDialog(jf, "已经归还成功!!", "通知", 1);
						model.setRowCount(0);
						List<BorrowDetail> borrowInfos = borrowService
								.getRecordList(null, null);
						for (int i = 0; i < borrowInfos.size(); i++) {
							BorrowDetail detail = borrowInfos.get(i);
							Object b[] = new Object[] { detail.getBorrowId(),
									detail.getBookId(), detail.getBookName(),
									detail.getStudentId(),
									detail.getBorrowTime(),
									detail.getReturnTime(),
									(detail.getReturnCondition() ? "是" : "否"),
									(detail.getReturnIfovertime() ? "是" : "否") };
							model.addRow(b);
						}
					} else {
						JOptionPane.showMessageDialog(jf,
								"你选择的记录有误，归还未成功，请核实!!", "通知", 0);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
	}

}
