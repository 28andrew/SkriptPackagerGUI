package com.gmail.xxandrew28xx.frames;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.yaml.snakeyaml.Yaml;

import com.gmail.xxandrew28xx.FixedVersion;
import com.gmail.xxandrew28xx.SkriptPackagerGUI;
import com.gmail.xxandrew28xx.StringToVersion;
import com.gmail.xxandrew28xx.SkriptPackagerGUI.Addon;
import com.gmail.xxandrew28xx.SkriptPackagerGUI.MainSkript;
import com.gmail.xxandrew28xx.SkriptPackagerGUI.VersionToDL;
import com.gmail.xxandrew28xx.frames.SkriptDependencySelectionFrame.SkriptFile;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;


public class CompileFrame extends JFrame{
	JPanel panel;

	public static volatile JProgressBar SPprogressBar;
	
	public static volatile JProgressBar mvnProgressBar;
	public static volatile Stage stage;
    private static final int EOF = -1;
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    private JLabel lblES;
    private JProgressBar ESprogressBar;
    private JProgressBar COprogressBar;
    private JLabel lblCo;

    private String javaHome;
	private JButton btnNext;

	private JButton btnStart;

	public static File claim;
	private JTextField folderName;
	private JLabel lblJdkLocation;
	private JLabel jdkInfo;
	private JButton btnSelectFolder;
	public static enum Stage{
		DOWNLOADING_MAVEN,
		DOWNLOADING_SKRIPT_PACKAGER,
		EDITING_SOURCE,
		COMPILING
	}
	private CompileFrame instance;
    public CompileFrame(){
    	
		super("Skript Packager GUI");
		instance = this;
		
		//DEFAULTS (not using a seperate method due to WindowBuilder parser
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/com/gmail/xxandrew28xx/icon.png")));
		setResizable(false);
		setSize(640, 480);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		mvnProgressBar = new JProgressBar();
		mvnProgressBar.setBounds(244, 58, 146, 14);
		getContentPane().add(mvnProgressBar);
		mvnProgressBar.setStringPainted(true);
		mvnProgressBar.setIndeterminate(false);
		mvnProgressBar.setMinimum(0);
		mvnProgressBar.setMaximum(100);
		
		JLabel lblMvn = new JLabel("Downloading Maven if needed.");
		lblMvn.setBounds(229, 38, 175, 16);
		getContentPane().add(lblMvn);
		
		SPprogressBar = new JProgressBar();
		SPprogressBar.setStringPainted(true);
		SPprogressBar.setMinimum(0);
		SPprogressBar.setMaximum(100);
		SPprogressBar.setIndeterminate(false);
		SPprogressBar.setBounds(244, 105, 146, 14);
		getContentPane().add(SPprogressBar);
		
		JLabel lblDLSP = new JLabel("Extracting Skript Packager");
		lblDLSP.setBounds(242, 85, 149, 16);
		getContentPane().add(lblDLSP);
		
		lblES = new JLabel("Editing Source Files");
		lblES.setBounds(258, 132, 117, 16);
		getContentPane().add(lblES);
		
		ESprogressBar = new JProgressBar();
		ESprogressBar.setStringPainted(false);
		ESprogressBar.setIndeterminate(false);
		ESprogressBar.setBounds(244, 152, 146, 14);
		getContentPane().add(ESprogressBar);
		
		COprogressBar = new JProgressBar();
		COprogressBar.setStringPainted(false);
		COprogressBar.setIndeterminate(false);
		COprogressBar.setBounds(244, 199, 146, 14);
		getContentPane().add(COprogressBar);
		
		lblCo = new JLabel("Compiling Final Jar");
		lblCo.setBounds(258, 179, 117, 16);
		getContentPane().add(lblCo);
		
		btnStart = new JButton("START");
		btnStart.setBounds(525, 409, 97, 25);
		getContentPane().add(btnStart);
		
		folderName = new JTextField();
		folderName.setBounds(259, 292, 252, 22);
		getContentPane().add(folderName);
		folderName.setColumns(10);
		
		lblJdkLocation = new JLabel("JDK Location:");
		lblJdkLocation.setBounds(299, 263, 105, 16);
		getContentPane().add(lblJdkLocation);
		
		jdkInfo = new JLabel("If not provided, it must be in the variable JAVA_HOME");
		jdkInfo.setBounds(184, 319, 327, 16);
		getContentPane().add(jdkInfo);
		
		btnSelectFolder = new JButton("SELECT FOLDER");
		btnSelectFolder.setBounds(110, 291, 146, 25);
		getContentPane().add(btnSelectFolder);
		//
		btnSelectFolder.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jf = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Skript Files", "sk", "skqc");
				jf.setFileFilter(filter);
				jf.showOpenDialog(instance);
				jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jf.setSize(640, 480);
				File path = jf.getSelectedFile();
				if (path == null){
					return;
				}
				folderName.setText(path.getAbsolutePath());
			}});
		btnStart.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				lblJdkLocation.setVisible(false);
				btnStart.setVisible(false);
				folderName.setVisible(false);
				btnSelectFolder.setVisible(false);
				jdkInfo.setVisible(false);
        		javaHome = folderName.getText();
				stage = Stage.DOWNLOADING_MAVEN;
				File maven = new File( "maven" );
				
		        if ( !maven.exists() )
		        {
		        	
		            Thread t = new Thread(){
		            	@Override
		            	public void run(){
		            		File mvnTemp = new File( "mvn.zip" );
		                    mvnTemp.deleteOnExit();
		                    mvnProgressBar.setIndeterminate(true);
		                    //download( "http://www.gtlib.gatech.edu/pub/apache/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.zip", mvnTemp );
		                    download("https://www.dropbox.com/s/lt3llqirc93vbk3/apache-maven-3.3.9.zip?dl=1", mvnTemp);
		                    mvnProgressBar.setIndeterminate(false);
		                    mvnProgressBar.setValue(100);
		                    unZip(mvnTemp, "maven");
		                    downloadSP();
		            	}
		            };
		            t.start();
		        }else{
		        	Thread t = new Thread(){
		        		@Override
		        		public void run(){
		        			mvnProgressBar.setValue(100);
		                	downloadSP();
		        		}
		        	};
		        	t.start();
		        }
			}});
        
        
        //InvocationRequest request = new DefaultInvocationRequest();
        
	}
    private void downloadSP(){
    	//https://github.com/xXAndrew28Xx/SkriptPackager/archive/master.zip
    	stage = Stage.DOWNLOADING_SKRIPT_PACKAGER;
    	File sp = new File("SP.zip");
    	if (!sp.exists()){
    		try{
        		FileUtils.copyInputStreamToFile(SkriptPackagerGUI.class.getResourceAsStream("/com/gmail/xxandrew28xx/SP.zip"), sp);
        		SPprogressBar.setValue(100);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
    	}else{
    		SPprogressBar.setValue(100);
    	}
    	try{
	    	File folder = new File("SkriptPackager/");
	    	if (folder.exists()){
	    		FileUtils.deleteDirectory(folder);
	    	}
	    	File folder2 = new File("Custom/");
	    	if (folder2.exists()){
	    		FileUtils.deleteDirectory(folder2);
	    	}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	unZip(sp, "SkriptPackager");
    	editSource();
    }
    private void editSource(){
    	new File("Custom").deleteOnExit();
    	stage = Stage.EDITING_SOURCE;
    	
    	ESprogressBar.setIndeterminate(true);
    	MainSkript ms = SkriptPackagerGUI.ms;
    	Integer id = new Random().nextInt(256);
    	String packageName = "skript" + id + "." + ms.getName();  
    	File folder = new File("Custom/src/main/java/skript" + id + "/" + ms.getName());
    	folder.mkdirs();
    	try{
    		
    		FileUtils.copyDirectory(new File("SkriptPackager/src/main/java/com/gmail/xXAndrew28Xx"), 
			new File("Custom/src/main/java/skript" + id + "/" + ms.getName()), false);
    		//Change Package Names
    		Iterator<File> it = FileUtils.iterateFiles(new File("Custom/src/main/java/skript" + id + "/" + ms.getName()), new String[]{ "java"}, true);
    		while (it.hasNext()){
    			// TODO close streams
    			File f = it.next();
    			String content = IOUtils.toString(new FileInputStream(f), "UTF-8");
    			content = content.replaceAll("com.gmail.xxandrew28xx", packageName);
    			IOUtils.write(content, new FileOutputStream(f), "UTF-8");
    		}
    		//Copy pom.xml
    		FileUtils.copyFile(new File("SkriptPackager/pom.xml"), new File("Custom/pom.xml"));
    		//Edit pom.xml 
    		// TODO CLEAN UP CODE
    		new File("Custom/pom2.xml").createNewFile();
    		if (new File("Custom/pom2.xml").exists()){
    			BufferedReader br = new BufferedReader(new FileReader(new File("Custom/pom.xml")));
        		BufferedWriter wr = new BufferedWriter(new FileWriter(new File("Custom/pom2.xml")));
        		
        		String line = null;
        		while ((line = br.readLine()) != null){  
        		   if (line.contains("<finalName>")){
        			   wr.write("	        <finalName>" + ms.getName() + "</finalName>" + "\n");
        		   }else{
        			   wr.write(line + "\n");
        		   }
        		} 
        		br.close();
        		wr.close();
    		}
    		new File("Custom/pom.xml").delete();
    		new File("Custom/pom2.xml").renameTo(new File("Custom/pom.xml"));
    		//Do resources folder & copy in to it config.yml, lang.yml, plugin.yml & skripts
    		
    		//FileUtils.copyDirectory(new File("SkriptPackager/src/main/resources/"), new File("Custom/src/main/resources"), false);
    		FileUtils.copyFile(new File("SkriptPackager/src/main/resources/lang.yml"), new File("Custom/src/main/resources/lang.yml"));
    		
    		File config = new File("Custom/src/main/resources/config.yml");
    		config.createNewFile();
    		File plugin = new File("Custom/src/main/resources/plugin.yml");

    		Yaml yml = new Yaml();
    		
    		Map<String, Object> yml_config = new HashMap<String, Object>();
    		
    		Map<String, Object> skript_links = new HashMap<String, Object>();
    		skript_links.put("any-version", "http://nfell2009.uk/skript/downloads/latest/Skript.jar");
    		yml_config.put("skript-links", skript_links);
    		
    		Map<String, Object> scripts = new LinkedHashMap<String, Object>();
    		for (Entry<String, SkriptFile> entry : SkriptDependencySelectionFrame.skripts.entrySet()){
    			String name = entry.getKey();
    			if (name.endsWith(".sk")){
    				name = name.substring(0, name.length() - 3);
    			}
    			SkriptFile sk = entry.getValue();
    			
    			File f = new File(sk.getPath());
    			
    			FileUtils.copyFile(f, new File("Custom/src/main/resources/" + f.getName()));
    			
    			Map<String, String> script_options = new HashMap<String, String>();
    			script_options.put("hide", Boolean.toString(sk.getHide()));
    			script_options.put("file-name", f.getName());
    			scripts.put(name, script_options);
    		}
    		//MainSkript = ms
    		FileUtils.copyFile(ms.getFile(), new File("Custom/src/main/resources/" + ms.getFile().getName()));
    		Map<String, String> script_options = new HashMap<String, String>();
    		script_options.put("hide", Boolean.toString(ms.getHidden()));
    		script_options.put("file-name", ms.getFile().getName());
    		scripts.put(ms.getName(), script_options);
    		
    		yml_config.put("scripts", scripts);
    		
    		Map<String, Object> addons = new LinkedHashMap<String, Object>();
    		for (Entry<String, Addon> entry : AddonSelectionFrame.addons.entrySet()){
    			String addon_name = entry.getKey();
    			Addon addon = entry.getValue();
    			
    			Map<String, Object> addon_options = new HashMap<String, Object>();
    			VersionToDL dl1 = addon.getDownload();
    			if (dl1 instanceof FixedVersion){
    				FixedVersion dl = (FixedVersion) dl1;
    				addon_options.put("any-version", dl.getURL().toString());
    			}else if (dl1 instanceof StringToVersion){
    				StringToVersion dl = (StringToVersion) dl1;
    				Map<String, String> versionSpecific = new HashMap<String, String>();
    				for (Entry<String, URL> entry2 : dl.getMap().entrySet()){
    					versionSpecific.put(entry2.getKey(), entry2.getValue().toString());
    				}
    				addon_options.put("version-specific", versionSpecific);
    			}
    			addons.put(addon_name, addon_options);
    		}
    		
    		yml_config.put("addons", addons);
    		
    		yml.dump(yml_config, new FileWriter(config));
    		
    		Map<String, Object> yml_plugin = new HashMap<String, Object>();
    		yml_plugin.put("main", packageName + ".SkriptPackager");
    		yml_plugin.put("soft-depend", new String[] { "Skript" });
    		yml_plugin.put("name", ms.getName());
    		yml_plugin.put("author", ms.getAuthor());
    		yml_plugin.put("version", ms.getVersion());
    		yml_plugin.put("description", ms.getDescription());
    		
    		yml.dump(yml_plugin, new FileWriter(plugin));
    		ESprogressBar.setIndeterminate(false);
    		ESprogressBar.setStringPainted(true);
    		ESprogressBar.setValue(100);
    		//Maven
    		stage = Stage.COMPILING;
    		COprogressBar.setIndeterminate(true);
    		//File pom = new File("Custom/pom.xml");

    		/*InvocationRequest request = new DefaultInvocationRequest();
    		request.setPomFile(pom);
    		request.setGoals(Collections.singletonList("install"));

    		Invoker invoker = new DefaultInvoker();
    		invoker.setMavenHome(new File("maven/apache-maven-3.3.9/"));
    		
    		invoker.execute( request );
    		*/
    		
    		File pathToExecutable = new File("maven/apache-maven-3.3.9/bin/mvn" + (System.getProperty("os.name").startsWith("Windows") ? ".bat" : ""));
			ProcessBuilder builder = new ProcessBuilder( pathToExecutable.getAbsolutePath(), "install");
			builder.directory( new File( "Custom" ).getAbsoluteFile() );
			builder.redirectErrorStream(true);
			builder.environment().put("M2_HOME", new File("maven/apache-maven-3.3.9").getAbsolutePath());
			//builder.environment().put("JAVA_HOME", "");
			//builder.environment().put("JAVA_HOME", System.getProperty("java.home"));
			if (javaHome != null && !javaHome.equals("")){
				builder.environment().put("JAVA_HOME", javaHome);
			}
			Process process =  builder.start();

			Scanner s = new Scanner(process.getInputStream());
			StringBuilder text = new StringBuilder();
			while (s.hasNextLine()) {
			  text.append(s.nextLine());
			  text.append("\n");
			}
			s.close();
			System.out.println(text);
			int result = process.waitFor();
			claim = new File("Custom/target/" + ms.getName() + ".jar");
			while (!claim.exists()){}
			COprogressBar.setIndeterminate(false);
			COprogressBar.setStringPainted(true);
			COprogressBar.setValue(100);
			System.out.println("RESULT: " + result);
			btnNext = new JButton("NEXT");
			btnNext.setBounds(493, 384, 128, 50);
			getContentPane().add(btnNext);
			btnNext.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					SkriptPackagerGUI.next();
				}});
			
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    }
	public void download(String URL, File out){
		try{
			URLConnection conn = new URL(URL).openConnection();
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:31.0) Gecko/20100101 Firefox/31.0");
			conn.connect();
			try{
				copyInputStreamToFileNew(conn.getInputStream(), out, conn.getContentLength());
			}catch(Exception e){
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void copyInputStreamToFileNew(final InputStream source, final File destination, int fileSize) throws IOException {
        try {

            final FileOutputStream output = FileUtils.openOutputStream(destination);
            try {

                final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
                long count = 0;
                int n = 0;
                while (EOF != (n = source.read(buffer))) {
                    output.write(buffer, 0, n);
                    count += n;
                    Integer done = (int) (count * 100 / fileSize);
                    switch(stage){
                    	case DOWNLOADING_MAVEN:
                    		mvnProgressBar.setValue(done);
                    		break;
                    	case DOWNLOADING_SKRIPT_PACKAGER:
                    		SPprogressBar.setValue(done);
                    		break;
                    	default:
                    		break;
                    }
                }

                output.close(); // don't swallow close Exception if copy completes normally
            } finally {
                IOUtils.closeQuietly(output);
            }

        } finally {
            IOUtils.closeQuietly(source);
        }
	}
	/*
	* http://www.mkyong.com/java/how-to-decompress-files-from-a-zip-file/
	*/
    public void unZip(File file, String outputFolder){

    	ZipFile zipFile = null;
        try{
        	zipFile = new ZipFile(file);
        	Enumeration<? extends ZipEntry> entries = zipFile.entries();
        	  while (entries.hasMoreElements()) {
        	    ZipEntry entry = entries.nextElement();
        	    File entryDestination = new File(outputFolder,  entry.getName());
        	    if (entry.isDirectory()) {
        	        entryDestination.mkdirs();
        	    } else {
        	        entryDestination.getParentFile().mkdirs();
        	        InputStream in = zipFile.getInputStream(entry);
        	        OutputStream out = new FileOutputStream(entryDestination);
        	        IOUtils.copy(in, out);
        	        IOUtils.closeQuietly(in);
        	        out.close();
        	    }
        	  }
        }catch(IOException ex){
          ex.printStackTrace(); 
        }finally{
        	try {
				zipFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
     }    
}
