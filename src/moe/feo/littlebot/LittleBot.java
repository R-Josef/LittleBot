package moe.feo.littlebot;
import java.awt.BorderLayout;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class LittleBot {
	
	public static String key;
	
	public static void main(String[] args) {
		Config cfg = new Config();
		cfg.createFile();
		key = cfg.getKey();
		if (key == null) {
			LittleBot.noKey();
			return;
		}
		new GUI().open();
		//System.out.println("请开始吹水.");
		//Scanner sc = new Scanner(System.in);
		//while (sc.hasNextLine()) {
		//	qqWebHook(sc.nextLine());
		//}
		//sc.close();
	}
	
	public static void qqWebHook(String msg) {
		String query = "{\"content\": [ {\"type\":0,\"data\":\"" + msg + "\"}]}";
		try {
			URL url = new URL("https://app.qun.qq.com/cgi-bin/api/hookrobot_send?key="+key);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			out.write(query);
			out.flush();
			out.close();
			int code = connection.getResponseCode();// 必须获取一下响应码
			if (code != 500) {
				InputStreamReader in = new InputStreamReader(connection.getInputStream());
				char[] chars = new char[1024];
				int count = 0;
				while ((count = in.read(chars)) != -1) {
					System.out.print(new String(chars, 0, count)); 
				}
				in.close();
			}
			connection.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void noKey() {
		JFrame frame = new JFrame("LittleBot");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//JLabel titlelabel = new JLabel("未找到Key");
		JLabel label = new JLabel("请在当前目录下的Key.txt文件中写入Key");
		//titlelabel.setHorizontalAlignment(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		//frame.add(BorderLayout.NORTH, titlelabel);
		frame.add(BorderLayout.CENTER, label);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
}
