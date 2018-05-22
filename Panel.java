

import javax.swing.JPanel;

public abstract class Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	// cette classe permet de recuperer les fonctions dans toute les classe qu'il etend le gamePanel 
	public abstract int getID();
	public abstract void setGrille(int [][]grille) ;
	public abstract int [][] getGrille();
	public abstract Joueur getJoueur(int ID);
	public abstract void delBloc(int x, int y);
	public abstract void killP(int x ,int y);
	public abstract void removeJoueur(int ID);
	public abstract void start();
	public abstract void findBombe(int x,int y,boolean a,boolean b );
	public abstract void changeGrille(int x,int y,int id);
	public abstract int getGrille(int x,int y);
	public abstract void aff();
	public abstract boolean isStart();
	public abstract void restart();
	public abstract void resume();
	public abstract int getBonus(int x,int y);
	public abstract void affBonus(int x,int y);
	public abstract void delBonus(int x,int y);
	public abstract void changeSoundVolume(float volume);
	public abstract void changeMusicVolume(float volume);
	public abstract void placePlayer(int texture ,int id,int up , int down , int left , int right,int bombe , int caseBx,int caseBy);
	public abstract void resStart();
	public abstract boolean needRestart();
	
	
}