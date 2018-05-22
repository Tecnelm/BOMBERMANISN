

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;




public class Joueur  {
	private int UUID;
	private int caseJx, caseJy , newX,newY;
	private int caseInitX , caseInitY;
	private int up , down , left , right, bombe;
	private boolean UP , DOWN , LEFT , RIGHT , BOMBE;
	private int taille = Settings.taillePers;
	
	private int nbbombe = 0;
	
	private ImageIcon imUp ;
	private ImageIcon imDown;
	private ImageIcon imRight;
	private ImageIcon imLeft ;
	
	private ImageIcon mvtUp ;
	private ImageIcon mvtDown;
	private ImageIcon mvtRight;
	private ImageIcon mvtLeft;
	private int [][]grille;
	
	private JLabel imgJoueur = new JLabel();
	
	private Thread depla;
	private Thread placeB;
		
	public Thread getdepla() {
		return depla;
	}
	private boolean kill ;

	private ArrayList<Bombe> bombeL = new ArrayList<>();
	private boolean reset;
	private boolean deplac;
	private Panel g ;
	private Bonus bonus;
	private float volume = -20;
	/*
	 * constructeur 
	 */
	public Joueur(int texture,int id ,int up , int down , int left , int right ,int bombe, int caseBx,int caseBy  )
	{
		this.UUID = id;
		this.up = up; this.down = down; this.left = left ; this.right = right; this.bombe = bombe;
		this.caseJx = caseBx ; this.caseJy = caseBy;this.newX = caseBx;this.newY=caseBy;
		this.caseInitX = caseBx;this.caseInitY = caseBy;
		
		bonus = new Bonus();
		
		
		//chargement des images 
		imUp = new ImageIcon(new ImageIcon(getClass().getResource("/Image/pers"+texture+"/avant/Persoh.jpg") ).getImage().getScaledInstance(taille, taille, Image.SCALE_DEFAULT));
		imDown = new ImageIcon(new ImageIcon(getClass().getResource("/Image/pers"+texture+"/arriere/Persob.jpg") ).getImage().getScaledInstance(taille, taille, Image.SCALE_DEFAULT));
		imRight = new ImageIcon(new ImageIcon(getClass().getResource("/Image/pers"+texture+"/droite/Persod.jpg") ).getImage().getScaledInstance(taille, taille, Image.SCALE_DEFAULT));
		imLeft = new ImageIcon(new ImageIcon(getClass().getResource("/Image/pers"+texture+"/gauche/Persog.jpg") ).getImage().getScaledInstance(taille, taille, Image.SCALE_DEFAULT));
		
		mvtUp = new ImageIcon(new ImageIcon(getClass().getResource("/Image/pers"+texture+"/avant/Persohg.gif") ).getImage().getScaledInstance(taille, taille, Image.SCALE_DEFAULT));
		mvtDown = new ImageIcon(new ImageIcon(getClass().getResource("/Image/pers"+texture+"/arriere/Persobg.gif") ).getImage().getScaledInstance(taille, taille, Image.SCALE_DEFAULT));
		mvtRight = new ImageIcon(new ImageIcon(getClass().getResource("/Image/pers"+texture+"/droite/Persodg.gif") ).getImage().getScaledInstance(taille, taille, Image.SCALE_DEFAULT));
		mvtLeft = new ImageIcon(new ImageIcon(getClass().getResource("/Image/pers"+texture+"/gauche/Persogg.gif")).getImage().getScaledInstance(taille, taille, Image.SCALE_DEFAULT));
		
		imgJoueur.setIcon(imDown);	
		imgJoueur.setBounds(caseBx*taille, caseBy*taille, taille, taille);
		kill = true;
		reset = false;	
		//lance une méthode en parallèle gérant le placement d'une bombe 
		 placeB = new Thread() {
			
			public void run() {
				
				while(true) {
					while(kill)
					{
						try {
							this.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				if(BOMBE)
				{
					if(nbbombe < bonus.getnbBMax()) {
						int casexB = (int)Math.round(((double)getImg().getX()/(double)taille)+0.1) , caseyB=(int)Math.round(((double)getImg().getY()/(double)taille)+0.1);
						
						
						if(PanelManager.getInstance().getJPanel(Settings.PanelGame).getGrille(casexB, caseyB) !=Settings.idcaseBombe) {
							nbbombe++;
							PanelManager.getInstance().getJPanel(Settings.PanelGame).changeGrille(casexB, caseyB, Settings.idcaseBombe);
							bombeL.add(new Bombe( UUID,nbbombe,1,2000,casexB,caseyB,bonus,volume));
							
						}
						
						try {
							this.sleep(100);
						} catch (InterruptedException e) {
							
							e.printStackTrace();
						}
					}
					else {
						try {
							this.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						};
					}
				} 
				else {
					try {
						this.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
}
			}}
		};
		placeB.start();
		/*
		 * lancement d'un"programme" en parallèle  gérant le déplacement 
		 */
		depla = new Thread() {
			public void run() {
				/*
				 * a adapter avec les settings
				 */
				while(true) {
					while(kill)
					{
						try {
							this.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					if(UP||DOWN||LEFT||RIGHT)
					{
						if(UP) 	{
							
							translate(0,-1,Settings.avant);
							
							if(!UP)
								changeImage(Settings.avant, Settings.id_move_typeoff); 
							}
						
						if(DOWN) {
							translate(0,1,Settings.back);
							if(!DOWN)
								changeImage(Settings.back, Settings.id_move_typeoff); }
						
	
						
						if(RIGHT) {
							translate(1,0,Settings.right);
							if(!RIGHT)
								changeImage(Settings.right, Settings.id_move_typeoff); }
	
						
						if(LEFT) {
							translate(-1,0,Settings.left);
							if(!LEFT)
								changeImage(Settings.left, Settings.id_move_typeoff); }
						
					}
					
					else
						try {
							this.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
				}		
			}
		};
		depla.start();
		
	}
	/*
	 * cette fonction permet d'afficher le bon GIF de déplacement 
	 */
	public void changeImage(int deplacement ,int mvt)
	{
		if(mvt == Settings.id_move_typeOn)
			switch(deplacement)
			{
				case Settings.avant :imgJoueur.setIcon(mvtUp);break;
				case Settings.back :imgJoueur.setIcon(mvtDown);break;
				case Settings.right :imgJoueur.setIcon(mvtRight);break;
				case Settings.left :imgJoueur.setIcon(mvtLeft);break;
			}
		else
			switch(deplacement)
			{
				case Settings.avant :imgJoueur.setIcon(imUp);break;
				case Settings.back :imgJoueur.setIcon(imDown);break;
				case Settings.right :imgJoueur.setIcon(imRight);break;
				case Settings.left :imgJoueur.setIcon(imLeft);break;
			}
	}

	// place le joueur 
	public void setCase(int caseBx,int caseBy)
	{
		caseJx = caseBx;
		caseJy=caseBy;
		newX=caseBx;
		newY=caseBy;
		caseInitX = caseBx;
		caseInitY = caseBy;
		g = PanelManager.getInstance().getJPanel(0);
		grille = g.getGrille();
		
		//PanelManager.getInstance().getJPanel(Settings.PanelGame).aff();
		try { // suprime les case en crois autour du personnage si ce sont des cassable  haut bas gauche droite  et change les id dans la grille 
			if (grille[caseBy][caseBx] ==Settings.idcaseCassable) {
				
				grille[caseBy][caseBx] = Settings.idcaseJ;
				g.delBonus(caseBx, caseBy);
				g.delBloc(caseBx, caseBy);
				checkplaceP( 1, 0, caseBx, caseBy, g);
				checkplaceP( -1, 0, caseBx, caseBy, g);
				checkplaceP( 0, 1, caseBx, caseBy, g);
				checkplaceP( 0, -1, caseBx, caseBy, g);
				g.setGrille(grille);
			}
			}catch(Exception e ) {};
		imgJoueur.setIcon(imDown);	
		imgJoueur.setBounds(caseBx*taille, caseBy*taille, taille, taille);
		
		
	}
	/*
	 * meme fonction que dans le Gamepanel crée la place de spawn du joueur
	 */
	private void checkplaceP(int dirx,int diry,int caseBx,int caseBy,Panel g)
	{
		if (grille[caseBy+diry][caseBx+dirx] !=Settings.idcaseIncassable) {
			grille[caseBy+diry][caseBx+dirx] = Settings.idcaseVide;
			g.delBloc(caseBx+dirx, caseBy+diry);
			g.delBonus(caseBx+dirx, caseBy+diry);}
	}
	
	
	public int getID()
	{
		return UUID;
		
	}
	/*
	 * permet le déplacmenent du joueur 
	 */
	 public void translate(int dirx , int diry , int iddep){ // deplacement (pas vraiment fini)
		 try {	
			
				if(PanelManager.getInstance().getJPanel(Settings.PanelGame).getGrille(caseJx+dirx, caseJy+diry) ==Settings.idcaseVide||PanelManager.getInstance().getJPanel(0).getGrille(caseJx+dirx, caseJy+diry) ==Settings.idcaseJtemp) { // regarde si la case  dans la direction est inderdite si elle ne l'est pas alors fais le deplacement
					deplac = true;
					
					PanelManager.getInstance().getJPanel(Settings.PanelGame).changeGrille(caseJx+dirx,caseJy+diry, Settings.idcaseJ);
					
					if(PanelManager.getInstance().getJPanel(Settings.PanelGame).getGrille(caseJx, caseJy) != Settings.idcaseBombe)
						PanelManager.getInstance().getJPanel(Settings.PanelGame).changeGrille(caseJx,caseJy, Settings.idcaseJtemp);
					bonus.checkBonus(PanelManager.getInstance().getJPanel(0).getBonus(caseJx, caseJy));
					
					newY = caseJy+diry;
					newX = caseJx+dirx;
					
					
			
					changeImage(iddep, Settings.id_move_typeOn);
								
					for (int i = 1 ; i <=taille; i++) {
						if(!reset&&!kill)
							
							imgJoueur.setBounds(caseJx*taille+i*dirx, caseJy*taille+i*diry, taille, taille);
						else
							break;
						depla.sleep(8);
					}				
					if(!reset)
					{
						if (PanelManager.getInstance().getJPanel(Settings.PanelGame).getGrille(caseJx, caseJy)==Settings.idcaseJtemp)
							PanelManager.getInstance().getJPanel(Settings.PanelGame).changeGrille(caseJx,caseJy, Settings.idcaseVide);
						caseJy+=diry;// changement de la case de joueur  ne pas faire rester sur la case théorique 
						caseJx+=dirx;
					
						
					}
					
					deplac= false;
					

					
				}
				else if(PanelManager.getInstance().getJPanel(Settings.PanelGame).getGrille(caseJx+dirx, caseJy+diry) ==Settings.idcaseExp)
					PanelManager.getInstance().getJPanel(0).killP(caseJx, caseJy);
				else {
					changeImage(iddep, Settings.id_move_typeoff); 
					}				
			  }catch(Exception e) {e.getMessage();};
				 
	 }
	 public void kill()
	 {
		 kill = true;
		 while(deplac)
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}; 
	 }
	 /*
	  * remet en vie un joueur 
	  */
	 public void resurect()
	 {
		 kill = true;
		 reset = true;
		 this.nbbombe = 0;
		 for(Bombe b: bombeL) {
			 b.stop(false);}
		 while(deplac)
			 try{
				 Thread.sleep(10);
			 }catch(Exception e) {
				 e.printStackTrace();
			 };
		 setCase(caseInitX, caseInitY);
		 bonus.reset();
		 reset = false;
		 kill = false;
		 PanelManager.getInstance().getJPanel(Settings.PanelGame).add(getImg());
		 PanelManager.getInstance().getJPanel(Settings.PanelGame).updateUI();
		 
	 }
	 // ce sont les boolean pour deplacement pourvoir les recuperer et les modifier

	 public void resume()
	 {
		 kill = false;
	 }
	 /*
	  * suprime un joueur
	  */
	 public void delete() {
		depla.stop();
		placeB.stop();
		pause();
		PanelManager.getInstance().getJPanel(Settings.PanelGame).remove(getImg());
		PanelManager.getInstance().getJPanel(Settings.PanelGame).updateUI();
	}
	 public void start() {
		kill = false;
	 }
	 /*
	  * fais exploser une bombe via ses coordonnée 
	  */
	 public void findBmb(int x,int y,boolean otherexpHO,boolean otherexpVE) {
		 Bombe temp = null;
		 for(Bombe b : bombeL)
		 {
			 if(b.getPosX()==x&&b.getPosY()==y) {
				 b.allow=false;
				 b.getTimer().cancel();
				 b.getTimer().purge();
				 b.song(false);
				 b.setautorImage(otherexpHO, otherexpVE);
				 b.explode();
				 temp = b;
				 
			 }
		 }
		 this.setNbBombe(temp);
	 }
	 /*
	  * met le joueur en pause
	  */
	 public void pause()
	 {
		
		 for(Bombe b: bombeL)
			 b.stop(true);
		 bombeL.clear();
		 this.nbbombe = 0;
		 while(deplac)
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 kill = true;
	 }
	 /*
	  * permet de modifier le nombre de bombe posé par le joueur 
	  */
	 public void setNbBombe(Bombe b)
	 {
		 if(b!=null)
		 {
			 this.nbbombe =(nbbombe-1);
			 this.bombeL.remove(b);
		 }
		 
		 
		 
	 }
	 /*
	  * permet de recuperer les touches du joueur 
	  */
	 public int getUp() {
		return up;
		 }
	 public int getDown() {
		 return down;
		 }
	 public int getLeft() {
		 return left;
		 }
	public int getRight() {
		 return right;
		 }
	 public int getBombe() {
		 return bombe;
		 }
		 /*
		  * permet de modifier divers valeur du joueur example les touche 
		  */
	 public void setUp(int key) {
		 this.up = key ;
	 }
	 public void setDown(int key) {
		 this.down = key ;
		 }
	 public void setLeft(int key) {
		 this.left = key ;
		 }
	 public void setRight(int key) {
		 this.right = key ;
		 }
	 public void setBombe(int key) {
		 this.bombe = key ;
		 }
	 
	 public void setUP(boolean mvt) {
		 this.UP=mvt;	 
	 }
	 public void setDOWN(boolean mvt) {
		 this.DOWN = mvt;
	 }
	 public void setLEFT(boolean mvt) {
		 this.LEFT = mvt;
	 }
	 public void setRIGHT(boolean mvt) {
		 this.RIGHT = mvt;
	 }
	 public void setBOMBE(boolean pose) {
		this.BOMBE = pose;
	 }	 
	 /*
	  * redeffinit la case du joueur 
	  */
	 public int getCaseJx() {
			return caseJx;
		}
	public int getCaseJy() {
			return caseJy;
		}
		
	public int getnewJx() {
			return newX;		
		}
		
	public int getnewJy() {
			return newY;
		}
	public JLabel getImg() {
		return imgJoueur;
	}
	// retourne si le personnage est mort ou nn 
	public boolean isAlive()
	{
		return kill;
	}
	// retourne si on lui enlève une (au joueur ) il est toujour en vie
	public boolean stillAlive() {
		bonus.setLife();
		return bonus.getnbLife() >0|| kill;
	}
	public void changesound(float volume)
	{
		this.volume = volume;
	}
}
	

