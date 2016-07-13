package com.gmail.xxandrew28xx.frames;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import com.gmail.xxandrew28xx.SkriptPackagerGUI;

import javax.swing.JButton;

public class MainFrame extends JFrame {
	JPanel panel;
	public MainFrame(){
		super("Skript Packager GUI");
		//DEFAULTS (not using a seperate method due to WindowBuilder parser
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/com/gmail/xxandrew28xx/icon.png")));
		setResizable(false);
		setSize(640, 480);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		//
		
		JLabel lblNewLabel = new JLabel("Welcome to Skript Packager");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(204, 13, 225, 22);
		getContentPane().add(lblNewLabel);
		
		JTextPane txtpnThisIs = new JTextPane();
		txtpnThisIs.setContentType("text/html");
		txtpnThisIs.setEditable(false);
		txtpnThisIs.setBounds(65, 52, 504, 109);
		HTMLDocument doc = (HTMLDocument)txtpnThisIs.getDocument();
		HTMLEditorKit editorKit = (HTMLEditorKit)txtpnThisIs.getEditorKit();
		String txt = "<html>This is a free software written by <b>xXAndrew28Xx ( andrewtran312@gmail.com https://forums.skunity.com/users/xxandrew28xx/ )</b> This software turns Skripts in to jar files. The jar files are a Bukkit/Spigot plugin that install all Skript/Addon dependencies and the scripts themselves to make the Skript feel like as if it was a real java plugin.</html>";
		try {
			editorKit.insertHTML(doc, doc.getLength(), txt, 0, 0, null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		getContentPane().add(txtpnThisIs);
		
		JButton btnNext = new JButton("NEXT");
		btnNext.setBounds(253, 211, 128, 50);
		getContentPane().add(btnNext);
		btnNext.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SkriptPackagerGUI.next();
			}});
		
	}
}
