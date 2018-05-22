


import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Buttoncustom extends JButton{
	/*
	 * boutton legèrement modifier avec les texture des bloc permettant un placement des personnages  dynamique  avec des coordonnée 1ere constructeur
	 * 2eme constructeur meme chose mais pour les textures
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private int id;
	private JButton b;
	
	// 1ere constructeur 
	public Buttoncustom(int id,int x,int y,int tailleBx,int tailleBy)
	{
		b=new JButton();
		ImageIcon []imageL  = {
				new ImageIcon(getClass().getResource("/Image/bloc/blocdur.jpg")),
				new ImageIcon(getClass().getResource("/Image/bloc/blocmou.jpg")),
		}; 
		for(int i =0 ; i < imageL.length ; i++)
		{
			imageL[i] = new ImageIcon(imageL[i].getImage().getScaledInstance(tailleBx, tailleBy, Image.SCALE_DEFAULT));
		}
		b.setIcon(imageL[id]);
		this.x=x;
		this.y=y;
	}
	/*
	 * 2eme constructeur sers pour les textures
	 */
	public Buttoncustom(int id)
	{
		this.id = id;
		ImageIcon img =new ImageIcon(new ImageIcon(getClass().getResource("/Image/pers"+id+"/arriere/Persob.jpg") ).getImage().getScaledInstance(72, 72, Image.SCALE_DEFAULT));
		b = new JButton(img);
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getID() {
		return id;
	}
	/*
	 * redéfinition de la methode toString 
	 */
	@Override
	public String toString()
	{
		return (x+"\t"+y);
	}
	public JButton getBut()
	{
		return b;
	}
}
