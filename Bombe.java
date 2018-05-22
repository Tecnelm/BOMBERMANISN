

import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Bombe {
	private int ID ;
	private int posX , posY;
	private int taille = Settings.taillePers;
	private Timer time = new Timer();
	
	private Clip clip;
	
	private URL path = getClass().getResource("/Image/bombe/bombe/gifbombe.gif"); // lien de l'image de la bombe lorsqu'elle est posé 
	
	private ImageIcon[] iL= new ImageIcon [7];
	// lien des images des explosions
	private URL []pL = {
			getClass().getResource("/Image/bombe/explosion/Burnm.jpg"),
			getClass().getResource("/Image/bombe/explosion/Burnho.jpg"),
			getClass().getResource("/Image/bombe/explosion/Burnve.jpg"),
			getClass().getResource ("/Image/bombe/explosion/Burnhb.jpg"),
			getClass().getResource("/Image/bombe/explosion/Burnhd.jpg"),
			getClass().getResource ("/Image/bombe/explosion/Burnhg.jpg"),
			getClass().getResource("/Image/bombe/explosion/Burnhh.jpg")
			
	};
	private ArrayList<JLabel>imageHO =  new ArrayList<>();
	private ArrayList<JLabel>imageVE = new ArrayList<>();
	private JLabel mid;
	public boolean allow = true ;
	
	
	private JLabel bombe ;
	private boolean Exp = false;
	private boolean otherexpHO = false;
	private boolean otherexpoVE = false;
	private boolean song = true;
	
	private ArrayList<int[]> cordexp = new ArrayList<>();
	private Bonus bonus;
	/*
	 * création de la bombe 
	 */
	public Bombe(int ID,int bum, int type ,long timePose, int posX , int posY,Bonus b,float volume)
	{
		this.ID = ID;
		this.posX = posX;
		this.posY = posY;
		this.bonus = b;
		
		
		
		bombe = new JLabel(new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(taille, taille, Image.SCALE_DEFAULT)));
		bombe.setBounds(posX*36,posY*36,taille,taille);
		PanelManager.getInstance().getJPanel(Settings.PanelGame).add(bombe);
		time.schedule(new TimerTask() {// lancement du temps a attendre avant de réaliser l'explosion 
			
			@Override
			public void run() {
				
				if(allow) {
					
				explode();
					
				time.cancel();	}			
			}

		}, timePose);
		// charge les images 
		for (int i = 0 ; i<7;i++)
		{
			iL[i] = new ImageIcon(new ImageIcon(pL[i]).getImage().getScaledInstance(taille, taille, Image.SCALE_DEFAULT));
		}
		try {// charge le son
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource("/Music/Bruitage explosion.wav"));
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			FloatControl gainControl = 
				    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(volume); 
			
		} catch (Exception e) {
			e.printStackTrace();
		};
		
		
	}
	/*
	 * fonction pricipale explosion 
	 */
	public void explode() {
		
		if(!Exp) {
			Exp = true;
			
			if(song)clip.start();
			PanelManager.getInstance().getJPanel(Settings.PanelGame).remove(bombe);
			PanelManager.getInstance().getJPanel(Settings.PanelGame).changeGrille(posX, posY, Settings.idcaseVide);	
			explode1();
			if(allow)
				PanelManager.getInstance().getJPanel(Settings.PanelGame).getJoueur(ID).setNbBombe(this);
			PanelManager.getInstance().getJPanel(Settings.PanelGame).updateUI();
		}
		
	}
	/*
	 * fonction declanchant l'explosion  avec gestion des  autres bombes et "recherche" du personnage 
	 */
	private void explode1()
	{
		PanelManager.getInstance().getJPanel(Settings.PanelGame).killP(posX, posY);
		placeExp(ImageID.Milieu,posX,posY,3);
		PanelManager.getInstance().getJPanel(Settings.PanelGame).changeGrille(posX, posY, 5);
		chek_dir_recu(1, 0, posX+1, posY, ImageID.Horizontal,ImageID.Horizontal,Settings.id_HO,0,0);
		chek_dir_recu(-1, 0, posX-1, posY, ImageID.Horizontal,ImageID.Horizontal,Settings.id_HO,0,0);
		chek_dir_recu(0, 1, posX, posY+1, ImageID.Vertival,ImageID.Vertival,Settings.id_VE,0,0);
		chek_dir_recu(0, -1, posX, posY-1, ImageID.Vertival,ImageID.Vertival,Settings.id_VE,0,0);	
		
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {removeAllExp();}
		}, 500);
	
		/*for(int[]c :cordexp) {
			
					PanelManager.getInstance().getJPanel(Settings.PanelGame).findBombe(c[0],c[1],false,false );
		}*/
		// declanchement de l'explosion des autre bombe si elle est sur le passage de la principale 
		for (int i =0 ; i <cordexp.size();i++)
		{
			PanelManager.getInstance().getJPanel(Settings.PanelGame).findBombe(cordexp.get(i)[0],cordexp.get(i)[1],false,false );
		}
		cordexp.clear();
		
	
	
	
	if(this.otherexpHO)
		removeOne(1);
	if(this.otherexpoVE)
		removeOne(2);
	}

	/*
	 * fonction recusive pour ajouter une explosion tuer les personnage qu'il y a sur l'explosion etc...
	 * 
	 */
	private void chek_dir_recu(int dirx,int diry,int posxTab,int posyTab ,int typeIm,int typefin,int type,int nbcase , int nbcaseC)
	{
		
		int IDcaseNext= PanelManager.getInstance().getJPanel(Settings.PanelGame).getGrille(posxTab, posyTab),IDcaseNext1;
		int[] coord = new int[2];
		switch (IDcaseNext)
		{
			case Settings.idcaseIncassable :break;
			case Settings.idcaseCassable :PanelManager.getInstance().getJPanel(Settings.PanelGame).changeGrille(posxTab, posyTab, Settings.idcaseExp);
					PanelManager.getInstance().getJPanel(Settings.PanelGame).delBloc(posxTab, posyTab);	
					PanelManager.getInstance().getJPanel(0).affBonus(posxTab, posyTab);					
					IDcaseNext1=PanelManager.getInstance().getJPanel(Settings.PanelGame).getGrille(posxTab+dirx, posyTab+diry);
					nbcaseC++;
					nbcase++;
					if (IDcaseNext1!=Settings.idcaseIncassable && nbcase <bonus.getRange()&&nbcaseC <bonus.getnbCassa()) {
						
						chek_dir_recu(dirx, diry, posxTab+dirx, posyTab+diry, typeIm, typefin, type,nbcase,nbcaseC);
						placeExp(typeIm,posxTab,posyTab,type);
					}
					else placeExp(typefin,posxTab,posyTab,type); break;
			
			case Settings.idcaseBombe:	if(type == Settings.id_HO)this.otherexpHO=false;else if(type==Settings.id_VE)this.otherexpoVE=false;
					coord[0]=posxTab;coord[1]=posyTab;
					cordexp.add(coord);
			case Settings.idcaseJ:case Settings.idcaseExp:
			case Settings.idcaseJtemp:	PanelManager.getInstance().getJPanel(Settings.PanelGame).killP(posxTab, posyTab);
			case 9 :
			case Settings.idcaseVide:	
					
					PanelManager.getInstance().getJPanel(Settings.PanelGame).changeGrille(posxTab, posyTab, Settings.idcaseExp);
					IDcaseNext1=PanelManager.getInstance().getJPanel(Settings.PanelGame).getGrille(posxTab+dirx, posyTab+diry);
					nbcase++;
					if (IDcaseNext1!=Settings.idcaseIncassable && nbcase <bonus.getRange()&&nbcaseC <bonus.getnbCassa()) {
						
						chek_dir_recu(dirx, diry, posxTab+dirx, posyTab+diry, typeIm, typefin, type,nbcase,nbcaseC);
						if(IDcaseNext!=9)
						{
							placeExp(typeIm,posxTab,posyTab,type);
						}
						
					}
					else 
						if(IDcaseNext !=9)
						placeExp(typefin,posxTab,posyTab,type); break;
					
		}		
	}
	/*
	 * ces fonction permette de récupérer quelque donnée
	 */
	public int getPosX()
	{
		return posX;
	}
	public int getPosY()
	{
		return posY;
	}
	public int getID()
	{
		return ID;
	}
	
	public Timer getTimer() {
		return time;
	}
