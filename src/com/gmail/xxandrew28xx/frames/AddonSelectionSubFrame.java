package com.gmail.xxandrew28xx.frames;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;

import com.gmail.xxandrew28xx.FixedVersion;
import com.gmail.xxandrew28xx.StringToVersion;
import com.gmail.xxandrew28xx.SkriptPackagerGUI.Addon;

import java.awt.SystemColor;

public class AddonSelectionSubFrame extends JFrame{
	JPanel panel;
	ButtonGroup dl_type = new ButtonGroup();
	private JTextField addonNameField;
	private JTextField ddlURL;
	private JLabel ddlURLmsg;
	private JLabel mddlURLmsg;
	private JEditorPane mddlURL;
	private JTextPane mddlURLhelp;
	private JLabel error;
	public AddonSelectionSubFrame(){
		//super(new AddonSelectionFrame(), "Add Addon", true);
		super("Add Addon");
		//DEFAULTS (not using a seperate method due to WindowBuilder parser
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/com/gmail/xxandrew28xx/icon.png")));
		setResizable(false);
		setSize(400, 470);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		getContentPane().setLayout(null);
		
		JLabel lblAddonName = new JLabel("Name:");
		lblAddonName.setBounds(12, 53, 38, 16);
		getContentPane().add(lblAddonName);
		
		addonNameField = new JTextField();
		addonNameField.setBounds(50, 53, 88, 22);
		getContentPane().add(addonNameField);
		addonNameField.setColumns(10);
		
		JRadioButton rdbtn1DL = new JRadioButton("One Download");
		rdbtn1DL.setSelected(true);
		rdbtn1DL.setBounds(172, 49, 127, 25);
		rdbtn1DL.setActionCommand("oneDL");
		rdbtn1DL.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

				updateDL(dl_type.getSelection().getActionCommand().equals("oneDL"));
			}});
		getContentPane().add(rdbtn1DL);
		
		JRadioButton rdbtnMultiDL = new JRadioButton("Version Specific Downloads");
		rdbtnMultiDL.setBounds(172, 76, 185, 25);
		rdbtnMultiDL.setActionCommand("multiDL");
		rdbtnMultiDL.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

				updateDL(dl_type.getSelection().getActionCommand().equals("oneDL"));
			}});
		getContentPane().add(rdbtnMultiDL);
		
		dl_type.add(rdbtn1DL);
		dl_type.add(rdbtnMultiDL);
		
		ddlURL = new JTextField();
		ddlURL.setBounds(50, 169, 318, 22);
		getContentPane().add(ddlURL);
		ddlURL.setColumns(10);
		
		ddlURLmsg = new JLabel("Direct Download URL:");
		ddlURLmsg.setBounds(12, 140, 128, 16);
		getContentPane().add(ddlURLmsg);
		
		error = new JLabel("");
		error.setForeground(Color.RED);
		error.setBounds(74, 370, 279, 16);
		getContentPane().add(error);
		
		JButton btnAddAddon = new JButton("Add Addon");
		btnAddAddon.setBounds(150, 399, 97, 25);
		getContentPane().add(btnAddAddon);
		
		mddlURLmsg = new JLabel("Download URLs for specific versions");
		mddlURLmsg.setBounds(12, 140, 256, 16);
		getContentPane().add(mddlURLmsg);
		
		mddlURL = new JEditorPane();
		mddlURL.setBounds(50, 204, 318, 86);
		getContentPane().add(mddlURL);
		
		mddlURLhelp = new JTextPane();
		mddlURLhelp.setText("One on each line. Format it like this:\r\n1.8.X http://downloadlink.com/example.jar\r\n1.9.4 http://downloadlink.com/example19.jar");
		mddlURLhelp.setBackground(SystemColor.menu);
		mddlURLhelp.setEditable(false);
		mddlURLhelp.setBounds(50, 300, 307, 59);
		getContentPane().add(mddlURLhelp);
		
		updateDL(dl_type.getSelection().getActionCommand().equals("oneDL"));
		
		btnAddAddon.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (addonNameField.getText().equals("")){
					error.setText("Addon name is required!");
					return;
				}
				String addon_name = addonNameField.getText();
				if (addon_name.contains(" ")){
					addon_name = addon_name.replace(' ', '_');
				}
				if (AddonSelectionFrame.addons.containsKey(addon_name)){
					error.setText("Addon name is already used!");
					return;
				}
				if (dl_type.getSelection().getActionCommand().equals("oneDL")){
					//Single
					if (ddlURL.getText().equals("")){
						error.setText("You need a download URL");
						return;
					}
					URL u;
					try{
						u = new URL(ddlURL.getText());
					}catch(MalformedURLException e2){
						error.setText("Invalid download URL :O");
						return;
					}
					AddonSelectionFrame.addons.put(addon_name, new Addon(new FixedVersion(u), addon_name));
					AddonSelectionFrame.list.add(addon_name);
				}else{
					//Multi
					HashMap<String, URL> versionToDL = new HashMap<String, URL>();
					if (mddlURL.getText().equals("")){
						error.setText("You need download URLs");
						return;
					}
					String[] split = mddlURL.getText().split("\n");
					Integer lineN = 1;
					for (String line : split){
						String[] split2 = line.split(" ");
						String version = split2[0];
						URL u;
						try{
							u = new URL(split2[1]);
						}catch(MalformedURLException e2){
							error.setText("Invalid URL on line " + lineN);
							return;
						}
						versionToDL.put(version, u);
						lineN++;
					}
					AddonSelectionFrame.addons.put(addon_name, new Addon(new StringToVersion(versionToDL),addon_name));
					AddonSelectionFrame.list.add(addon_name);
				}
				setVisible(false);
			}});
	}
	public void updateDL(Boolean single){
		if (single){
			ddlURL.setVisible(true);
			ddlURLmsg.setVisible(true);
			mddlURL.setVisible(false);
			mddlURLmsg.setVisible(false);
			mddlURLhelp.setVisible(false);
		}else{
			ddlURL.setVisible(false);
			ddlURLmsg.setVisible(false);
			mddlURL.setVisible(true);
			mddlURLmsg.setVisible(true);
			mddlURLhelp.setVisible(true);
		}
	}
}
