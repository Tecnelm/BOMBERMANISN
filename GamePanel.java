

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

public class GamePanel extends Panel implements KeyListener  {
	
	private static final long serialVersionUID = 1L;
	private int largeur = 31,hauteur = 21; // nombre de bloc
	private int[][] grille = new int[hauteur][largeur]; // grille de jeu
	private int ID; // ID du panel

	private Bloc[][] blocL = new Bloc [hauteur][largeur]; // une liste de bloc pour modifier le visuelle 
	private Bloc bloc;
	private Joueur player;
	private ArrayList<Joueur> joueurL = new ArrayList<>(); // la liste de joueur
	private boolean restart = false;
	private Clip clip;
	private int[][] grilleBonus = new int [hauteur][largeur];
	private JLabel [][] bonusIL = new JLabel[hauteur][largeur];
	private  URL[] urlB = {
			getClass().getResource("/Image/Bonus/BombUp.jpg"),
			getClass().getResource("/Image/Bonus/FlameUp.jpg"),
			getClass().getResource("/Image/Bonus/Invincibility.jpg"),
			getClass().getResource("/Image/Bonus/PowerFULL.jpg")
	};
	private  ImageIcon[] imgB = new ImageIcon[bonusIL.length];
	private int nbPLayerAlive;
	
	private boolean start = false;
	private boolean needRest = false;
	
		
	/*-----------------------------------------------------------//
					Génération du jeu de base lors du alncement 
	//-----------------------------------------------------------*/

	public GamePanel(int ID) {
		this.setLayout(null); // permet la position absolu
		this.setBackground(Color.GRAY); // met le fond en gris 
		this.setPreferredSize(new Dimension(Settings.largeurF, Settings.hauteurF));
		this.ID = ID;
		createBonus();
		createGrille();
		placePlayer(1,1,'Z','S','Q','D','X',1,1); // crée un joueur avec ces touches que l'ont positionne a la position 1,1 du quadrillage 
		placePlayer(2,2,'U','J','H','K','N',9,9);
		prepareMusic();
		initImageBonus();
		
		this.addKeyListener(this);
		this.updateUI();
		
	}
	
	/*
	 * permet d'ajouter un joueur
	 */
	@Override 
	public void placePlayer(int texture ,int id,int up , int down , int left , int right,int bombe , int caseBx,int caseBy)
	{
			try { // suprime les case en crois autour du personnage si ce sont des cassable  haut bas gauche droite  et change les id dans la grille 
			if (grille[caseBy][caseBx] ==Settings.idcaseCassable) {
				grille[caseBy][caseBx] = Settings.idcaseJ;
				this.remove(blocL[caseBy][caseBx].getBloc());
				checkplaceP(-1,0,caseBx,caseBy);
				checkplaceP(1,0,caseBx,caseBy);
				checkplaceP(0,1,caseBx,caseBy);
				checkplaceP(0,-1,caseBx,caseBy);	
				//del1();
				player = new Joueur( texture,id , up ,  down ,  left ,  right,bombe ,  caseBx, caseBy ); // cree le joueur 
				joueurL.add(player);// ajoute a la liste de joueur 
				
				this.add(player.getImg());
				this.updateUI(); // met a jour l'image 

			}
			}catch(Exception e ) {};

	}
	/*
	 * verification d'une case si ont peut suprimer la case et le fais dans ce cas pour permettre le placement du joueur 
	 */
	private void checkplaceP(int dirx,int diry,int caseBx,int caseBy)
	{
		if (grille[caseBy+diry][caseBx+dirx] ==Settings.idcaseCassable) {
			grille[caseBy+diry][caseBx+dirx] = Settings.idcaseVide;
			this.remove(blocL[caseBy+diry][caseBx+dirx].getBloc());
			this.delBonus(caseBx+dirx, caseBy+diry);}
	}
	/*
	 *retourne l'identifiant du panel
	 */
	@Override
	public int getID() {
		
		return ID;
	}
	/*
	 * permet de changer la grille entièrement a partir d'une autre 
	 */
	
	@Override
	public void setGrille(int [][]grille)
	{
		this.grille = grille;
	}
	/*
	 * permet de récupérer la grille  de jeu
	 */
	@Override
	public int[][] getGrille() {

		return grille;
		
	}


