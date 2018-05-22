

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Bloc  {
	
	
	private int posX, posY;
	private URL path0 =getClass().getResource("/Image/bloc/blocdur.jpg") ;// chemin vers les image
	private URL path1 =getClass().getResource("/Image/bloc/blocmou.jpg") ;
	private int taille = Settings.taillePers; // taille en pixel du bloc
	private JLabel bloc; //l'image du bloc que l'on crée sers a l'affichage
	
	// constructeur de mon objet 
	public Bloc(int ID , int posX , int posY )
	{
		
		
		this.posX = posX;
		this.posY = posY;
		switch(ID)
		{
		
			case Settings.idcaseIncassable :
				bloc = new JLabel(new ImageIcon(new ImageIcon(path0).getImage().getScaledInstance(taille, taille, Image.SCALE_DEFAULT))); break;
				// creation des bloc avec les images via l'utilisation des JLabel de la librairie Swing+ une modification de la taille de l'image
			case Settings.idcaseCassable :
				bloc = new JLabel(new ImageIcon(new ImageIcon(path1).getImage().getScaledInstance(taille, taille, Image.SCALE_DEFAULT))); break;
		}
		bloc.setBounds(posX*taille, posY*taille,taille,taille);
		
		
		
	}
	public JLabel getBloc() // recupère l'image du bloc
	{
		return bloc;
	}
	public int getPosX() // recupère les coordonées du bloc
	{
		return posX;
	}
	public int getPoseY()
	{
		return posY;
		
	}

}
