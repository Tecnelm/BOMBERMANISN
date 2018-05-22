public class Bonus {
	
	/*
	 *permet de savoir quels sont les bonus qu'un joueur possède chacun  possède le siens 
	 */
	// valeurs de base pour le pour les joueurs
	private int nbbombeMax =1, rangeBombe = 4,nbcassa = 1 , nbLife =1  ;
	// constructeur 
	public Bonus() {}

	// cette fonction sers lors que l'ont lui rentre un id d'un bonus mettre  se mettre à jour par example nb de bombe max
	public void checkBonus(int bonus)
	{
		
		switch(bonus)
		{
		case SettingsBonus.ID_NOBONUS:break;
		
		case SettingsBonus.ID_BONUS_BOMBUP: 
			if(nbbombeMax<=3)
			nbbombeMax++;break;
			
		case SettingsBonus.ID_BONUS_FLAME_UP:
			bonusBombe();break;
			
		case SettingsBonus.ID_BONUS_POWERFULL:
			rangeBombe = SettingsBonus.NBPOWERFULL;
			nbcassa = rangeBombe-3;break;
			
		case SettingsBonus.ID_BONUS_SHIELD:
			if(nbLife<=2)
			nbLife++;break;
		}
	}
	//fonction pour  si l'id est "flame up"
	private void bonusBombe()
	{
		if(rangeBombe <SettingsBonus.NBPOWERFULL)
		{
			rangeBombe++;
			nbcassa++;
		}
	}
	// ces fonction serve a recupérer les paramettres de l'objet  par example la range ou le nombre de bombe max 
	public int getnbBMax()
	{
		return nbbombeMax;
	}
	public int getRange()
	{
		return rangeBombe;
	}
	public int getnbCassa()
	{
		return nbcassa;
	}
	public int getnbLife()
	{
		return nbLife;
	}
	// reset toutes les valeurs a des valeurs de base par example lorsque l'on relance une partie 
	public void reset() {
		
			nbbombeMax = 1; rangeBombe = 4;nbcassa = 1 ; nbLife = 1;

	}
	// cette fonction sers à modifier le nombre de vie 
	public void setLife()
	{
		nbLife--;
	}
	public void addLife()
	{
		nbLife++;
	}
	// redefinition de la fonction toString de type objet 
	@Override
	public String toString() {
		return "vous avez: "+nbbombeMax+" bombe, une range de: "+rangeBombe+" et: "+nbLife+"vie";
		
	}
	
	
}
