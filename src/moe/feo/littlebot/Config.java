package moe.feo.littlebot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Config {
	
	private File file;
	
	public void createFile() {
		String jarpath = this.getClass().getClassLoader().getResource("").getPath();// 获取jar包所在的根目录
		try {
			jarpath = URLDecoder.decode(jarpath, "utf-8");// 转换编码以防乱码
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String filepath = jarpath + "key.txt";
		file = new File(filepath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public String getKey() {
		String key = null;
		FileReader in = null;
		BufferedReader bfin = null;
		try {
			in = new FileReader(file);
			bfin = new BufferedReader(in);
			key = bfin.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				bfin.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
			}
		}
		return key;
	}
}
