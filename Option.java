


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;






public class Option extends JPanel{
	/*
	 * Panel des option regroupant 3 panel different  selection de la case  du joueur , paramettre d'un joueur et validation + son
	 */
	

	private static final long serialVersionUID = 1L;
	private ArrayList<JoueurPanel> joueurL = new ArrayList<>();
	private int nbJ = 0 ; 
	private ButtonPanel b;
	private JTabbedPane onglet;
	private PanelOptionOther pO;
	private Image font;
	private Image font1;
	private JButton buttontemp;
	private boolean restart = false;
	private JoueurPanel current;
	// constructeur 
	public Option()
	{ 
		this.setPreferredSize(new Dimension(1280,720));
		this.setLayout(new BorderLayout());
		onglet = new JTabbedPane();
		
		try {
			font =ImageIO.read(getClass().getResource("/Image/jeu/ImageOption.jpg"));
			font =font.getScaledInstance(1300, 740, Image.SCALE_DEFAULT);
			font1 =ImageIO.read(getClass().getResource("/Image/jeu/ImageOption.jpg"));
			font1 =font.getScaledInstance(660, 650, Image.SCALE_DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JPanel panadd = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics arg0) {
				super.paintComponent(arg0);
				arg0.drawImage(font1,0,0,this);
			}
		};
		JButton buttadd = new JButton("Ajouter un joueur");
		buttadd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				nbJ++;
				JoueurPanel j = new JoueurPanel(nbJ);
				joueurL.add(j);
				onglet.addTab("Player N°"+nbJ, j);
				
				
				
			}
		});
		panadd.add(buttadd);
		onglet.addTab("Add player", panadd);
		b = new ButtonPanel();
		
		this.add(b,BorderLayout.WEST);
		createPanel2();
		this.add(onglet,BorderLayout.EAST);
		pO = new PanelOptionOther();
		this.add(pO, BorderLayout.SOUTH);
		this.setFocusable(true);
		buttontemp=b.getButton(1, 1).getBut();
		if(onglet.getSelectedComponent() instanceof JoueurPanel)
		{
			current =(JoueurPanel)onglet.getSelectedComponent();
			current.dislaBut();
		}
			onglet.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(onglet.getSelectedComponent() instanceof JoueurPanel)
				{
					buttontemp.setEnabled(true);
					current =(JoueurPanel)onglet.getSelectedComponent();
					if(current.getCasex()!=0)
					{
						buttontemp = current.getB();
						buttontemp.setEnabled(false);
					}
				}
				else 
				{
					buttontemp.setEnabled(true);
				}
				
				
			}
		});
		
		
			
	}
	/*
	 * création des deux onglet des joueur de base crée 
	 */
	private void createPanel2()
	{
		 
		 joueurL.add(new JoueurPanel(1, b.getButton(1, 1),'Z','S','Q','D','X',1));
		 joueurL.add(new JoueurPanel(2, b.getButton(9, 9),'U','J','H','K','N'/*(deux point)*/,2));
		    for(JoueurPanel j : joueurL){
		      nbJ++;
		      onglet.addTab("Player N°"+nbJ, j);
		    }
		    
	}
	/*
	 * permet de suprimer un joueur 
	 */
	public void delete(JoueurPanel j)
	{
		if(j.getB()!=null)
		j.getB().setEnabled(true);
		joueurL.remove(j);
		onglet.remove(j);
		onglet.updateUI();
	}
	/*
	 * cette fonction lors de la validation verifie que toute les valeurs sont correct (pas de champs non rempli) pour la création d'un joueur 
	 * sinon le suprime 
	 */
	public void valid()
	{
		ArrayList <JoueurPanel> toDel = new ArrayList<>();
		for (JoueurPanel j : joueurL)
		{
			if(!j.isRemove())
			{
				if(j.isNew() && j.verifInfo())
				{
					PanelManager.getInstance().getJPanel(0).placePlayer(j.getTexture(), j.getID(), j.getUP(), j.getDOWN(), j.getLEFT(), j.getRIGHT(), j.getBOMB(), j.getCasex(), j.getCasey());
					j.setNew(false);
					if(!restart)
						restart= true;
					
					
				}
				else if (j.verifInfo()&&j.getChangeCase()){
					PanelManager.getInstance().getJPanel(0).getJoueur(j.getID()).setCase(j.getCasex(), j.getCasey());
					restart = true;
					updateJ(j);
					
					
				}
				else if (j.verifInfo())
				{
					updateJ(j);
					
				}
				else if( !j.verifInfo())
				{
					toDel.add(j);
					
				}
				
				
			}
			else
			{
				PanelManager.getInstance().getJPanel(0).removeJoueur(j.getID());
				if(!restart)
				restart = true;
				toDel.add(j);
			}
			
		}
		for(JoueurPanel j : toDel)
			delete(j);
		toDel.clear();
		if(restart)
		{
			PanelManager.getInstance().getJPanel(0).resStart();
			restart = false;
		}
		Frame.getInstance().setContentPane(PanelManager.getInstance().getMenu());
		Frame.getInstance().pack();
		
		
	}
	/*
	 * annule la supression d'un joueur  ou changement 
	 */
	public void back()
	{
		for(JoueurPanel j : joueurL)
		{
			j.setRemove(false);
			j.changeCase(false);
		}
		Frame.getInstance().setContentPane(PanelManager.getInstance().getMenu());
		Frame.getInstance().pack();
	}
	@Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		arg0.drawImage(font,0,0,this);
	}
	/*
	 * cette fonction va mettre a jour les coordonnée du joueur 
	 */
	public void updatepos(Buttoncustom b)
	{
		if(current instanceof JoueurPanel && onglet.getSelectedComponent() instanceof JoueurPanel)
			current.updatePos(b);
		
	}
	/*
	 * met a jour les touches du joueur 
	 */
	private void updateJ(JoueurPanel j)
	{
		Joueur g = PanelManager.getInstance().getJPanel(0).getJoueur(j.getID());
		
		
		
		g.setBombe(j.getBOMB());
		g.setUp(j.getUP());
		g.setDown(j.getDOWN());
		g.setLeft(j.getLEFT());
		g.setRight(j.getRIGHT());
	}
	
	

}
