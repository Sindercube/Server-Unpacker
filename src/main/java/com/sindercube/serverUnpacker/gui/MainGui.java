package com.sindercube.serverUnpacker.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DropTarget;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class MainGui {

	public static final String ICON_PATH = "assets/server_unpacker/icon.png";

	private JButton uploadButton;
	private JProgressBar progressBar;

	public MainGui() {
		JFrame frame = new JFrame("Server Unpacker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(240, 240);
		try {
			InputStream stream = this.getClass().getClassLoader().getResourceAsStream(ICON_PATH);
			assert stream != null;
			BufferedImage image = ImageIO.read(stream);
			frame.setIconImage(image);
		} catch (Exception ignored) {}
		frame.add(createMainPanel());
		frame.setVisible(true);
	}

	public void addUploadButtonActionListener(ActionListener actionListener) {
		uploadButton.addActionListener(actionListener);
	}

	public void setUploadButtonDropTarget(DropTarget dropTarget) {
		uploadButton.setDropTarget(dropTarget);
	}

	public void setProgress(int progress) {
		progressBar.setValue(progress);
	}

	private JPanel createMainPanel() {
		JPanel panel = new JPanel();

		uploadButton = new JButton("Upload File");
		uploadButton.setSize(300,100);
		panel.add(uploadButton);

		JLabel dropInfo = new JLabel("or Drag and Drop");
		panel.add(dropInfo);

		progressBar = new JProgressBar();
		progressBar.setMaximum(100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		panel.add(progressBar);

		return panel;
	}
}
