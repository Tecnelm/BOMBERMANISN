

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JPanel;

public class MenuPanel  extends JPanel {
	/*
	 * creation du menu avec nouveau jeu 
	 * continue le précédent
	 * les options  
	 */
	
	private static final long serialVersionUID = 1L;
	private Clip clip;
	private Image font;
	
	private float volume = -20;
	
	public MenuPanel()
	{
		super();
		this.setLayout(null);
		setbutton();
		
		this.setPreferredSize(new Dimension(1200, 675));
		try {
			font =ImageIO.read(getClass().getResource("/Image/jeu/ImageMenu.jpg"));
			font.getScaledInstance(1200, 675, Image.SCALE_DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prepare_music();
		
		
	}
	@Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		arg0.drawImage(font,0,0,null);
	}
	/*
	 * lance le chargement de la musique 
	 */
	private void prepare_music()
	{			

		try {
			AudioInputStream audioIn=AudioSystem.getAudioInputStream(getClass().getResource("/Music/music3.mid"));
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
	 * parametrage des boutons 
	 */
	private void setbutton()
	{
		Button new_game = new Button(ButtonStyle.NEW_GAME,new CustomListener() {
			
			@Override
			public void ActionToDo() {
				//System.out.println("NEW_GAME");
				Frame.getInstance().setContentPane(PanelManager.getInstance().getJPanel(0));
				if(!PanelManager.getInstance().getJPanel(0).isStart())
					PanelManager.getInstance().getJPanel(0).start();
				else
				{	
					
					PanelManager.getInstance().getJPanel(0).restart();
					
				}
				PanelManager.getInstance().getJPanel(0).requestFocus();	
				Frame.getInstance().pack();
						
				clip.stop();
				
				
			}
		});
		new_game.setBounds(800, 100, 300, 75);		
		this.add(new_game);
		
		Button resume = new Button(ButtonStyle.RESUME,new CustomListener() {
			
			@Override
			public void ActionToDo() {
				//System.out.println("RESUME");
				
				if(PanelManager.getInstance().getJPanel(0).isStart()&& !PanelManager.getInstance().getJPanel(0).needRestart())
				{
					Frame.getInstance().setContentPane(PanelManager.getInstance().getJPanel(0));
					PanelManager.getInstance().getJPanel(0).resume();
				
					Frame.getInstance().pack();
					PanelManager.getInstance().getJPanel(0).requestFocus();			
					clip.stop();
				}
				
			}
		});
		resume.setBounds(800, 200, 300, 75);		
		this.add(resume);
		
		Button settings = new Button(ButtonStyle.SETTINGS,new CustomListener() {
			
			@Override
			public void ActionToDo() {
				//System.out.println("SETTINGS");
				Frame.getInstance().setContentPane(PanelManager.getInstance().getOption());
				Frame.getInstance().pack();
				PanelManager.getInstance().getOption().requestFocus();
				
			}
		});
		settings.setBounds(800, 300, 300, 75);		
		this.add(settings);
		
		/*Button credit = new Button(ButtonStyle.CREDIT,new CustomListener() {
			
			@Override
			public void ActionToDo() {
				System.out.println("CREDITS");
				
			}
		});
		credit.setBounds(800, 400, 300, 75);		
		this.add(credit);*/
		
		Button exit = new Button(ButtonStyle.EXIT,new CustomListener() {
			
			@Override
			public void ActionToDo() {
			//	System.out.println("EXIT");
				System.exit(0);
				
			}
		});
		exit.setBounds(800, 400, 300, 75);		
		this.add(exit);
		

	}
	/*
	 * cette fonction sers a lancer la musique 
	 */
	public void start_music()
	{
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	/*
	 * sers a changer le volume de la musique
	 */
	public void changeMusicVolume(float volume) {
		FloatControl gainControl = 
			    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(volume);
		
	}
}
