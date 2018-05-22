


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class JoueurPanel extends JPanel implements KeyListener {
	/*
	 * panel des option pour rajouter un joueur choix des cases et des touches 
	 */
	
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private int UP;
	private int DOWN;
	private int LEFT;
	private int RIGHT;
	private int BOMB;
	private int texture;
	private int ID;
	private int temp = '\0';
	private String temps = "";
	private boolean request = false;
	private JButton b ;
	private JoueurPanel j;
	private Thread main;
	private boolean New;
	private Image font;
	private JButton up;
	private JButton down;
	private JButton left;
	private JButton right;
	private JButton bomb;
	private Buttoncustom b1;
	private Buttoncustom b2;
	private JButton remove;
	private boolean Remove=false; // demande de supression 
	private boolean changeCase = false;
	
	//constructeur 1 cas commun 
	public JoueurPanel(int ID)
	{
		this.ID = ID;
		New=true;
		init();

		
	} 
	//cas de base lors du lancement du jeu 
	public JoueurPanel(int ID,Buttoncustom bi , int up,int down,int left,int right,int bomb, int textur)
	{
		this.ID = ID;
		this.UP = up;
		this.DOWN = down;
		this.RIGHT = right;
		this.LEFT = left;
		this.BOMB = bomb;
		this.x = bi.getX();
		this.y =bi.getY();
		
		b= bi.getBut();
		New = false;
		init();
		this.up.setText(Character.toString((char)up).toUpperCase());
		this.down.setText(Character.toString((char)down).toUpperCase());
		this.left.setText(Character.toString((char)left).toUpperCase());
		this.right.setText(Character.toString((char)right).toUpperCase());
		this.bomb.setText(Character.toString((char)bomb).toUpperCase());
		switch(textur)
		{
		case 1 :b2.getBut().setEnabled(false);this.texture = textur;break;
		case 2 : b1.getBut().setEnabled(false);this.texture = textur ;break;
		}
		
	}

	public JButton getB() {
		return b;
	}

	public void setB(JButton b) {
		this.b = b;
	}
	public void setHold()
	{
		this.New = false;
	}
	public  boolean isNew()
	{
		return New;
	}
	/*
	 * renvoie false si il manque une info 
	 */
	public boolean  verifInfo()
	{
		if(x!=0&&y!=0&&UP!=0&&DOWN!=0&&LEFT!=0&&RIGHT!=0&&BOMB!=0&&texture!=0)
		return true;
		return false;
	}
	public void init()
	{
		
		this.setLayout(null);
		this.setPreferredSize(new Dimension(660,640));
		 j =this;
		 try {
				font =ImageIO.read(getClass().getResource("/Image/jeu/ImageOption.jpg"));
				font =font.getScaledInstance(660, 650, Image.SCALE_DEFAULT);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		/*
		 * parametrage de l'aspect grafique des bouton etc...
		 */
		JLabel UP1 = new JLabel("UP: ");
		UP1.setBounds(50, 50, 100, 50);
		JLabel DOWN1 = new JLabel("DOWN: ");
		DOWN1.setBounds(50, 125, 100, 50);
		JLabel RIGHT1 = new JLabel("RIGHT: ");
		RIGHT1.setBounds(50, 200, 100, 50);
		JLabel LEFT1 = new JLabel("LEFT: ");
		LEFT1.setBounds(50, 275, 100, 50);
		JLabel BOMB1 = new JLabel("BOMB: ");
		BOMB1.setBounds(50, 350, 100, 50);
		
		JLabel text[] = 
			{
					UP1,DOWN1,LEFT1,RIGHT1,BOMB1
			};
		Image im[]=BoutonImage.getInstance().getTexture("SIGNAL");
		for(int i  =0 ; i <5 ; i++)
		{
			
			Image temp = im[i];
			
		temp=	temp.getScaledInstance(100, 50, Image.SCALE_DEFAULT);
			text[i].setIcon(new ImageIcon(temp));
		}
		up = new JButton();
		up.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				main =new Thread() {
					public void run() {
						j.requestFocus();
						request = true;
						do
						{
							if(temp == '\0')
								try {
									this.sleep(1);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							else
							{//permet de mofifier la touche du joueur et l'affichaque du bouton 
								up.setText(temps);
								UP = temp;
								temp = '\0';
								temps = "";
								request = false;
							}
									
						}while(request);
						
					}
					
				};
				main.start();
				
				
				
			}
		});
		
		down = new JButton();
		down.addActionListener(new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					main =new Thread() {
						public void run() {
							j.requestFocus();
							request = true;
							do
							{
								if(temp == '\0')
									try {
										this.sleep(1);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								else
								{
									down.setText(temps);
									DOWN = temp;
									temp = '\0';
									temps = "";
									request = false;
								}
										
							}while(request);
							
						}
						
					};
					main.start();
					
				}
			});
		left = new JButton();
		left.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				main =new Thread() {
					public void run() {
						j.requestFocus();
						request = true;
						do
						{
							if(temp == '\0')
								try {
									this.sleep(1);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							else
							{
								left.setText(temps);
								LEFT = temp;
								temp = '\0';
								temps = "";
								request = false;
							}
									
						}while(request);
						
					}
					
				};
				main.start();
				
			}
		});
		
		right = new JButton();
		right.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				main =new Thread() {
					public void run() {
						j.requestFocus();
						request = true;
						do
						{
							if(temp == '\0')
								try {
									this.sleep(1);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							else
							{
								right.setText(temps);
								RIGHT = temp;
								temp = '\0';
								temps = "";
								request = false;
							}
									
						}while(request);
						
					}
					
				};
				main.start();
				
			}
		});
		bomb = new JButton();
		bomb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 main =new Thread() {
					public void run() {
						j.requestFocus();
						request = true;
						do
						{
							if(temp == '\0')
								try {
									this.sleep(1);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							else
							{
								bomb.setText(temps);
								BOMB = temp;
								temp = '\0';
								temps = "";
								request = false;
							}
									
						}while(request);
						
					}
					
				};
				main.start();
				
			

					
					
						
					
				};
				
			
		});
		up.setFocusable(false);
		down.setFocusable(false);
		left.setFocusable(false);
		right.setFocusable(false);
		bomb.setFocusable(false);
		up.setBounds(250, 50, 200, 50);
		down.setBounds(250, 125, 200, 50);
		left.setBounds(250, 275, 200, 50);
		right.setBounds(250, 200, 200, 50);
		bomb.setBounds(250, 350, 200, 50);
		
		b2 = new Buttoncustom(2);
		b1 = new Buttoncustom(1);
		
		b1.getBut().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				b1.getBut().setEnabled(false);
				b2.getBut().setEnabled(true);
				texture = b1.getID();
				
				
			}
		});
		b2.getBut().addActionListener(new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					b2.getBut().setEnabled(false);
					b1.getBut().setEnabled(true);
					texture = b2.getID();
					
					
				}
			});
		b1.getBut().setBounds(200, 450, 72, 72);
		 
		b2.getBut().setBounds(400, 450, 72, 72);
		remove= new JButton("Remove");
		remove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Remove = true;
				
			}
		});
		remove.setBounds(240,540,200,60);
		remove.setFocusable(false);
		
		this.addKeyListener(this);
		this.add(remove);
		this.add(up);
		this.add(down);
		this.add(left);
		this.add(right);
		this.add(bomb);
		
		this.add(UP1);
		this.add(DOWN1);
		this.add(LEFT1);
		this.add(RIGHT1);
		this.add(BOMB1);
		
		this.add(b1.getBut());
		this.add(b2.getBut());
		
	}
