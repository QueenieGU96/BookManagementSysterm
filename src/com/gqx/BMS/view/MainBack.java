package com.gqx.BMS.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class MainBack extends JFrame implements Runnable {                    // 继承JFrame，则类MainBack可成一个窗体
    /**
	 * 主界面
	 */
	private static final long serialVersionUID = 1L;

	// 接口Runnable
	JPanel p1, p2, p3;                                                        // JPanel，面板容器类

	public JSplitPane jsp1, jsp2;                                             // JSplitPane，分割面板类

	JMenuBar menubar;                                                         // JMenuBar标题，JMenu菜单名，JMenuItem菜单项
	JMenu menu1, menu2, menu3;
	JMenuItem[] item = new JMenuItem[22];

	JTree tree;                                                               // JTree控件，树形结构
	DefaultMutableTreeNode root = new DefaultMutableTreeNode("后台管理");      
	DefaultTreeModel treeModel;

	JScrollPane jsc1;                                                         // 滚动条

	JLabel label1, label2, label3;

	Thread time = null;                                                       // Thread类，用以启动Runnable实现的多线程

	Date date;

	SimpleDateFormat m = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");

	
	public static void main(String[] args) {

		new MainBack();
	}
	
	
	public MainBack() {
		super("图书管理系统");
		init();
		setBounds(200, 80, 1200, 800);                                       // setBounds(x,y,width,height)
		setVisible(true);                                                    // 使控件可以显示出来
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                      // 用户单击窗口的关闭按钮时程序执行的操作
	}

	
	/*界面显示设置*/
	public void init() {
		time = new Thread(this);
		p1 = new JPanel();
		p1.setLayout(new BorderLayout());                                    // 设置用户界面上的屏幕组件的格式布局

		label1 = new JLabel("图书管理系统", JLabel.CENTER);                   // "图书管理系统"显示设置
		label1.setFont(new Font("华文行楷", Font.ROMAN_BASELINE, 40));
		label1.setForeground(Color.BLUE);
		p1.add(label1, BorderLayout.CENTER);

		label2 = new JLabel();
		p1.add(label2, BorderLayout.NORTH);

		p2 = new JPanel();
		p2.setLayout(null);

		p3 = new JPanel();
		p3.setLayout(null);

		label3 = new JLabel("图书管理");                                      // "图书管理"显示设置
		label3.setFont(new Font("华文行楷", Font.ROMAN_BASELINE, 40));
		label3.setForeground(Color.BLUE);
		label3.setBounds(400, 20, 500, 100);
		p3.add(label3);

		jsp2 = new JSplitPane(1, p2, p3);
		jsp2.setDividerLocation(160);

		jsp1 = new JSplitPane(0, p1, jsp2);
		jsp1.setDividerLocation(100);
		this.add(jsp1);

		time.start();

		menubar = new JMenuBar();                                           // 左侧菜单栏分支显示设置
		menu1 = new JMenu("图书管理");
		item[1] = new JMenuItem("图书列表");
		item[2] = new JMenuItem("出版社列表");
		item[3] = new JMenuItem("退出");
		menu1.add(item[1]);
		menu1.add(item[2]);
		menu1.add(item[3]);

		menu2 = new JMenu("借阅管理");
		item[4] = new JMenuItem("借阅列表");
		item[5] = new JMenuItem("归还列表");
		menu2.add(item[4]);
		menu2.add(item[5]);

		menubar.add(menu1);
		menubar.add(menu2);
		setJMenuBar(menubar);

		Vector<String> v = new Vector<String>();
		v.add("图书管理");
		v.add("借阅管理");
		v.add("退出系统");

		DefaultMutableTreeNode n[] = new DefaultMutableTreeNode[v.size()]; // 树形结构
		for (int i = 0; i < v.size(); i++) {
			n[i] = new DefaultMutableTreeNode(v.get(i));
			root.add(n[i]);
		}

		DefaultMutableTreeNode nn1 = new DefaultMutableTreeNode("图书列表");
		DefaultMutableTreeNode nn2 = new DefaultMutableTreeNode("出版社列表");
		DefaultMutableTreeNode nn3 = new DefaultMutableTreeNode("借阅列表");
		DefaultMutableTreeNode nn4 = new DefaultMutableTreeNode("归还列表");
		DefaultMutableTreeNode nn5 = new DefaultMutableTreeNode("退出系统");

		n[0].add(nn1);
		n[0].add(nn2);
		n[1].add(nn3);
		n[1].add(nn4);
		n[2].add(nn5);

		treeModel = new DefaultTreeModel(root);

		tree = new JTree(treeModel);
		tree.addTreeSelectionListener(new TreeSelectionListener() {       // 添加树子节点监听事件
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				TreeAction(e);
				}
			});

		jsc1 = new JScrollPane(tree);
		p2.setLayout(new BorderLayout());
		p2.add(jsc1, BorderLayout.CENTER);
		for (int i = 1; i <= 5; i++) {
			item[i].addActionListener(new ActionListener() {             // 添加按钮监听事件

				@Override
				public void actionPerformed(ActionEvent e) {
					action(e);
				}
			});
		}

		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}
	}

	
	/*当特定于组件的动作（比如被按下）发生时，由组件（比如 Button）生成此高级别事件*/
	public void action(ActionEvent e) {                                    // 类ActionEvent,指示发生了组件定义的动作的语义事件
		String str = e.getActionCommand();
		if (str.equals(item[1].getText())) {
			jsp2.setRightComponent(new BookList());
			jsp2.setDividerLocation(160);
		} else if (str.equals(item[2].getText())) {
			jsp2.setRightComponent(new PublishList());
			jsp2.setDividerLocation(160);
		}else if (str.equals(item[3].getText())) {
			int flag = JOptionPane.showConfirmDialog(null, "是否确定退出系统？", "确认消息",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (flag == 0) {
				System.exit(0);
			}
		} else if (str.equals(item[4].getText())) {
			jsp2.setRightComponent(new BorrowList());
			jsp2.setDividerLocation(160);
		} else if (str.equals(item[5].getText())) {
			jsp2.setRightComponent(new RecordList());
			jsp2.setDividerLocation(160);
		}
	}

	
	/*单击树的叶子节点，可触发TreeSelectionEvent树事件*/
	public void TreeAction(TreeSelectionEvent e) {
		String sNode = e.getPath().getLastPathComponent().toString();
		if (sNode.equals("图书列表") || sNode.equals("图书管理")) {
			jsp2.setRightComponent(new BookList());
			jsp2.setDividerLocation(160);
		}
		if (sNode.equals("出版社列表") || sNode.equals("图书管理")) {
			jsp2.setRightComponent(new PublishList());
			jsp2.setDividerLocation(160);
		}
		

		if (sNode.equals("借阅列表") || sNode.equals("借阅管理")) {
			jsp2.setRightComponent(new BorrowList());
			jsp2.setDividerLocation(160);
		}

		if (sNode.equals("归还列表")) {
			jsp2.setRightComponent(new RecordList());
			jsp2.setDividerLocation(160);
		}

		if (sNode.equals("退出系统")) {
			int flag = JOptionPane.showConfirmDialog(null, "是否确定退出系统？", "确认消息",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (flag == 0) {
				System.exit(0);
			}
		}
	}

	@Override
	public void run() {
		while (true) {                                                          // 当前时间显示
			date = new Date();
			label2.setText("当前时间：" + m.format(date));
			label2.setForeground(Color.BLUE);
			label2.setFont(new Font("华文行楷", Font.ROMAN_BASELINE, 25));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("异常");
			}

		}
	}

}
