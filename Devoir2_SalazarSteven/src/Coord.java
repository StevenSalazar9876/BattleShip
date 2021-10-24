/**
 * Cet enregistrement repr�sente les coordonn�es 
 * possible dans diff�rents jeux de grille.
 * 
 *@author Pierre B�lisle
 *@version Copyright A2021
 */
public class Coord {

	/*
	 * Les attributs conserv�s.
	 */
	int ligne;
	int colonne;

	/**
	 * Constructeur par d�faut (0,0)
	 */
	public Coord(){
		ligne = 0;
		colonne = 0;
	}
	
	/**
	 * Constructeurs par copie d'attributs,
	 * 
	 * @param ligne
	 * @param colonne
	 */
	public Coord(int ligne,int colonne){
		this.ligne = ligne;
		this.colonne = colonne;
	}

	/**
	 * M�thode qui compare les lignes et les colonnes des deux coordonn�es
	 * (this et coord)
	 * 
	 * @param coord La coordonn�e de comparaison.
	 * @return Si coord est �gal � this (deep equals)
	 */
	public boolean equals(Coord coord){
		return coord.ligne == ligne && coord.colonne == colonne;
	}
	
	/**
	 * Une version String d'un Coord.
	 * 
	 * @return Une cha�ne de caract�res format�e "(x,y)"
	 */
	
	public String toString(){
		return "(" + ligne + "," + colonne + ")";
	}	
}
