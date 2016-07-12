package com.gmail.xxandrew28xx;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;

public class ClaimJarFrame extends JFrame{
	JPanel panel;
	File file = CompileFrame.claim;
	private JTextField fileName;
	private File saveTo;
	private ClaimJarFrame instance;
	private JLabel lblCopied;
	public ClaimJarFrame(){
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
		
		JLabel lblSaveJar = new JLabel("Save Jar");
		lblSaveJar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSaveJar.setBounds(279, 13, 75, 16);
		getContentPane().add(lblSaveJar);
		
		JButton btnSave = new JButton("Select Save Location\r\n");
		btnSave.setBounds(221, 94, 191, 25);
		getContentPane().add(btnSave);
		
		fileName = new JTextField();
		fileName.setBounds(196, 132, 242, 22);
		getContentPane().add(fileName);
		fileName.setColumns(10);
		
		JButton btnDone = new JButton("Done");
		
		btnDone.setBounds(268, 188, 97, 25);
		getContentPane().add(btnDone);
		
		lblCopied = new JLabel("");
		lblCopied.setBounds(31, 290, 575, 16);
		getContentPane().add(lblCopied);
		btnSave.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser j = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Jar File", "jar");
				j.setFileFilter(filter);

				j.setSelectedFile(new File(file.getName()));
				j.showSaveDialog(instance);
				File path = j.getSelectedFile();
				if (path == null){
					return;
				}
				fileName.setText(path.getName());
				saveTo = path;
			}});
		btnDone.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					if (saveTo == null){
						return;
					}
					FileUtils.copyFile(file, saveTo);
					lblCopied.setText("Copied to: " + saveTo.getAbsolutePath());
				}catch(Exception e2){
					e2.printStackTrace();
				}
			}});
	}
}
