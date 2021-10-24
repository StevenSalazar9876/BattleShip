
import java.awt.Color;
import java.awt.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Navire {

	// Les variables que nous allons utiliser

	// Constantes pour les exceptions
	private static String NORD_SUD_EXCEPTION = "Coordonnées Nord Sud invalide";
	private static String EST_OUEST_EXCEPTION = "Coordonnées EST OUEST invalide";
	private static String LIGNE_INVALIDE_EXCEPTION = "Ligne invalide";
	private static String COLONNE_INVALIDE_EXCEPTION = "Colonne invalide";

	// Nom du navire
	private String nNomNavire;
	// La couleur du navire
	private Color nCouleurNavire;
	// Les coups qui ont touché le navire.
	public ArrayList<Coord> nCoupTouchés;
	// Le array qui va garder les coords de ce navire
	public ArrayList<Coord> nCoordNavire;

	// La variable pour la difference entre debut et fin de ligne
	private int nbLigne = 0;
	// La variable pour la difference entre debut et fin de colonne
	private int nbColonne = 0;
	// La variable pour la grandeur du bateau
	private int nNbCasesNavire = 0;
	// La variable qui va détécté si le bateau est coulé.
	private boolean nEstCoule = false;
	// La variable qui va détécté si le tir va touche le bateau actuel.
	private boolean nTirATouche = false;

	// Constructor du navire
	public Navire(String nNomNavire, Color nCouleurNavire, Coord debut, Coord fin) throws MessagesExceptions {

		int nbLignes = fin.ligne - debut.ligne + 1;
		int nbColonnes = fin.colonne - debut.colonne + 1;

		// Exceptions

		/*
		 * Le nombre de lignes est plus grand que 1 et les colonnes sont différentes
		 * entre le début et la fin.
		 */
		if (nbLignes > 1 && fin.colonne != debut.colonne) {
			throw new MessagesExceptions(NORD_SUD_EXCEPTION);
			/*
			 * Le nombre de colonnes est plus grand que 1 et les lignes sont différentes
			 * entre le début et la fin.
			 */
		} else if (nbColonnes > 1 && fin.ligne != debut.ligne) {
			throw new MessagesExceptions(EST_OUEST_EXCEPTION);
			/*
			 * La ligne n’est pas dans l’intervalle de la grille ou la ligne de début est
			 * plus grande que la ligne de fin.
			 */
		} else if (debut.ligne > fin.ligne || fin.ligne < 1 || fin.ligne > Constantes.TAILLE
				|| debut.ligne < 1 || debut.ligne > Constantes.TAILLE) {
			throw new MessagesExceptions(LIGNE_INVALIDE_EXCEPTION);
			/*
			 * La colonne n’est pas dans l’intervalle de la grille ou la colonne de début
			 * est plus grande que la colonne de fin.
			 */
		} else if (debut.colonne > fin.colonne || fin.colonne < 1 || fin.colonne > Constantes.TAILLE
				|| debut.colonne < 1 || debut.colonne > Constantes.TAILLE) {
			throw new MessagesExceptions(COLONNE_INVALIDE_EXCEPTION);
		} else {
			nCoordNavire = new ArrayList<Coord>();
			nCoupTouchés = new ArrayList<Coord>();

			this.nNomNavire = nNomNavire;
			this.nCouleurNavire = nCouleurNavire;
			mRemplirArrayNavireCoords(debut, fin);
		}

	}

	// Constructor vide par default

	// Getters and setters

	public String getnNomNavire() {
		return nNomNavire;
	}

	public void setnNomNavire(String nNomNavire) {
		this.nNomNavire = nNomNavire;
	}

	public int getnNbrCases() {
		return nNbCasesNavire;
	}

	public void setnNbrCases(int nNbrCases) {
		this.nNbCasesNavire = nNbrCases;
	}

	public Color getnCouleurNavire() {
		return nCouleurNavire;
	}

	public void setnCouleurNavire(Color nCouleurNavire) {
		this.nCouleurNavire = nCouleurNavire;
	}

	public ArrayList<Coord> getnCoupTouchés() {
		return nCoupTouchés;
	}

	public void setnCoupTouchés(ArrayList<Coord> nCoupTouchés) {
		this.nCoupTouchés = nCoupTouchés;
	}

	public int getNbLignes() {
		return nbLigne;
	}

	public void setNbLignes(int nbLignes) {
		this.nbLigne = nbLignes;
	}

	public int getNbColonne() {
		return nbColonne;
	}

	public void setNbColonne(int nbColonne) {
		this.nbColonne = nbColonne;
	}

	public ArrayList<Coord> getnCoordNavire() {
		return nCoordNavire;
	}

	public void setnCoordNavire(ArrayList<Coord> nCoordNavire) {
		this.nCoordNavire = nCoordNavire;
	}

	public int getNbLigne() {
		return nbLigne;
	}

	public void setNbLigne(int nbLigne) {
		nbLigne = nbLigne;
	}

	public int getnNbCasesNavire() {
		return nNbCasesNavire;
	}

	public void setnNbCasesNavire(int nNbCasesNavire) {
		nNbCasesNavire = nNbCasesNavire;
	}

	public boolean isnEstCoule() {
		return nEstCoule;
	}

	public void setnEstCoule(boolean nEstCoule) {
		nEstCoule = nEstCoule;
	}

	public boolean isnTirATouche() {
		return nTirATouche;
	}

	public void setnTirATouche(boolean nTirATouche) {
		nTirATouche = nTirATouche;
	}

	// --------------------------------------------------LES AUTRES
	// MÉTHODES------------------------------------------------------

	/*
	 * Cette méthode retourne vrai si le bateau est coulé. Autrement dit, il a été
	 * touché autant de fois qu’il a de position entre début et fin, à des positions
	 * différentes (deux fois sur la même position ne compte pas).
	 */
	public boolean mEstCoule() {
		// Je nettoie le array de nCoupTouchés avant de verifier sa taille pour ne pas
		// avoir des doublons
		mNettoyerLeArrayDesDoublons();

		if (nCoupTouchés.size() == nNbCasesNavire) {
			nEstCoule = true;
		} else
			nEstCoule = false;
		return nEstCoule;

	}

	/*
	 * Une méthode boolean dejaRecuTir(Coord tir) qui retourne vrai si la coordonnée
	 * reçue à déjà touché au navire.
	 */
	public boolean mDejaRecuTir(Coord tir) {

		boolean nVerifier = false;

		for (int i = 0; i < nCoupTouchés.size(); i++) {

			if (nCoupTouchés.get(i).equals(tir)) {
				nVerifier = true;
			}
		}
		return nVerifier;
	}

	/*
	 * Cette méthode retourne true si le navire actuel contient la coord du tir
	 */
	private boolean mVerifierSiLeTirToucheLeNavire(Coord tir) {

		boolean nVerifier = false;

		for (int i = 0; i < nCoordNavire.size(); i++) {

			if (nCoordNavire.get(i).equals(tir)) {
				nVerifier = true;
			}
		}
		return nVerifier;

	}

	/*
	 * Une méthode boolean tirAtouche (Coord tir) qui retourne vrai si la coordonnée
	 * reçue touche au navire actuel (this) et faux autrement (implique une boucle
	 * de recherche). Retient aussi la coordonnée si elle l’a touché. Voici
	 * l’algorithme en pseudocode.
	 */

	public boolean mTirATouche(Coord tir) {
		// Si le navire n’est pas coulé
		// Si le navire actuel n’a pas déjà reçu ce tir
		// Si le tir touche au navire actuel (méthode privée)
		if (mEstCoule() == false && mDejaRecuTir(tir) == false && mVerifierSiLeTirToucheLeNavire(tir) == true) {
			// On retient la coordonnée dans la collection
			nCoupTouchés.add(tir);
			// On met le booléen à vrai.
			nTirATouche = true;
		} else {
			// System.out.println("Le tir n'a pas touché le navire!");
			nTirATouche = false;
		}
		return nTirATouche;

	}

	/*
	 * Une méthode boolean chevauche(Navire navire) qui retourne vrai si une des
	 * positions du navire reçu touche à une des position du navire actuel (en ligne
	 * ou en colonne).
	 */
	public boolean mChevauche(Navire navire) {

		boolean nVerifier = false;
		for (int i = 0; i < this.nCoordNavire.size(); i++) {
			for (int j = 0; j < navire.nCoordNavire.size(); j++) {
				if (this.nCoordNavire.get(i).equals(navire.nCoordNavire.get(j)) == true) {
					nVerifier = true;
				}

			}
		}

		return nVerifier;
	}

	public boolean mVerifierEmplacementNavire() {

		boolean depasseLimite = false;

		for (int i = 0; i < nCoordNavire.size(); i++) {

			if (nCoordNavire.get(i).colonne > 10) {
				depasseLimite = true;
			} else if (nCoordNavire.get(i).ligne > 10) {
				depasseLimite = true;
			}
		}
		return depasseLimite;
	}

	// Cette methode va calculer la difference des coords pour avoir la taille
	public int mCalculerDebutFin(Coord debut, Coord fin) {

		nbLigne = (fin.ligne - debut.ligne);
		nbColonne = (fin.colonne - debut.colonne);

		nNbCasesNavire = nbLigne + nbColonne + 1;

		return nNbCasesNavire;
	}

	// Cette methode va nettoyer l'arrayList des duplicates
	public void mNettoyerLeArrayDesDoublons() {
		Set<Coord> set = new HashSet<Coord>(nCoupTouchés);
		nCoupTouchés.clear();
		nCoupTouchés.addAll(set);
	}

	/*
	 * Méthode qui sert a remplir le array avec les données des cords que le navire
	 * contient
	 */
	public ArrayList<Coord> mRemplirArrayNavireCoords(Coord debut, Coord fin) {
		mCalculerDebutFin(debut, fin);

		if (nbLigne > nbColonne) {
			for (int i = 0; i < nNbCasesNavire; i++) {
				nCoordNavire.add(new Coord((debut.ligne + i), (debut.colonne)));

			}
		} else {
			for (int i = 0; i < nNbCasesNavire; i++) {
				nCoordNavire.add(new Coord((debut.ligne), (debut.colonne + i)));

			}
		}

		return nCoordNavire;

	}
	

	@Override
	public String toString() {
		return "Navire [nNomNavire=" + nNomNavire + ", nCouleurNavire=" + nCouleurNavire + ", nCoordNavire="
				+ nCoordNavire.toString() + "]";
	}

	public static void main(String[] args) {

		// jouer();
		// on cree une coord de tir
		Coord tir1 = new Coord(5, 7);

		// On cree des coord pour le debut et fin du bateau
		Coord coordDebut = new Coord(5, 1);
		Coord coordFin = new Coord(5, 9);

		Coord coordDebut2 = new Coord(2, 1);
		Coord coordFin2 = new Coord(2, 7);

		Navire navire1 = null;
		Navire navire2 = null;

		try {
			navire1 = new Navire("Raul", Color.BLUE, coordDebut, coordFin);
		} catch (MessagesExceptions e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			navire2 = new Navire("Steven", Color.YELLOW, coordDebut2, coordFin2);
		} catch (MessagesExceptions e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("NAVIRE 1");
		System.out.println(navire1.getnCoordNavire().toString());
		System.out.println();
		System.out.println("NAVIRE 2");
		System.out.println(navire2.getnCoordNavire().toString());

		// --------Tester pour un tir au navire1------------------
		System.out.println("--------------------------------------------");
		System.out.println();
		System.out.println("NAVIRE 1");
		System.out.println();
		System.out.println("Le navire est coulé? " + navire1.mEstCoule());
		// --------Tester pour un tir------------------
		System.out.println();
		System.out.println("Le navire est deja touché? " + navire1.mDejaRecuTir(tir1));
		// --------Tester pour si le tir touche le navire------------------
		System.out.println();
		System.out.println("Le tir touche le navire? " + navire1.mVerifierSiLeTirToucheLeNavire(tir1));
		// --------On ajoute le tir au Array des coups------------------
		System.out.println();
		System.out.println("Le tir respecte toute les condition pour etre ajouté? " + navire1.mTirATouche(tir1));
		System.out.println();

		// --------On ajoute le tir au Array des coups------------------
		System.out.println();
		System.out.println("Le navire 2 chevauche avec le navire 1? " + navire1.mChevauche(navire2));

		System.out.println("---------------------------------------------------------------------------");
		System.out.println();

		// --------Tester pour un tir au navire1------------------
		System.out.println("NAVIRE 2");
		System.out.println();
		System.out.println("Le navire est coulé? " + navire2.mEstCoule());
		// --------Tester pour un tir------------------
		System.out.println();
		System.out.println("Le navire est deja touché? " + navire2.mDejaRecuTir(tir1));
		// --------Tester pour si le tir touche le navire------------------
		System.out.println();
		System.out.println("Le tir touche le navire? " + navire2.mVerifierSiLeTirToucheLeNavire(tir1));
		// --------On ajoute le tir au Array des coups------------------
		System.out.println();
		System.out.println("Le tir respecte toute les condition pour etre ajouté? " + navire2.mTirATouche(tir1));

	}

}
