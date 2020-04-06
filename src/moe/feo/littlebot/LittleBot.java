package moe.feo.littlebot;
import javafx.application.Application;
import moe.feo.littlebot.fxui.FXMain;

import java.awt.BorderLayout;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class LittleBot {
	
	public static void main(String[] args) {
		if(args.length >= 1){
			String choose = args[0];
			switch (choose){
				case "fx":
					String[] fxArgs = null;
					if(args.length >= 2)
						fxArgs = Arrays.copyOfRange(args, 1, args.length);
					Application.launch(FXMain.class, fxArgs);
					break;
				case "swing":
					swingInit();
					break;
				default:
					swingInit();
					break;
			}
		} else {
			swingInit();
		}
	}

	public static void swingInit(){
		System.setProperty("sun.java2d.opengl", "true");
		System.setProperty("sun.java2d.d3d", "false");
		Config cfg = Config.getInstance();
		if (cfg.key.isEmpty()) {
			LittleBot.noKey();
			return;
		}
		Group.GroupMannager.getInstance().load(cfg.key);
		new GUI().draw();
	}

	public static void noKey() {
		//提示标签
		JLabel label = new JLabel("<html><div align='center'><font color='#708090' size='3'>"
				+ "<font color='#CD5C5C' size='5'>未找到Key.</font><br>"
				+ "请在当前目录下的Key.txt文件中写入Key.<br>"
				+ "格式: &lt;名称&gt;=&lt;KEY&gt;<br>"
				+ "示例: MyGroup=02aca1e815814b5a<br>"
				+ "名称可以随意填写, 但是key一定要填写正确<br>"
				+ "允许写入多个不同群的key, 一行一个."
				+ "</font></div></html>");
		label.setHorizontalAlignment(JLabel.CENTER);
		//框架设置
		JFrame frame = new JFrame("LittleBot");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(BorderLayout.CENTER, label);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
}