	/*
	 * (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 * gestion des evenements des touche clavier 
	 */
	@Override
	public void keyPressed(KeyEvent e) {

		for(int i =0 ; i<joueurL.size();i++) {
			
			if(e.getKeyCode()== joueurL.get(i).getUp()) {
				joueurL.get(i).setUP(true);
				
			}
			
			if(e.getKeyCode() == joueurL.get(i).getDown()) {
				joueurL.get(i).setDOWN(true);
			}
			
			if(e.getKeyCode() == joueurL.get(i).getRight()) {
				joueurL.get(i).setRIGHT(true);
			}
			
			if(e.getKeyCode() == joueurL.get(i).getLeft()) {
				joueurL.get(i).setLEFT(true);
			}
			if(e.getKeyCode() == joueurL.get(i).getBombe()) {
				joueurL.get(i).setBOMBE(true);
			}
			
			
		}
		
		
		
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
		for(int i =0 ; i< joueurL.size() ; i++) {
			
			if(e.getKeyCode() == joueurL.get(i).getUp()) {
				joueurL.get(i).setUP(false);	
			}
			
			if(e.getKeyCode() == joueurL.get(i).getDown()) {
				joueurL.get(i).setDOWN(false);			
			}
			
			if(e.getKeyCode() == joueurL.get(i).getRight()) {

				joueurL.get(i).setRIGHT(false);		
			}
			
			if(e.getKeyCode() == joueurL.get(i).getLeft()) {
				joueurL.get(i).setLEFT(false);				
			}
			if(e.getKeyCode() == joueurL.get(i).getBombe()) {
				joueurL.get(i).setBOMBE(false);
			}
			
			
		}
		if(e.getKeyChar()==KeyEvent.VK_ESCAPE&& !restart ) {
			this.pause();
			Frame.getInstance().setContentPane(PanelManager.getInstance().getMenu());
			Frame.getInstance().pack();
			clip.stop();
			PanelManager.getInstance().getMenu().start_music();
			PanelManager.getInstance().getMenu().requestFocus();
			
			
			
		}

	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	/*
	 * recupère l'un des joueur via son identifiant 
	 */
	@Override
	public Joueur getJoueur(int ID)
	{
		for(Joueur j : joueurL)
			if(j.getID()==ID)
				return j;
		return null;
	}
	//permet de suprimer un joueur 
	@Override
	public void removeJoueur(int ID)
	{
		Joueur j;
		for (int i =0 ; i< joueurL.size() ; i++)
		{
			if(joueurL.get(i).getID()==ID)
			{ j  =joueurL.get(i);
				j.delete();
				joueurL.remove(i);
			}
		}
	}

	@Override
	public void delBloc(int x, int y) {
		try {
					this.remove(blocL[y][x].getBloc());

		}catch(Exception a)
		{
			System.err.println("le bloc n'existe pas dans la grille");
			/*
			 * si pb a cette endroit faire un tableau de boolean et verifier si la case existe encore 
			 * danger si n existe plus peut causer des erreur
			 */
		}
		
	}

	/*
	 * permet de "tuer" un personnage dans le case ou il est sur une case en explosion 
	 */
	@Override
	public void killP(int x, int y) {
		for(Joueur j : joueurL) {
			if((j.getCaseJx()==x&&j.getCaseJy()==y)||(j.getnewJx()==x&&j.getnewJy()==y)) {
				if(!j.stillAlive()) {
					j.kill();
					this.remove(j.getImg());
					
					nbPLayerAlive--;
					if(nbPLayerAlive <= 1)
						new Timer().schedule(new TimerTask() {
							
							@Override
							public void run() {
								restart();
								
							}
						}, 1500);
					this.updateUI();
				}
			}		
		}
	}
	//relancer la partie remetttre a 0 la carte et les bonus 
	@Override
	public void restart() {
		restart = true;
		nbPLayerAlive= joueurL.size();
		for(Bloc[]a:blocL)
			for(Bloc b :a)	
				this.remove(b.getBloc());
		
		for(JLabel l []: bonusIL)
			for(JLabel j : l)
				if(j!=null)
						this.remove(j);
		
		
		int  id=1 ,id2 = 1 ;
		this.grille = new int[hauteur][largeur];
		for (int H = 0 ; H < hauteur ; H++ ) {
			id = 1;
			for(int L =0 ; L<largeur ; L++) {
				if( H ==0 || H == hauteur-1 || L==0|| L == largeur-1 ) {// fais le contour du tableau en bloc incassable
					grille[H][L] = Settings.idcaseIncassable;
				}
				else {
					if(id2!=0) { // une ligne sur deux et un bloc sur deux dans cette ligne met un bloc incassable
						
						
						switch(id)
						{
							case 0: this.grille[H][L] = Settings.idcaseIncassable;break;
							case 1: this.grille[H][L] = Settings.idcaseCassable;break;
						}
						id = (id+1)%2;
						}
					else
					{
						this.grille[H][L] = Settings.idcaseCassable; // fais une ligne cassable
						
					}	
				}
				
				this.add(blocL[H][L].getBloc()); // ajoute le bloc a l'image dans la fenetre
				
			}
			id2 = (id2+1)%2;
			
			
		}			
		createBonus();
		
		for(Joueur j : joueurL)
		{
			j.resurect();	
		}
		
		clip.loop(clip.LOOP_CONTINUOUSLY);
		this.updateUI();
		restart = false;
		
	}
	//permet de crée une grille de jeu (la première lors du lancement
	private void createGrille()
	{
		int  id=1 ,id2 = 1 ;
		grille = new int[hauteur][largeur];
		for (int H = 0 ; H < hauteur ; H++ ) {
			id = 1;
			for(int L =0 ; L<largeur ; L++) {
				if( H ==0 || H == hauteur-1 || L==0|| L == largeur-1 ) {// fais le contour du tableau en bloc incassable
					grille[H][L] = Settings.idcaseIncassable;
					bloc = new Bloc(Settings.idcaseIncassable,L,H); // creation d'un bloc puis stocké
				}
				else {
					if(id2!=0) {
						// une ligne sur deux et un bloc sur deux dans cette ligne met un bloc incassable
						switch(id)
						{
						case 0:	grille[H][L] = Settings.idcaseIncassable;
								bloc = new Bloc(Settings.idcaseIncassable,L,H);break;
						case 1 :grille[H][L] = Settings.idcaseCassable;
								bloc = new Bloc(Settings.idcaseCassable,L,H);break;
								
						}
						
						id = (id+1)%2;
						}
					
					else
					{
						grille[H][L] = Settings.idcaseCassable; // fais une ligne cassable
						bloc = new Bloc(Settings.idcaseCassable,L,H);
					}	
				}
				this.add(bloc.getBloc()); // ajoute le bloc a l'image dans la fenetre
				blocL[H][L] = bloc; // ajoute le bloc dans la liste de bloc
			}
			id2 = (id2+1)%2;
			
		}
	}
	//crée  la grille de bonus 
	private void createBonus()
	{
		int bonus,id=0;
		for (int y =0 ; y<hauteur ; y++)
		{
			for(int x =0 ; x<largeur ; x++)
			{
				bonus =  (int)(Math.random() * (101));
				
				if(bonus >50)		id=SettingsBonus.ID_NOBONUS;
				
				else if(bonus >40)	id=SettingsBonus.ID_BONUS_BOMBUP;
				
				else if(bonus >30)	id=SettingsBonus.ID_BONUS_FLAME_UP;
				
				else if(bonus >25 && bonus <27)	id=SettingsBonus.ID_BONUS_POWERFULL;
				
				else if(bonus >15)	id=SettingsBonus.ID_BONUS_SHIELD;	
				else				id = SettingsBonus.ID_NOBONUS;
				
				grilleBonus[y][x] = id;
				
			}
		}
		
		
	}
	//fonction pour les testes laisse uniquement les bloc incassable 
	private void del1()
	{
		for (int i =0 ; i<hauteur ; i++) // elève tous les bloc solide sauf les dur
		for(int a = 0 ; a<largeur ;a++ )
		{
			if(grille[i][a] ==Settings.idcaseCassable) {
				grille[i][a] =Settings.idcaseVide;	
				this.remove(blocL[i][a].getBloc());}
		}
	}
	
	// lance la partie
	@Override
	public void start() {
		for(Joueur j : joueurL)
			j.start();	
		start = true;
		needRest = false;
		nbPLayerAlive= joueurL.size();
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	//prepare la music
	private void prepareMusic()
	{
		
		try {
			AudioInputStream audioIn=AudioSystem.getAudioInputStream(getClass().getResource("/Music/Bomberman.wav"));
			
			
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			FloatControl gainControl = 
				    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(-20.0f); 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		};
	}
	//recherche une bombe parmis les divers joueur pour la faire exploser 
	@Override
	public synchronized void findBombe(int x, int y,boolean otherexpHO, boolean otherexpVE) {

		for (Joueur J : joueurL)
			J.findBmb(x, y,otherexpHO,otherexpVE);
		
	}
	//changer un élément de la grille de jeu
	@Override
	public synchronized void  changeGrille(int x, int y, int id) {
		grille[y][x]=id;	
	}
	
	//permet de récupérer un élement de la grille de jeu 
	@Override
	public synchronized int getGrille(int x,int y)
	{
		return grille[y][x];
	}
	//modification de la fonctin remove pour la rendre plus sûr
	@Override
	public synchronized void remove(Component comp)
	{
		if (this.isAncestorOf(comp))
		{
			super.remove(comp);
		}
	}
	//permet d'afficher la grille de bonus 
	@Override
	public void aff() {
		for(int i =0 ; i<grilleBonus.length;i++)
		{
			for (int b =0 ; b<grilleBonus[1].length;b++)
				System.out.print(grilleBonus[i][b]);
			System.out.println();
		}
		
	}

	//revoie si une partie est en cours
	@Override
	public boolean isStart() {
		return start;
	}
	@Override
	public void resStart()
	{
		needRest=true;
	}
	//met en pause le jeu 
	private void pause()
	{
		for(Joueur j : joueurL)
		{
			j.pause();
		}
	}
	//relance le jeu
	@Override
	public void resume() {
		for(Joueur j : joueurL)
		{
			j.resume();
		}
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}

	//récupérer un bonus avec des coordonnée 
	@Override
	public int getBonus(int x, int y) {
	
		int idbonus = grilleBonus[y][x];
		if(grilleBonus[y][x]!=SettingsBonus.ID_NOBONUS){
			this.remove(bonusIL[y][x]);
			
		}
		grilleBonus[y][x]=SettingsBonus.ID_NOBONUS;
		
		
		return idbonus;
	}

	//affiche l'image d'un bonus via ses coordonnée 
	@Override
	public void affBonus(int x, int y) {
		int idB = grilleBonus[y][x];
		
		JLabel bonus;
		if(idB!=SettingsBonus.ID_NOBONUS) {
			ImageIcon img=null;
			switch(idB)
			{
				case SettingsBonus.ID_BONUS_BOMBUP:
					img = imgB[SettingsBonus.IMG_BONUS_BOMBUP];break;
				case SettingsBonus.ID_BONUS_FLAME_UP:
					img = imgB[SettingsBonus.IMG_BONUS_FLAME_UP];break;
				case SettingsBonus.ID_BONUS_SHIELD:
					img = imgB[SettingsBonus.IMG_BONUS_SHIELD];break;
				case SettingsBonus.ID_BONUS_POWERFULL:
					img = imgB[SettingsBonus.IMG_BONUS_POWERFULL];break;
			}
			
			bonus = new JLabel(img);
			bonus.setBounds(x*Settings.taillePers, y*Settings.taillePers, Settings.taillePers, Settings.taillePers);
			bonusIL[y][x] = bonus;
			this.add(bonus);
		}
		
	}
	//charge les image des bonus 
	private void initImageBonus()
	{
		
		for (int i = 0 ; i < urlB.length ; i++)
		{			
			imgB[i] =new ImageIcon(new ImageIcon(urlB[i]).getImage().getScaledInstance(Settings.taillePers, Settings.taillePers,Image.SCALE_DEFAULT));

		}
	}

//suprime un bonus 
	@Override
	public void delBonus(int x, int y) {
		
		this.grilleBonus[y][x] = SettingsBonus.ID_NOBONUS;
		
	}

//modifie le volume des bombe
	@Override
	public void changeSoundVolume(float volume) {
		for(Joueur j : joueurL)
		{
			j.changesound(volume);
		}
		
	}

//modifie le volume de la musique 
	@Override
	public void changeMusicVolume(float volume) {
		FloatControl gainControl = 
			    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(volume);
		
	}

	@Override
	public boolean needRestart() {
		
		return needRest;
	}
	
}
