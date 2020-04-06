package moe.feo.littlebot;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class LittleBot {
	
	public static void main(String[] args) {
		Config cfg = new Config();
		cfg.load();
		
		if (Config.key.isEmpty()) {
			LittleBot.noKey();
			return;
		}
		Group.load(Config.key);
		new GUI().draw();
	}
	
	public static void noKey() {
		//提示标签
		JLabel label = new JLabel("请在当前目录下的Key.txt文件中写入Key");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		//提示文本框
		JTextArea info = new JTextArea();
		info.setText("格式:"
				+ System.lineSeparator()
				+ "<名称>=<KEY>"
				+ System.lineSeparator()
				+ "示例: "
				+ System.lineSeparator()
				+ "MyGroup=02aca1e815814b5ab8f3dc88ff74bbebfb4f2e79"
				+ System.lineSeparator()
				+ "注: 名称可以随意填写, 但是key一定要填写正确, 允许写入多个不同群的key.");
		info.setEditable(false);
		info.setLineWrap(true);
		//框架设置
		JFrame frame = new JFrame("LittleBot");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(BorderLayout.NORTH, label);
		frame.add(BorderLayout.CENTER, info);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
}
