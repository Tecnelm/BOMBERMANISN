

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Frame extends JFrame {
	/**
	 * création de la fenetre
	 */
	private static final long serialVersionUID = 1L;
	private static Frame instance;
	/*
	 * variable d'intance pour récupérer la Frame dans tous le programme 
	 */
	public static Frame getInstance() {
		return instance;
	}

	
	/*
	 * constructeur 
	 * -image de font 
	 * -titre de la fenetre lancement du jeu 
	 * 
	 */
	public Frame(String name) {
		instance = this;
		this.setTitle(name);
		this.setResizable(false);
		try {
			this.setIconImage(ImageIO.read(getClass().getResource("/Image/jeu/Imagecoin.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(new Loading());
		this.pack();
		this.setLocationRelativeTo(null);
		
		this.setVisible(true);
	}


}
