

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel implements ActionListener {
	/*
	 * Panel servant au option  c est dans  ce panel que l'ont choisira la position du joueur 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int hauteur =21;
	private int largeur = 31;
	private int tailleBx = 20;
	private int tailleBy = 30;
	private Buttoncustom b;
	private Buttoncustom[][] buttonL = new Buttoncustom [hauteur][largeur];
	private Image font;
	
	/*
	 * constructeur du panel  on deffinit l'image de font et l'ont crée la grille de bouton 
	 */
	public ButtonPanel()
	{
		this.setLayout(null);
		createGrille();
		this.setPreferredSize(new Dimension(620,600));
		try {
			font =ImageIO.read(getClass().getResource("/Image/jeu/ImageOption.jpg"));
			font =font.getScaledInstance(620, 700, Image.SCALE_DEFAULT);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		this.updateUI();
		
	}
	/*
	 * fonction de la creation de la grille de bouton fonction de la même manière que celle pour crée les blocs dans GamePanel
	 */
	private void createGrille()
	{
		int  id=1 ,id2 = 1 ;
		for (int H = 0 ; H < hauteur ; H++ ) {
			id = 1;
			for(int L =0 ; L<largeur ; L++) {
				if( H ==0 || H == hauteur-1 || L==0|| L == largeur-1 ) {// fais le contour du tableau en bloc incassable
					b= new Buttoncustom(0,L,H,tailleBx,tailleBy); // creation d'un bloc puis stocké
					b.getBut().setEnabled(false);
				}
				else {
					if(id2!=0) {
						// une ligne sur deux et un bloc sur deux dans cette ligne met un bloc incassable
						switch(id)
						{
						case 0:
							b=  new Buttoncustom(id,L,H,tailleBx,tailleBy);
							b.getBut().setEnabled(false);break;
						case 1 :
							b=  new Buttoncustom(id,L,H,tailleBx,tailleBy);break;
								
						}
						
						id = (id+1)%2;
						}
					
					else
					{
						
						b=  new Buttoncustom(1,L,H,tailleBx,tailleBy);
					}	
				}
				b.getBut().addActionListener(this);
				this.add(b.getBut()); // ajoute le bloc a l'image dans la fenetre
				b.getBut().setBounds(L*tailleBx, H*tailleBy, tailleBx, tailleBy);
				buttonL[H][L]=b; // ajoute le bloc dans la liste de bloc
			}
			id2 = (id2+1)%2;
			
		}
	}
	/*
	 * permet de recupérer un bouton permet de savoir quel est le boutons choisi  et d'avoir ses coordonnée par la suite
	 */
	public Buttoncustom getButton(int x , int y)
	{
		return buttonL[y][x];
	}
	/*
	 *action a realiser lors que le bouton est activé 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		for(Buttoncustom a[]:buttonL)
			for(Buttoncustom b : a)
				if(e.getSource()==b.getBut())
				{
					PanelManager.getInstance().getOption().updatepos(b);
				}

	}
	/*
	 * font de panel 
	 */
	@Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		arg0.drawImage(font,0,0,this);
	}
}
