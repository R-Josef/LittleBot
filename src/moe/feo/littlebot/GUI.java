package moe.feo.littlebot;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GUI implements ActionListener, KeyListener {
	
	private JTextArea his = new JTextArea(10,0);
	private JTextArea inputbox = new JTextArea(3,0);
	private JFrame frame = new JFrame("LittleBot");
	
	public GUI() {
		his.setEditable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inputbox.addKeyListener(this);
	}
	
	public void open() {
		his.setLineWrap(true);
		JScrollPane hisscroll = new JScrollPane(his);
		
		inputbox.setLineWrap(true);
		JScrollPane inputscroll = new JScrollPane(inputbox);
		JButton send = new JButton("发送");
		send.addActionListener(this);
		JPanel inputpanel = new JPanel();
		inputpanel.setLayout(new BorderLayout());
		inputpanel.add(BorderLayout.EAST, send);
		inputpanel.add(BorderLayout.CENTER, inputscroll);
		
		frame.add(BorderLayout.CENTER, hisscroll);
		frame.add(BorderLayout.SOUTH, inputpanel);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
	
	public void enter() {
		String msg = inputbox.getText();
		inputbox.setText("");
		recordMessage(msg);
		LittleBot.qqWebHook(msg);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() instanceof JButton) {
			enter();
		}
	}
	
	public void recordMessage(String str) {
		SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String message = new String("[" + ft.format(date) + "]: " + str + System.lineSeparator());
		his.append(message);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.isControlDown() && arg0.getKeyCode()==KeyEvent.VK_ENTER) {
			inputbox.append(System.lineSeparator());
		} else if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			enter();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (!arg0.isControlDown() && arg0.getKeyCode()==KeyEvent.VK_ENTER) {
			inputbox.setText("");
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}
