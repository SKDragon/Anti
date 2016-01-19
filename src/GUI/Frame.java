package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * @author Gavin L
 *
 */
public class Frame extends JFrame {
	// Global Frame Variables
	private Dimension mainFrameDim = new Dimension(1000, 830);
	private MainPanel mainMenu = new MainPanel();
	

	public Frame() {
		super();
		setTitle("Antivirus Defender");
		setPreferredSize(mainFrameDim);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(false);
		add(mainMenu);
		
	}
	
}
