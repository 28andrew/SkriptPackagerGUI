package com.gmail.xxandrew28xx.frames;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gmail.xxandrew28xx.FixedVersion;
import com.gmail.xxandrew28xx.SkriptPackagerGUI;
import com.gmail.xxandrew28xx.StringToVersion;
import com.gmail.xxandrew28xx.SkriptPackagerGUI.Addon;
import com.gmail.xxandrew28xx.SkriptPackagerGUI.VersionToDL;

import java.awt.List;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.awt.Color;

public class AddonSelectionFrame extends JFrame{
	JPanel panel;
	private JLabel labelAddonName;
	private JLabel lblDownload;
	private JTextArea showAddonDownload;
	public static HashMap<String, Addon> addons;
	public static List list;
	private Boolean allow_newline = false;
	private Boolean is_multi = false;
	private JLabel error;
	private JButton btnNext;
	private JButton btnRemove;
	private Addon selected_addon;
	
	public AddonSelectionFrame(){
		super("Skript Packager GUI");
		addons = new HashMap<String, Addon>();
		//DEFAULTS (not using a seperate method due to WindowBuilder parser
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/com/gmail/xxandrew28xx/icon.png")));
		setResizable(false);
		setSize(640, 480);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		list = new List();
		list.setBounds(25, 49, 158, 325);
		getContentPane().add(list);
		//
		JLabel title = new JLabel("Addon Dependencies");
		title.setFont(new Font("Tahoma", Font.PLAIN, 18));
		title.setBounds(234, 0, 165, 22);
		getContentPane().add(title);
		
		JLabel lblAddon = new JLabel("Addons: ");
		lblAddon.setBounds(79, 27, 56, 16);
		getContentPane().add(lblAddon);
		
		JButton addAddonButton = new JButton("Add Addon\r\n");
		addAddonButton.setBounds(56, 380, 97, 25);
		getContentPane().add(addAddonButton);
		
		labelAddonName = new JLabel("");
		labelAddonName.setBounds(372, 35, 198, 16);
		getContentPane().add(labelAddonName);
		
		showAddonDownload = new JTextArea();
		showAddonDownload.setBackground(UIManager.getColor("Button.background"));
		showAddonDownload.setBounds(234, 76, 320, 205);
		showAddonDownload.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == 10 && !allow_newline){
					arg0.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}});
		getContentPane().add(showAddonDownload);
		
		lblDownload = new JLabel("Download:");
		lblDownload.setVisible(false);
		lblDownload.setBounds(382, 61, 72, 16);
		getContentPane().add(lblDownload);
		
		error = new JLabel("");
		error.setBackground(Color.RED);
		error.setBounds(207, 396, 274, 16);
		getContentPane().add(error);
		
		btnNext = new JButton("NEXT");
		btnNext.setBounds(493, 384, 128, 50);
		getContentPane().add(btnNext);
		
		btnRemove = new JButton("REMOVE");
		btnRemove.setBounds(234, 294, 97, 25);
		getContentPane().add(btnRemove);
		btnRemove.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				list.remove(selected_addon.getName());
				addons.remove(selected_addon.getName());
				showAddonDownload.setEditable(false);
			}});
		list.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				String addon_name = list.getItem((Integer) arg0.getItem());
				saveEdit(addon_name);
				if (addons.containsKey(addon_name)){
					Addon a = addons.get(addon_name);
					selected_addon = a;
					labelAddonName.setText("Addon: " + a.getName());
					lblDownload.setVisible(true);
					VersionToDL dl = a.getDownload();
					if (dl instanceof FixedVersion){
						allow_newline = false;
						is_multi = false;
						showAddonDownload.setEditable(true);
						showAddonDownload.setText(((FixedVersion)dl).getURL().toString());
					}else{
						allow_newline = true;
						is_multi = true;
						showAddonDownload.setEditable(true);
						showAddonDownload.setText(((StringToVersion)dl).getFormatted());
					}
				}
			}});
		addAddonButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				AddonSelectionSubFrame assf = new AddonSelectionSubFrame();
				assf.setVisible(true);
				
			}});
		btnNext.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (Addon a : addons.values()){
					saveEdit(a.getName());
				}
				SkriptPackagerGUI.next();
			}});
	}
	public void saveEdit(String addon_name){
		if (labelAddonName.getText() != ""){
			if (addons.containsKey(addon_name)){
				Addon a = addons.get(labelAddonName.getText().split(" ")[1]);
				HashMap<String, URL> versionToDL = new HashMap<String, URL>();
				if (is_multi){
					if (showAddonDownload.getText().equals("")){
						error.setText("You need download URLs");
						return;
					}
					String[] split = showAddonDownload.getText().split("\n");
					Integer lineN = 1;
					for (String line : split){
						String[] split2 = line.split(" ");
						String version = split2[0];
						URL u;
						try{
							u = new URL(split2[1]);
						}catch(MalformedURLException e2){
							error.setText("Invalid URL on line " + lineN + ".");
							return;
						}
						versionToDL.put(version, u);
						lineN++;
					}
					
					a.download = new StringToVersion(versionToDL);
				}else{
					if (showAddonDownload.getText().equals("")){
						error.setText("You need a download URL");
						return;
					}
					URL u;
					try{
						u = new URL(showAddonDownload.getText());
					}catch(MalformedURLException e2){
						error.setText("Invalid URL..");
						return;
					}
					a.download = new FixedVersion(u);
				}
				
				
			}
		}
	}
}
