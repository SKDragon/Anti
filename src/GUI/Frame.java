package GUI;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * The Main
 * @author Gavin L/Iain
 * @version January 19, 2016
 */
public class Frame extends JFrame
{
	/**
	 * Default Serialization
	 */
	private static final long serialVersionUID = 1L;
	// Global Frame Variables
	private Dimension mainFrameDim = new Dimension(1000, 830);
	private MainPanel mainMenu = new MainPanel();

	/**
	 * Main Constructor
	 */
	public Frame()
	{
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
