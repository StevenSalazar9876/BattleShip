import java.util.ArrayList;

public class BattailleNavale {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GrilleGui grille = new GrilleGui(Constantes.TAILLE, Constantes.TAILLE,Constantes.COULEUR_TEXTE 
				, Constantes.COULEUR_FOND, Constantes.OPTIONS, GrilleGui.QUITTE);

		ArrayList<Navire> navires = new ArrayList<>();
		Flotte flotte = new Flotte(navires);
		
		
		UtilitaireGrilleGui.montrerFlotte(flotte, grille);
	}

}
