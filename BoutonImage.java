import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class BoutonImage {
	/*
	 * texture des 3état pour chacun des bouton charge toutes les texture 
	 */
	private static BoutonImage instance = new BoutonImage();
	private BufferedImage sprite[] = new BufferedImage[4] ;
	private int largeur ;
	private int hauteur;
	private int largB;
	private int hautB;
	private int nbLarge = 3;
	private int nbHaut = 6;
	private Map<String, BufferedImage[]> myImg = new HashMap<>(); // une hashmap permet lorsque l'ont donne une"clef" c'est à dire dans ce cas un mot
	// elle renvoie le deuxième paramètre  une liste de Buffered Image
	private String []nombut= {
		ButtonStyle.NEW_GAME.toString(),
		ButtonStyle.RESUME.toString(),
		ButtonStyle.SETTINGS.toString(),
		ButtonStyle.EXIT.toString(),
		ButtonStyle.VALID.toString(),
		ButtonStyle.BACK.toString(),
		
	};
	private URL path[] = new URL[4];
	
	BufferedImage font ;
	public BoutonImage() {}
	/*
	 * initialisation de l'objet 
	 */
	public void setup()
	{
		path[0]= getClass().getResource("Image/jeu/Bouton JeuBase.png");
		path[1]= getClass().getResource("Image/jeu/Bouton JeuOver.png");
		path[2]= getClass().getResource("Image/jeu/Bouton JeuCliked.png");
		path[3] = getClass().getResource("Image/jeu/signal.png");
		try {
			for(int i = 0 ; i <path.length;i++)
			{
				sprite[i] = ImageIO.read(path[i]);
			}
			 
			
			for(int y=0;y<nbHaut;y++)
			{
				BufferedImage  temp[]= new BufferedImage[nbLarge];
				
				for(int x = 0 ; x<nbLarge;x++)
				{
					update(sprite[x],0);
					temp[x] =crop(sprite[x],0,y,largB,hautB); ;
				}
				myImg.put(nombut[y], temp);		
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedImage  temp[]= new BufferedImage[5];
		for(int y = 0 ; y<5 ; y++ )
		{
			update(sprite[3],1);
			temp[y] = crop(sprite[3],0,y,largB,hautB);
		}
		myImg.put("SIGNAL", temp);	
		
		
		
	}
	/*
	 * cette fonction met a jour les caractéristique de l'image (hauteur d'une des image et largeur 
	 */
	private void update(BufferedImage sprite,int id)
	{
		largeur= sprite.getWidth(new ImageObserver() {
			
			@Override
			public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		hauteur= sprite.getHeight(new ImageObserver() {
						
						@Override
						public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
							// TODO Auto-generated method stub
							return false;
						}
					});
		if(id==0) 
			hautB = hauteur/nbHaut;
		else
			hautB = hauteur/5;
		largB = largeur;
		
	}
	/*
	 * renvoi la liste d'image associé au bouton
	 */
	public BufferedImage [] getTexture(ButtonStyle b)
	{
		return myImg.get(b.toString());
	}
	public BufferedImage [] getTexture(String a)
	{
		return myImg.get(a);
	}
	public static BoutonImage getInstance()
	{
		return instance;
	}
	/*
	 * permet de decouper une image 
	 */
	private BufferedImage crop(BufferedImage i,int x,int y,int w,int h)
	{
		return i.getSubimage(x*w, y*h, w, h);
	}

}
