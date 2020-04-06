package moe.feo.littlebot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;

public class Config {
	
	private File file;
	public static LinkedHashMap<String, String> key = new LinkedHashMap<String, String>();
	
	public void createFile() {// 如果文件不存在就创建
		// 获取jar包所在的根目录
		String jarpath = this.getClass().getClassLoader().getResource("").getPath();
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
	
	public void load() {// 加载key.txt
		createFile();
		FileReader in = null;
		BufferedReader bfin = null;
		try {
			in = new FileReader(file);
			bfin = new BufferedReader(in);
			String line = null;
			while ((line = bfin.readLine()) != null) {
				String[] strs = line.split("=");
				if (strs.length == 2) {
					key.put(strs[0], strs[1]);
				}
			}
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
		
	}
	
}
