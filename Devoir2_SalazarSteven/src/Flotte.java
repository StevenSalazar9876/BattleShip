import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Flotte {

	// Liste des navires
	private static ArrayList<Navire> navires = new ArrayList<>();

	// constantes
	private static String NAVIRE_DEJA_SUR_PLACE_EXCEPTION = "Il y a déjà un navire en place";
	private static String POSITION_INVALIDE_EXCEPTION = "Il y a déjà un navire en place";
	private static String AUCUNE_ERREUR_EXCEPTION = "Il n'y a aucune erreur";

	// Constructeur
	/**
	 * @param navires
	 */
	public Flotte(ArrayList<Navire> navires) {

		this.navires = navires;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	
		
		

	}

	/**
	 * @param tir
	 * @return
	 * 
	 *         Méthode qui retourne true si le tir a touché un des navires
	 */
	public boolean dejaRecuCoup(Coord tir) {

		boolean navireTouche = false;

		for (int i = 0; i < navires.size(); i++) {
			if (navires.get(i).mDejaRecuTir(tir) == true) {

				navireTouche = true;
			}
		}
		return navireTouche;

	}

	/**
	 * @return
	 * 
	 *         Méthode qui retourne true si tout les navires sont coulés
	 */
	public boolean jeuTermine() {
		boolean jeuFini = false;
		int countNaviresCoules = 0;

		for (int i = 0; i < navires.size(); i++) {
			if (navires.get(i).mEstCoule() == true) {
				countNaviresCoules++;
			}
		}
		if (countNaviresCoules == navires.size()) {
			jeuFini = true;
		}
		return jeuFini;
	}

	/**
	 * @return
	 */
	public Navire[] getTabNavires() {

		Navire[] naviresTab = (Navire[]) navires.toArray();
		return naviresTab;

	}

	/**
	 * @param tir
	 * @return
	 */
	public boolean leTirTouche(Coord tir) {

		boolean navireTouche = false;

		for (int i = 0; i < navires.size(); i++) {
			if (navires.get(i).mTirATouche(tir) == true) {

				navireTouche = true;
			}
		}
		return navireTouche;

	}

	/**
	 * @param navire
	 * @return
	 * @throws MessagesExceptions
	 * 
	 * 
	 */
	private static int ajouterNavire(Navire navire) throws MessagesExceptions {

		boolean chevauche =false; 
		
		for(int i = 0; i< navires.size(); i++) {
			
			if(navire.mChevauche(navires.get(i))== true) {
				chevauche = true;
			}
		}
		
		// Si les coordonnés du navire sont à l'intérieur de la grille
		if (navire.mVerifierEmplacementNavire() == true) {
			throw new MessagesExceptions(POSITION_INVALIDE_EXCEPTION);

			// Si les coordonnés du navire chevauchent un autre navire
		} else if (chevauche == true) {
			throw new MessagesExceptions(NAVIRE_DEJA_SUR_PLACE_EXCEPTION);
		} else {
			navires.add(navire);
			throw new MessagesExceptions(AUCUNE_ERREUR_EXCEPTION);

		}

	}

	/**
	 * @param nom
	 * @param longueur
	 * @param color
	 * @return
	 * 
	 *         Une méthode NavireobtenirNavireAleatoire(Stringnom,intlongueur,Color
	 *         couleur)qui retourne un navire dont les coordonnées ont été générées
	 *         aléatoirement, validées et ordonnées (haut en bas et de gauche à
	 *         droite). Un bon truc est d’utiliser les messages d’exception que le
	 *         constructeur de la classe Navire lève. Tant qu’il lance des messages,
	 *         on régénère des coordonnées.
	 * @throws MessagesExceptions
	 */
	private static Navire obtenirNavireAleatoire(String nom, int longueur, Color color) {
		boolean navireValide = false;
		Navire navire = null;
		MessagesExceptions e;
		while (navireValide == false) {
			e = null;

			int ligne =  (int) (Math.random() * 9);
			int colonne =  (int) (Math.random() * 9);
			int directionChoix = 1 + (int) (Math.random() * 4);

			Coord coordonneeDebut = new Coord(ligne, colonne);
			Coord coordonneeFin = null;

			String direction = "";

			switch (directionChoix) {
			case 1:
				direction = "SUD";
				coordonneeFin = new Coord(ligne, colonne + longueur);
				break;
			case 2:
				direction = "NORD";
				coordonneeFin = new Coord(ligne, colonne - longueur);
				break;
			case 3:
				direction = "EST";
				coordonneeFin = new Coord(ligne + longueur, colonne);
				break;
			case 4:
				direction = "OUEST";
				coordonneeFin = new Coord(ligne - longueur, colonne);
				break;
			}

			try {
				navire = new Navire(nom, color, coordonneeDebut, coordonneeFin);
			} catch (MessagesExceptions message) {

				e = message;
				// TODO Auto-generated catch block

				e.printStackTrace();
			}
			if (e == null) {
				navireValide = true;

			}
		}

		return navire;

	}
	/*
	 * méthode void genererPosNavireAleaInsererDsGrille() qui ajoute un à un les
	 * navires dans la flotte. Il y a autant de boucles qu’il y a de navires. Une
	 * boucle se termine lorsque ajouterNavire retourne AUCUNE_ERREUR.
	 */

	private static void genererPosNavireAleaInsererDsGrille() {
		MessagesExceptions navireException;
		boolean ajoutValide = false;
		
		//NAVIRE 1
		while (ajoutValide == false) {

			navireException = null;
			Navire navire1 = obtenirNavireAleatoire(Constantes.CROISEUR, 3, Color.blue);
			try {
				ajouterNavire(navire1);
			} catch (MessagesExceptions e) {
				// TODO Auto-generated catch block
				navireException = e;
				e.printStackTrace();
			}
			if (navireException.getMessage().equals("Il n'y a aucune erreur")) {
				ajoutValide = true;
				System.out.println(navire1.toString());
			}
		}

		ajoutValide = false;
		
		//NAVIRE 2
		while (ajoutValide == false) {

			navireException = null;
			Navire navire2 = obtenirNavireAleatoire(Constantes.CUIRASSE, 2, Color.DARK_GRAY);

			try {
				ajouterNavire(navire2);
			} catch (MessagesExceptions e) {
				// TODO Auto-generated catch block
				navireException = e;
				e.printStackTrace();
			}
			if (navireException.getMessage().equals("Il n'y a aucune erreur")) {
				ajoutValide = true;
				System.out.println(navire2.toString());
			}
		}

		ajoutValide = false;
		//NAVIRE 3
		while (ajoutValide == false) {

			navireException = null;
			Navire navire3 = obtenirNavireAleatoire(Constantes.DESTROYER, 3, Color.yellow);
			try {
				ajouterNavire(navire3);
			} catch (MessagesExceptions e) {
				// TODO Auto-generated catch block
				navireException = e;
				e.printStackTrace();
			}
			if (navireException.getMessage().equals("Il n'y a aucune erreur")) {
				ajoutValide = true;
				System.out.println(navire3.toString());
			}
		}

		ajoutValide = false;
		
		//NAVIRE 4
		while (ajoutValide == false) {

			navireException = null;
			Navire navire4 = obtenirNavireAleatoire(Constantes.PORTE_AVION, 4, Color.GREEN);
			try {
				ajouterNavire(navire4);
			} catch (MessagesExceptions e) {
				// TODO Auto-generated catch block
				navireException = e;
				e.printStackTrace();
			}
			if (navireException.getMessage().equals("Il n'y a aucune erreur")) {
				ajoutValide = true;
				System.out.println(navire4.toString());
			}
		}

		ajoutValide = false;
		//NAVIRE 5
		while (ajoutValide == false) {

			navireException = null;
			Navire navire5 = obtenirNavireAleatoire(Constantes.SOUS_MARIN, 5, Color.CYAN);
			try {
				ajouterNavire(navire5);
			} catch (MessagesExceptions e) {
				// TODO Auto-generated catch block
				navireException = e;
				e.printStackTrace();
			}
			if (navireException.getMessage().equals("Il n'y a aucune erreur")) {
				ajoutValide = true;
				System.out.println(navire5.toString());
			}
		}
		
		

	}
	public static ArrayList<Navire> obtenirFlotteAleatoire() {
		genererPosNavireAleaInsererDsGrille();
		
		return navires;
	}

}
