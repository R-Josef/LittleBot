package moe.feo.littlebot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Group {
	
	public static ArrayList<Group> list = new ArrayList<Group>();
	
	public String name;
	public QQWebHook hook;
	public String history;
	public String sending;
	
	public Group(String name, QQWebHook hook) {
		this.name = name;
		this.hook = hook;
		this.history = "";
		this.sending = "";
	}
	
	public static void load(LinkedHashMap<String, String> map) {
		Iterator<Map.Entry<String, String>> iterator= map.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			String name = entry.getKey();
			String key = entry.getValue();
			QQWebHook hook = new QQWebHook(key);
			Group group = new Group(name, hook);
			list.add(group);
		}
	}

}
