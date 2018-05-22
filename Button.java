

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button extends JButton {
	/*
	 * JButton légèrement modifier pour implmenter le font que l'ont veux 
	 * utilisation d'un callBack Listener qui seras la fonction a faire dans  dans le cas ou l'ont appuis sur le boutton 
	 */
	
	private static final long serialVersionUID = 1L;
	//private ArrayList<ImageIcon> image = new ArrayList<>();
	private final int BASE =0,OVER=1,CLIKED=2;
	private final int taillex = 300,tailley=75;
	private Button but;
	private BufferedImage stateL[] ;
	private ImageIcon state[] =new ImageIcon[3];
	boolean AllowChange = false;
	public Button(ButtonStyle b , CustomListener a) {
		super(b.toString());
		setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setBorderPainted(false);
        but =this;  
        stateL = BoutonImage.getInstance().getTexture(b);
        
        
       for(int i = 0 ; i <3;i++)
        {
    	   
        	state[i] = new ImageIcon(stateL[i].getScaledInstance(taillex, tailley, Image.SCALE_DEFAULT));
        }
       this.setIcon(state[0]);
       /*font = stateL[0];*/
       
		
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				but.setIcon(state[BASE]);
				
				if(AllowChange)
					a.ActionToDo();
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				but.setIcon(state[CLIKED]);
				AllowChange = true;
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				but.setIcon(state[BASE]);
				AllowChange = false;
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				but.setIcon(state[OVER]);
				
				
				
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				
			}
		});	
	}
}
