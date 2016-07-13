package com.gmail.xxandrew28xx.frames;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.gmail.xxandrew28xx.frames.SkriptDependencySelectionFrame.SkriptFile;

import java.awt.Color;

public class AddSkript extends JFrame{
	JPanel panel;
	JFrame instance;
	private JTextField fileName;
	private JLabel error;
	private JCheckBox hideSkriptchk;
	public AddSkript(){
		super("Add Skript");
		instance = this;
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/com/gmail/xxandrew28xx/icon.png")));
		setResizable(false);
		setSize(395, 220);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		getContentPane().setLayout(null);
		
		JButton btnSelectFile = new JButton("Select File");
		btnSelectFile.setBounds(12, 13, 97, 25);
		getContentPane().add(btnSelectFile);
		
		fileName = new JTextField();
		fileName.setBounds(121, 14, 256, 22);
		getContentPane().add(fileName);
		fileName.setColumns(10);
		
		hideSkriptchk = new JCheckBox("Hide Script");
		hideSkriptchk.setBounds(121, 51, 113, 25);
		getContentPane().add(hideSkriptchk);
		
		JTextPane txtpnMakesTheSkript = new JTextPane();
		txtpnMakesTheSkript.setText("Makes the Skript load without it showing up in plugins/Skript/scripts.");
		txtpnMakesTheSkript.setBackground(UIManager.getColor("Button.background"));
		txtpnMakesTheSkript.setBounds(121, 73, 220, 45);
		getContentPane().add(txtpnMakesTheSkript);
		
		error = new JLabel("");
		error.setForeground(Color.RED);
		error.setBounds(12, 116, 365, 16);
		getContentPane().add(error);
		
		JButton btnAddSkript = new JButton("Add Skript");
		btnAddSkript.setBounds(147, 149, 97, 25);
		getContentPane().add(btnAddSkript);
		btnSelectFile.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jf = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Skript Files", "sk", "skqc");
				jf.setFileFilter(filter);
				jf.showOpenDialog(instance);
				jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jf.setSize(640, 480);
				File path = jf.getSelectedFile();
				if (path == null){
					return;
				}
				fileName.setText(path.getAbsolutePath());
			}});
		btnAddSkript.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (fileName.getText().equals("")){
					error.setText("You need to select a Skript file!");
					return;
				}
				Boolean hide_sk = hideSkriptchk.isSelected();
				File f = new File(fileName.getText());
				if (!f.exists()){
					error.setText("File does not exist!");
					return;
				}
				for (Entry<String, SkriptFile> e : SkriptDependencySelectionFrame.skripts.entrySet()){
					if (e.getKey().equals(f.getName())){
						error.setText("File with same name already given!");
						return;
					}
				}
				SkriptDependencySelectionFrame.list.add(f.getName());
				SkriptDependencySelectionFrame.skripts.put(f.getName(), new SkriptFile(f.getAbsolutePath(), getFileName(f), hide_sk));
				setVisible(false);
			}});
		
	}
	public String getFileName(File file){
		String name = file.getName();
		int pos = name.lastIndexOf(".");
		if (pos > 0) {
		    name = name.substring(0, pos);
		}
		return name;
	}
}