

import java.util.ArrayList;



// cette classe va gerer le pannel a afficher 
public class PanelManager {
	private ArrayList < Panel > jPanels = new ArrayList < >();
	private MenuPanel menu ;
	private Option option;
	private static PanelManager instance = new PanelManager();
	// permet d'eviter de  regénerer le pannel manageur a chaque fois
	public static PanelManager getInstance() {
		return instance;
	}
	/*
	 * creation de tous les panels  dans des threads differents (coeur) permettant un lancement plus rapide 
	 */
	public void setup() {
		 
		Thread optionT = new Thread() {
			public void run() {
				option = new Option();
				Loading.getInstance().setload1();
			}
		};
		Thread menuT = new Thread() {
			public void run() {
				menu = new MenuPanel();
				Loading.getInstance().setload2();
			}
		};
		Thread gameT = new Thread() {
			public void run() {
				jPanels.add(new GamePanel(Settings.PanelGame));
				Loading.getInstance().setload3();
			}
		};
		optionT.start();
		menuT.start();
		gameT.start();
		
		instance = this;
	}
	

	/*
	 * ces fonction serve a recupérer les divers panel via une seul classe 
	 */
	public ArrayList < Panel > getJPanels() {
		return jPanels;
	}
	
	public Panel getJPanel(int id) { // recherche d'un panel via son ID
		for (Panel panel: jPanels) {
			if (panel.getID() == id) return panel;
		}
		return null;
	}
	public MenuPanel getMenu()
	{
		return menu;
	}
	public Option getOption()
	{
		return option;
	}

	
}
