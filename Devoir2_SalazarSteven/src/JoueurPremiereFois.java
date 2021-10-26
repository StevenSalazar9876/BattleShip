
/*
 * Joueur qui joue pour la première fois.  Sa stratégie n'est pas très bonne.
 * 
 *  * 
 * @author Pierre Bélisle
 * @version Copyright A2021

 */
public class JoueurPremiereFois {

	/**
	 * Retourne le coup du joueur
	 * 
	 * @return
	 */
	public Coord getTir(){
		
		/*
		 * Aucune stratégie.  Retourne un coup au hasard et ne regarde même pas 
		 * s'il a déjà joué son coup.
		 *                               
		 * Il ne fait donc rien lorsqu'on l'avise qu'il a touché un navire.
		 */
		return  UtilitaireFonctions.coordAleatoire();
		
	}
	
	public void aviseTouche(Coord c){}	

}
