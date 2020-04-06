package moe.feo.littlebot;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GUI implements ActionListener, KeyListener, ChangeListener {
	
	private JFrame frame;
	private JTabbedPane tab;
	private JTextArea history;
	private JTextArea inputbox;
	private Group group;
	
	public GUI() {
		frame = new JFrame("LittleBot");
		tab = new JTabbedPane();
		history = new JTextArea(10,0);
		inputbox = new JTextArea(3,0);
	}
	
	
	public void draw() {
		//选项卡
		for (Group group : Group.GroupMannager.getInstance().list) {
			tab.add(group.name, null);
		}
		tab.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tab.addChangeListener(this);
		stateChanged(null);// 先触发一下监听器以免空指针
		//历史文本区设置
		history.setEditable(false);
		history.setLineWrap(true);
		JScrollPane historyscroll = new JScrollPane(history);
		//输入框
		inputbox.setLineWrap(true);
		inputbox.addKeyListener(this);
		JScrollPane inputscroll = new JScrollPane(inputbox);
		//发送键
		JButton send = new JButton("发送");
		send.addActionListener(this);
		//输入区: 输入框+发送键
		JPanel inputpanel = new JPanel();
		inputpanel.setLayout(new BorderLayout());
		inputpanel.add(BorderLayout.EAST, send);
		inputpanel.add(BorderLayout.CENTER, inputscroll);
		//icon图标
		URL imageurl = this.getClass().getClassLoader().getResource("icon.png");
		Image image= Toolkit.getDefaultToolkit().getImage(imageurl);
		//将组件加入框架
		frame.add(BorderLayout.NORTH, tab);
		frame.add(BorderLayout.CENTER, historyscroll);
		frame.add(BorderLayout.SOUTH, inputpanel);
		//框架设置
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setIconImage(image);
		frame.setVisible(true);
	}
	
	public void send() {//发送, 将输入框的文字发送
		String msg = inputbox.getText();
		inputbox.setText("");
		recordMessage(msg);
		group.hook.send(msg);
	}

	@Override
	public void actionPerformed(ActionEvent event) {// "发送"按钮按下
		if (event.getSource() instanceof JButton) {
			send();
		}
	}
	
	public void recordMessage(String str) {// 将消息记录至历史区域
		SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String message = new String("[" + ft.format(date) + "]: " + str + System.lineSeparator());
		history.append(message);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {// 按下键
		if (arg0.isControlDown() && arg0.getKeyCode()==KeyEvent.VK_ENTER) {// ctrl+enter
			inputbox.append(System.lineSeparator());// 向输入区写入一个回车
		} else if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {// enter
			send();// 发送消息
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {// 松开键
		if (!arg0.isControlDown() && arg0.getKeyCode()==KeyEvent.VK_ENTER) {// enter被松开且没按住ctrl
			inputbox.setText("");// 视为发送了消息, 清空刚刚输入的回车
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (group != null) {// 把历史记录和输入框的内容保存至group对象
			group.history = this.history.getText();
			group.sending = this.inputbox.getText();
		}
		int index = tab.getSelectedIndex();// 这个index和list中的list应该是一样的
		group = Group.GroupMannager.getInstance().list.get(index);// 更换group对象
		// 恢复group对象中的历史记录和输入框
		this.history.setText(group.history);
		this.inputbox.setText(group.sending);
	}
}
