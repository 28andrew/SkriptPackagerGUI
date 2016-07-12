package com.gmail.xxandrew28xx;

import java.net.URL;

import com.gmail.xxandrew28xx.SkriptPackagerGUI.VersionToDL;

public class FixedVersion implements VersionToDL{
	URL u;
	public FixedVersion(URL u){
		this.u = u;
	}
	public URL fromVersion(String version){
		return u;
	}
	public URL getURL(){
		return u;
	}
}