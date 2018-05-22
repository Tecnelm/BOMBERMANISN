

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Loading extends JPanel {
	/*
	 * cette classe gère le lancement du jeu en multicoeur  via des threads 
	 */
	

	private static final long serialVersionUID = 1L;
	private static Loading instance ;
	/*
	 * tous les panels a charger 
	 */
	private boolean load1 = false;
	private boolean load2 = false;
	private boolean load3 = false;
	
	// permet de recuperer l'instance de loading dans tous le code
	public static Loading getInstance() {
		return instance;
	}
	Image font ;
	/*
	 * constructeur 
	 * lance la création de tous les panels 
	 * image d'attente et gère le changement de lorsque tous les panel sont chargé 
	 */
	public Loading()
	
	{
		instance = this;
		JLabel load = new JLabel("Loading ...");
		load.setBounds(300,200,50,50);
		this.add(load);
		this.setPreferredSize(new Dimension(682,351));
		try {
			
			font =ImageIO.read(getClass().getResource("/Image/jeu/band.jpg"));
			font =font.getScaledInstance(682, 351, Image.SCALE_DEFAULT);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Thread waiting = new Thread() {
			public void run()
			{
				
				PanelManager.getInstance().setup();
				while(!load1||!load2||!load3)
					try {
						this.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				Frame.getInstance().setContentPane(PanelManager.getInstance().getMenu());
				PanelManager.getInstance().getMenu().start_music();
				Frame.getInstance().pack();
				Frame.getInstance().setLocationRelativeTo(null);
			}
		};
		waiting.start();
	}
	@Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		arg0.drawImage(font,0,0,null);
	}
	public void setload1()
	{
		load1 = true;
	}
	public void setload2()
	{
		load2 = true;
	}
	public void setload3()
	{
		load3 = true;
	}

}
