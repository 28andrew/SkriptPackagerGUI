package com.gmail.xxandrew28xx.frames;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.gmail.xxandrew28xx.SkriptPackagerGUI;
import com.gmail.xxandrew28xx.SkriptPackagerGUI.MainSkript;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.JCheckBox;

public class SkriptOptionsFrame extends JFrame{
	JPanel panel;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JEditorPane editorPane;
	private JLabel error;
	private JCheckBox chckbxHideScript;
	private JTextField fileName;
	SkriptOptionsFrame instance;
	public SkriptOptionsFrame(){
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
		//
		JLabel lblNewLabel = new JLabel("Skript Options");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(258, 0, 117, 22);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Skript Name:");
		lblNewLabel_1.setBounds(62, 57, 75, 16);
		getContentPane().add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setToolTipText("");
		textField.setBounds(142, 54, 206, 22);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNoSpaces = new JLabel("No spaces (A-Z)");
		lblNoSpaces.setForeground(Color.GRAY);
		lblNoSpaces.setBounds(103, 74, 150, 16);
		getContentPane().add(lblNoSpaces);
		
		JLabel lblAuthor = new JLabel("Author Name:\r\n");
		lblAuthor.setBounds(57, 106, 80, 16);
		getContentPane().add(lblAuthor);
		
		textField_1 = new JTextField();
		textField_1.setToolTipText("No spaces and only letters!");
		textField_1.setColumns(10);
		textField_1.setBounds(142, 103, 206, 22);
		getContentPane().add(textField_1);
		
		JLabel lblNoSpacesaz = new JLabel("No spaces (A-Z, 1-9, _, -)");
		lblNoSpacesaz.setForeground(Color.GRAY);
		lblNoSpacesaz.setBounds(103, 125, 150, 16);
		getContentPane().add(lblNoSpacesaz);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(67, 204, 70, 16);
		getContentPane().add(lblDescription);
		
		editorPane = new JEditorPane();
		editorPane.setBounds(142, 204, 206, 118);
		getContentPane().add(editorPane);
		
		JLabel lblToUseQuotes = new JLabel("Description of your Skript");
		lblToUseQuotes.setForeground(Color.GRAY);
		lblToUseQuotes.setBounds(103, 335, 150, 16);
		getContentPane().add(lblToUseQuotes);
		
		JButton btnNext = new JButton("NEXT");
		btnNext.setBounds(258, 364, 128, 50);
		getContentPane().add(btnNext);
		
		JLabel lblVersion = new JLabel("Version:");
		lblVersion.setBounds(85, 154, 52, 16);
		getContentPane().add(lblVersion);
		
		textField_2 = new JTextField();
		textField_2.setToolTipText("No spaces and only letters!");
		textField_2.setColumns(10);
		textField_2.setBounds(142, 151, 206, 22);
		getContentPane().add(textField_2);
		
		JLabel lblMustContainA = new JLabel("Must contain a number and no spaces.");
		lblMustContainA.setForeground(Color.GRAY);
		lblMustContainA.setBounds(103, 175, 247, 16);
		getContentPane().add(lblMustContainA);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setBounds(113, 86, 1, 10);
		getContentPane().add(verticalStrut);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalStrut_1.setBounds(113, 145, 1, 10);
		getContentPane().add(verticalStrut_1);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		verticalStrut_2.setBounds(111, 196, 1, 10);
		getContentPane().add(verticalStrut_2);
		
		error = new JLabel("");
		error.setForeground(Color.RED);
		error.setBounds(379, 192, 212, 130);
		getContentPane().add(error);
		
		chckbxHideScript = new JCheckBox("Hide Script");
		chckbxHideScript.setBounds(379, 53, 113, 25);
		chckbxHideScript.setBorderPainted(false);
		getContentPane().add(chckbxHideScript);
		
		JLabel lblMakeNotShow = new JLabel("Make not show up in scripts folder.\r\n");
		lblMakeNotShow.setForeground(Color.GRAY);
		lblMakeNotShow.setBounds(403, 74, 247, 16);
		getContentPane().add(lblMakeNotShow);
		
		JButton btnSelectFile = new JButton("Select File");
		btnSelectFile.setBounds(379, 137, 97, 25);
		getContentPane().add(btnSelectFile);
		
		fileName = new JTextField();
		fileName.setBounds(379, 169, 225, 22);
		getContentPane().add(fileName);
		fileName.setColumns(10);
		
		JLabel lblSkript = new JLabel("Skript File:");
		lblSkript.setBounds(379, 109, 75, 16);
		getContentPane().add(lblSkript);
		btnSelectFile.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
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
		btnNext.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String sk_name = textField.getText();
				String author = textField_1.getText();
				String version = textField_2.getText();
				String description = editorPane.getText();
				String sk_file_path = fileName.getText();
				if (sk_name.equals("") || author.equals("") || version.equals("") || description.equals("") || sk_file_path.equals("")){
					error.setText("Please fill out all text fields & sk file selection.");
					return;
				}
				if (sk_name.contains(" ")){
					error.setText("Skript name can not contain spaces");
					return;
				}
				if (!sk_name.replace("_", "").replace("-", "").matches("[a-zA-Z0-9]+")){
					error.setText("Skript name must be alphanumeric and can also use _ and -");
					return;
				}
				if (author.contains(" ")){
					error.setText("Author must not contain a space");
					return;
				}
				if (version.contains(" ")){
					error.setText("Version can not contain spaces");
					return;
				}
				if (!version.matches(".*\\d+.*")){
					error.setText("Version must contain at least one number");
					return;
				}
				File f = new File(sk_file_path);
				if (!f.exists()){
					error.setText("File does not exist!");
					return;
				}
				error.setText("");
				SkriptPackagerGUI.ms = new MainSkript(sk_name, author, version, description, chckbxHideScript.isSelected(), f);
				System.out.println("Dev MSG - Skript: " + SkriptPackagerGUI.ms.toString());
				SkriptPackagerGUI.next();
			}});
	}
}
