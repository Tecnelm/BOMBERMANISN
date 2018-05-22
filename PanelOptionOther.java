


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class PanelOptionOther extends JPanel {
	/*
	 * cette classe est le 3 eme panel des options. il permet de valider les choix revenir en arrière  valider et changer le volume des explosions ou de la musique 
	 */
	
	private static final long serialVersionUID = 1L;
	Image font;
	public PanelOptionOther()
	{
		this.setPreferredSize(new Dimension(1280, 80));
		this.setLayout(null);
		try {
			font =ImageIO.read(getClass().getResource("/Image/jeu/band.jpg"));
			font =font.getScaledInstance(1290, 80, Image.SCALE_DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createSong();
		Button back = new Button(ButtonStyle.BACK, new CustomListener() {
			
			@Override
			public void ActionToDo() {
				PanelManager.getInstance().getOption().back();
				
				
			}
		});
		Button valid = new Button(ButtonStyle.VALID, new CustomListener() {
					
					@Override
					public void ActionToDo() {
						PanelManager.getInstance().getOption().valid();
						
						
						
					}
				});
		
		back.setBounds(650, -10, 300, 75);
		valid.setBounds(950, -10, 300, 75);
		this.add(back);
		this.add(valid);
		
		
	}
	private void createSong() {
		JLabel Music  = new JLabel("Music :");
		Music.setBounds(10, 5, 200, 15);
		this.add(Music);
		JSlider music = new JSlider();
		music.setMaximum(100);
		music.setMinimum(0);
		music.setValue(30);
		music.setPaintTicks(true);
		music.setPaintLabels(true);
		music.setMinorTickSpacing(10);
		music.setMajorTickSpacing(20);
		music.addChangeListener(new ChangeListener(){
		      public void stateChanged(ChangeEvent event){
		        PanelManager.getInstance().getJPanel(0).changeMusicVolume(map(((JSlider)event.getSource()).getValue(),music.getMaximum(),80));
		        PanelManager.getInstance().getMenu().changeMusicVolume(map( ((JSlider)event.getSource()).getValue(),music.getMaximum(),80));
		    	  
		      }
		    });
		music.setBounds(10, 20, 200, 50);
		this.add(music);
		JLabel Sound  = new JLabel("Sound :");
		this.add(Sound);
		Sound.setBounds(310, 5, 200, 15);
		JSlider sound = new JSlider();
		sound.setMaximum(100);
		sound.setMinimum(0);
		sound.setValue(30);
		sound.setPaintTicks(true);
		sound.setPaintLabels(true);
		sound.setMinorTickSpacing(10);
		sound.setMajorTickSpacing(20);
		sound.addChangeListener(new ChangeListener(){
		      public void stateChanged(ChangeEvent event){
		    	  PanelManager.getInstance().getJPanel(0).changeSoundVolume(map( ((JSlider)event.getSource()).getValue(),music.getMaximum(),80));
		      }
		    });
		sound.setBounds(310, 20, 200, 50);
		this.add(sound);
		
	}
	@Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		arg0.drawImage(font,0,0,this);
	}
	/*
	 *  le volume peut etre réglé entre 6 et -80 je convertis la valeur qui est sur 100 a 80 puis effectue les
	 *   calculs pour avoir une valeur de -74 a 6
	 *  
	 */
	public float map(int val,int max,int newMax)
	{
		float value;
		value = (float)val*(float)newMax/(float)max;
		value = -74.0f+value;
		return value;
	}

}
