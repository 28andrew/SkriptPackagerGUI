package com.gmail.xxandrew28xx.frames;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;

import com.gmail.xxandrew28xx.SkriptPackagerGUI;

public class SkriptDependencySelectionFrame extends JFrame {
	JPanel panel;
	private JTextPane skInfo;
	public static LinkedHashMap<String, SkriptFile> skripts;
	public static List list;
	SkriptFile selected;
	private JButton btnNext;
	public SkriptDependencySelectionFrame(){
		super("Skript Packager GUI");
		skripts = new LinkedHashMap<String, SkriptFile>();
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
		JLabel title = new JLabel("Skript Dependencies");
		title.setFont(new Font("Tahoma", Font.PLAIN, 18));
		title.setBounds(237, 13, 159, 22);
		getContentPane().add(title);

		JLabel lblSkripts = new JLabel("Skripts: ");
		lblSkripts.setBounds(79, 27, 56, 16);
		getContentPane().add(lblSkripts);
		
		JButton addSkriptButton = new JButton("Add Skript\r\n");
		addSkriptButton.setBounds(56, 380, 97, 25);
		getContentPane().add(addSkriptButton);
		
		JTextPane info = new JTextPane();
		info.setEditable(false);
		info.setText("This is where you put your Skript Dependencies. NOT where you put your Main Skript, you did this earlier! Just hit NEXT if you have none. (Skript Dependencies e.g : json.sk, math.sk, cool.skqc)");
		info.setBackground(UIManager.getColor("Button.background"));
		info.setBounds(214, 57, 356, 70);
		getContentPane().add(info);
		
		skInfo = new JTextPane();
		skInfo.setEditable(false);
		skInfo.setBackground(UIManager.getColor("Button.background"));
		skInfo.setBounds(214, 150, 356, 96);
		getContentPane().add(skInfo);
		
		JButton btnRemove = new JButton("REMOVE");
		btnRemove.setBounds(208, 259, 97, 25);
		btnRemove.setVisible(false);
		getContentPane().add(btnRemove);
		
		
		addSkriptButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new AddSkript().setVisible(true);
			}});
		list.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				SkriptFile sk = skripts.get(list.getItem((Integer)arg0.getItem()));
				selected = sk;
				skInfo.setText(sk.getName() + ": " + sk.getPath() + "\n" + "Hidden: " + sk.getHide());
			}});
		btnRemove.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (selected != null){
					list.remove(selected.getName());
					skripts.remove(selected.getName());
				}
			}});
		btnNext = new JButton("NEXT");
		btnNext.setBounds(493, 384, 128, 50);
		getContentPane().add(btnNext);
		btnNext.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SkriptPackagerGUI.next();
			}});
		
	}
	public static class SkriptFile{
		String path;
		String name;
		Boolean hide;
		
		public SkriptFile(String path, String name, Boolean hide){
			this.path = path;
			this.name = name;
			this.hide = hide;
		}
		public String getPath(){
			return path;
		}
		public String getName(){
			return name;
		}
		public Boolean getHide(){
			return hide;
		}
	}
}
