package com.gmail.xxandrew28xx;

import java.io.File;
import java.io.IOException;

public class Config {
	public static File config = new File("save.yml");
	public static void createConfig(){
		if (!config.exists()){
			try {
				config.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
