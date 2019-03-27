package com.gqx.BMS.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

import com.gqx.BMS.service.UserService;
import com.gqx.BMS.view.MainBack;

public class Login extends JFrame {

	/**
	 * 管理员登录界面
	 */
	private static final long serialVersionUID = 1L;
	private final UserService userService = new UserService();

	public static void main(String[] args) {
		new Login();
	}
	
	public Login() {

		setTitle("图书管理系统-登录");                                // 设置Login界面标题
		setSize(392, 300);
		setLocationRelativeTo(null);                                 // 让窗体居中显示
		setLayout(null);                                             // 将容器的布局设置为null布局（空布局）

		JLabel nameLabel = new JLabel("用户名:");                    // 卡号标签显示设置
		nameLabel.setBounds(60, 40, 100, 40);
		add(nameLabel);

		final JTextField nameText = new JTextField("");             // 方框
		nameText.setBounds(140, 45, 185, 30);
		add(nameText);

		JLabel pwdLabel = new JLabel("密码:");
		pwdLabel.setBounds(60, 100, 100, 40);
		add(pwdLabel);

		final JPasswordField pwdText = new JPasswordField("");
		pwdText.setBounds(140, 105, 185, 30);
		add(pwdText);
		// 登录按钮
		JButton loginButton = new JButton("登录");                  // 按钮
		loginButton.setBounds(70, 195, 90, 30);
		add(loginButton);
		// 取消按钮
		JButton cancelButton = new JButton("取消");
		cancelButton.setBounds(190, 195, 90, 30);
		add(cancelButton);

		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
		loginButton.addActionListener(new ActionListener() {      // 添加登录按钮监听事件

			@Override
			public void actionPerformed(ActionEvent e) {
				try {                                             // 确保uname为学号合法格式
					String username = nameText.getText();
					// 正则规则
					Pattern pattern = Pattern.compile("3(1|2)1\\d{1}0\\d{5}");
					// 被校验的字符串
					Matcher match = pattern.matcher(username);
					if (!match.matches()) {
						JOptionPane.showMessageDialog(null, "学号格式输入有误");
						return;
					}
					
					char[] pwd = pwdText.getPassword();          // 调用类UserService，返回是否能成功登录
					boolean isSuccess = userService.userLoginSuccess(username,String.valueOf(pwd));
					if (!isSuccess) {
						JOptionPane.showMessageDialog(null, "登录失败，用户名或密码错误!!",
								"通知", 1);
						return;
					}
					JOptionPane.showMessageDialog(null, "恭喜，登录成功!!", "通知", 1);
					dispose();                                  // 关闭当前Login窗体，释放所有本机屏幕资源
					new MainBack();                             // 打开MainBack窗体
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		// 取消按钮监听事件
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 登录界面消失
				dispose();
			}
		});
	}

}