/*
 * cette fonction set a arreter le processus de la bombe 
 */
	public void stop(boolean pause)
	{
		
		time.cancel();
		time.purge();
		PanelManager.getInstance().getJPanel(Settings.PanelGame).remove(bombe);
		PanelManager.getInstance().getJPanel(Settings.PanelGame).updateUI();
		if(pause)
			PanelManager.getInstance().getJPanel(0).changeGrille(posX, posY, Settings.idcaseVide);
	}
	/*
	 * place une image d'explosion 
	 */
	private void placeExp (int id ,int x,int y,int numdepla)
	{
		JLabel j = new JLabel(iL[id]);
		if(j!=null){
		switch(numdepla)
		{
			case Settings.id_HO :imageHO.add(j);break;
			case Settings.id_VE:imageVE.add(j);break;
			case Settings.id_MID: mid =j;break;
		}
		j.setBounds(x*taille, y*taille, taille, taille);
		PanelManager.getInstance().getJPanel(Settings.PanelGame).add(j);
		}
		
	}
	/*
	 * suprime uniquement un groupe d'image
	 */
	private void removeOne(int id)
	{
		switch(id)
		{
			case Settings.id_HO:	for(JLabel j : imageHO)	PanelManager.getInstance().getJPanel(Settings.PanelGame).remove(j); imageHO.clear(); break;
			case Settings.id_VE:for(JLabel j : imageVE)	PanelManager.getInstance().getJPanel(Settings.PanelGame).remove(j);imageVE.clear(); break;
			
		}
		PanelManager.getInstance().getJPanel(Settings.PanelGame).updateUI();
	}
	/*
	 * permet de bien redefinir les case qui sont en explosion a vide (recursif)
	 */
	private void remp_recu(int dirx,int diry,int posX,int posY) {
		
		int IDcaseNext , lastnbx =posX+dirx ,lastnby =posY+diry;
		IDcaseNext = PanelManager.getInstance().getJPanel(Settings.PanelGame).getGrille(lastnbx, lastnby);
		if( IDcaseNext ==Settings.idcaseExp||IDcaseNext ==9) {
			PanelManager.getInstance().getJPanel(Settings.PanelGame).changeGrille(lastnbx, lastnby, Settings.idcaseVide);
			remp_recu(dirx, diry, lastnbx, lastnby);
			}
	}
	/*
	 * suprime toute les images  d'explosion
	 */
	public void removeAllExp()
	{
		for(JLabel j : imageHO)
		{
			PanelManager.getInstance().getJPanel(Settings.PanelGame).remove(j);
		}
		imageHO.clear();
		for(JLabel j : imageVE)
		{
			PanelManager.getInstance().getJPanel(Settings.PanelGame).remove(j);
		}
		PanelManager.getInstance().getJPanel(Settings.PanelGame).remove(mid);
		imageVE.clear();
		PanelManager.getInstance().getJPanel(Settings.PanelGame).updateUI();	
		PanelManager.getInstance().getJPanel(Settings.PanelGame).changeGrille(posX, posY, Settings.idcaseVide);
		
		remp_recu( 1, 0,posX,posY);
		remp_recu( -1, 0,posX,posY);
		remp_recu( 0, 1,posX,posY);
		remp_recu( 0, -1,posX,posY);
	}
	
	/*
	 * autorisation des sens des image 
	 * Alpha encore des bug
	 */
	public void setautorImage(boolean a,boolean b)
	{
		otherexpHO = a;
		otherexpoVE = b;
	}
	/*
	 * sers à autoriser le song de se jouer
	 */
	public void song(boolean e)
	{
		song = e;
	}


}
