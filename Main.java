


public class Main {

	public static void main(String[] args) {
	/*
	 * lancement du jeu avec la création de la fenetre classe principale de lancement 
	 */BoutonImage.getInstance().setup();
		Thread main = new Thread() {
			public void run () {
				new Frame( "Bomberman"); // instanciation du jeu
			}
		};
		main.start();
	}
}
