package com.gmail.xxandrew28xx;

import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.gmail.xxandrew28xx.SkriptPackagerGUI.VersionToDL;

public class StringToVersion implements VersionToDL{
	HashMap<String, URL> map = new HashMap<String, URL>();
	public StringToVersion(HashMap<String, URL> map){
		this.map = map;
	}
	@Override
	public URL fromVersion(String version) {
		if (map.containsKey(version)){
			return map.get(version);
		}
		return null;
	}
	public HashMap<String, URL> getMap(){
		return map;
	}
	public String getFormatted(){
		String s = "";
		Set<Entry<String, URL>> entrySet = map.entrySet();
		for (Entry<String,URL> entry : entrySet){
			if (s != ""){
				s += "\n";
			}
			s += entry.getKey() + " " + entry.getValue().toString();
		}
		return s;
	}
}