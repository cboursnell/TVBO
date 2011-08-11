package com.deranged.tvbo;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Popup extends JDialog {

	private JScrollPane contentPanel;

	public Popup(String s) {
		String[] lines = s.split("\n");
		int height = lines.length;
		System.out.println(height);
		height=height*18+50;

		Toolkit toolkit =  Toolkit.getDefaultToolkit ();
		Dimension dim = toolkit.getScreenSize();
		if(dim.height-150<height) {
			height=dim.height-150;
		}
		setBounds(100, 0, 450, height);

		JTextArea textArea = new JTextArea(s);
		contentPanel = new JScrollPane(textArea);
		getContentPane().add(contentPanel);
	}

}