//evenement quand oue touche est pressé 
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(request)
		{
			if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
				request = false;
			else
			{
				temp = e.getKeyCode();
				temps = e.getKeyText(temp);
				
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	/*
	 * fonction pour recupérer les divers valeurs 
	 */
	public void setNew(boolean b)
	{
		New=b;
	}
	public int getUP()
	{
		return UP;
	}
	public int getDOWN()
	{
		return DOWN;
	}
	public int getRIGHT()
	{
		return RIGHT;
	}
	public int getLEFT()
	{
		return LEFT;
	}
	public int getBOMB()
	{
		return BOMB;
	}
	public int getCasex()
	{
		return x;
	}
	public int getCasey()
	{
		return y;
	}
	public boolean isRemove()
	{
		return Remove;
	}
	public void setRemove(boolean b)
	{
		Remove = b;
	}
	public int getTexture()
	{
		return texture;
	}
	public int getID()
	{
		return ID;
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * permet d'afficher une image de font 
	 */
	@Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		arg0.drawImage(font,0,0,null);
	}
	/*
	 * permet de mettre a jour la position du personnage 
	 */
	public void updatePos(Buttoncustom b)
	{
		x = b.getX();
		y = b.getY();
		if(this.b != null)
			this.b.setEnabled(true);
		this.b = b.getBut();
		this.b.setEnabled(false);
		changeCase = true;
	}
	public void changeCase(boolean b)
	{
		changeCase = b;
	}
	public boolean getChangeCase()
	{
		return changeCase;
	}
	public void dislaBut()
	{
		b.setEnabled(false);
	}

}
