package moe.feo.littlebot;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class QQWebHook {
	
	private String key;
	
	public QQWebHook(String key) {
		this.key = key;
	}
	
	public void send(String msg) {
		String query = "{\"content\": [ {\"type\":0,\"data\":\"" + msg + "\"}]}";
		try {
			URL url = new URL("https://app.qun.qq.com/cgi-bin/api/hookrobot_send?key=" + key);
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
}
