
import java.util.Vector;

/**
 * Joueur qui a déjà joué.  Sa stratégie est un peu meilleur que la 
 * première fois qu'il a  joué.
 *
 * Il retient ses coups et ne joue pas deux fois le même.  Il ne fait rien de 
 * spécial s'il est avisé d'avoir touché un navire.
 * 
 * @author Pierre Bélisle
 * @version Copyright A2021
 *
 */
public class JoueurDebutant {
	
	/*
	 * Stratégie : 
	 */
	
	// On aurait pu prendre n'importe quelle collection.
	private Vector tabCoups;
	
	public JoueurDebutant(){
		tabCoups = new Vector();
	}

	/**
	 * 
	 * @return
	 */
	public Coord getTir(){
		
		/*
		 * Stratégie : On obtient un coup pas déjà joué du module de collection
		 * et on l'ajoute à la collection.
		 *                  
		 * La méthode a été mise dans UtilitaireCollection pour réutilisation 
		 */
		Coord c = UtilitaireCollection.obtenirCoupPasDejaJouer(tabCoups);  
				
		tabCoups.add(c);
		
		return c; 
		
	}
	
	public void aviseTouche(Coord c){}	
}
