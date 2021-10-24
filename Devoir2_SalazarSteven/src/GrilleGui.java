import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Grille de jeu rectangulaire d'au maximum MAX_LIGNES X MAX_COLONNES
 * qui permet d'obtenir s'il y  eu un clic, la position du clic et modifier le 
 * contenu de la case (couleur et texte).
 * 
 * Il est possible aussi d'ajouter des boutons de menu.  Dans ce cas, 
 * estBoutonMenu retourne vrai et getTexteMenu retourne le texte contenu 
 * dans le bouton.  Ces boutons sont cr��s en bas de la fen�tre � partir d'un 
 * tableau de String fourni au constructeur (mettre null si non d�sir�).
 * 
 * Utile pour des TP1 en inf111 (jeux tels Sudoku, Binero, ...)
 * 
 * @author Pierre B�lisle (copyright 2016)
 * @version Copyright A2021
 */
public class GrilleGui  implements Runnable{

	/*
	 * STRAT�GIE : On met des boutons dans un panneau mais on les retient aussi 
	 * dans une grille.  Une classe interne MonJButton h�rite de JButton �
	 * laquelle on ajoute des attributs pour retenir la position du bouton 
	 * dans la grille. Tout cela pour �viter la recherche du bouton lors 
	 * d'un clic (deux boucles en moins).
	 *                        
	 * Un bool�en permet de retenir si un bouton a �t� cliqu� et il est remis ��
	 * faux apr�s une lecture de la position par son accesseur.
	 */

	// Limite pour voir le texte.
	public static final int MAX_LIGNES = 20;
	public static final int MAX_COLONNES = 20;
	
	// Celle du texte des boutons.
	public static final int TAILLE_CAR = 40;
	
	// Deux modes de fermeture du gui.  On quitte le programme  ou on 
	// dispose juste la fen�tre.
	 public static final int QUITTE = JFrame.EXIT_ON_CLOSE;
	 public static final int DISPOSE = JFrame.DISPOSE_ON_CLOSE;

	// On compose dans un cadre.
	private JFrame cadre = new JFrame();

	// La grille qui sera affich�e (classe interne d�crite � la fin).
	private MonJButton [][] grille;

	// La position en y,x du dernier clic.
	private Coord dernierClic;

	// Mis � vrai lors d'un clic et � faux dans getPosition.
	private boolean estClique;
	
	// Retenir la taille de la grille.
	private int nbLignes;
	private int nbColonnes;

	// Les couleurs.
	private Color couleurTexte;
	private Color couleurFond;
	
	// La position.
	Point position;

	// Retenir le tableau des options de menus.
	private String [] tabMenus;
	
	// Pour les options de menus du panneau du bas.
	private boolean estBoutonMenu;
	
	// Vaudra le bouton cliqu� s'il y a eu un clic sur un des boutons de menu
	// et il est mis � null apr�s getOptionMenu.
	private String optionClique;
	
	// Retenir le mode de fermeture d�sir�e.
	private int modeFermeture;
	/**
	/**
	 * Cr�e une grille selon les dimensions re�ues.
	 * 
	 * Le modeFermeture peut �tre QUITTE ou DISPOSE.	 
	 * 
	 * S'il y a un tableau de String diff�rent de null, il appara�tra
	 * un bouton pour chaque case du tableau non null en bas de la fen�tre.
	 *   
	 * @param nbLignes L'axe des Y
	 * @param nbColonnes L'axe des X
	 * @param couleurTexte
	 * @param couleurFond
	 * @param dim
	 * @param position
	 * @param tabMenus Les options du menu du bas
	 */
	public GrilleGui(int nbLignes, int nbColonnes, 
			         Color couleurTexte, Color couleurFond,
			         String[] tabMenus,
		          	 int modeFermeture){
		
		// On retient la taille et les couleurs de la grille.
		this.nbLignes = (nbLignes>MAX_LIGNES)
				?MAX_LIGNES
						:nbLignes;

		this.nbColonnes = (nbColonnes>MAX_COLONNES)
				?MAX_COLONNES
						:nbColonnes;

		this.couleurFond = couleurFond;
		this.couleurTexte = couleurTexte;

		// On retient les options du menu.
		this.tabMenus = tabMenus;

		this.modeFermeture = modeFermeture;
		
		// On cr�e le tableau 2D (vide).
		grille = new MonJButton[nbLignes][nbColonnes];

		// Sert � retenir l'�tat des clics entre les appels.
		// Rien de cliqu� au d�part.
		estClique = false;
		estBoutonMenu = false;

		// On cr�e le panneau du bas avec les boutons de menu.

		// On affiche le cadre dans le EDT.
		SwingUtilities.invokeLater(this);
		
		
		

	}

	/**
	 * Accesseur de la position du dernier clic.  Ne tient pas compte 
	 * s'il y a eu un clic ou non.
	 * 
	 * @return La positoin du dernier clic.
	 */
	public Coord getPosClic(){

		estClique = false;
		
		return dernierClic;		
	}

	/**
	 * Retourne si vrai si un des boutons de menu a �t� cliqu�.
	 * 
	 * @return Si un des boutons de menu a �t� cliqu�.
	 */
	public boolean optionMenuEstCliquee(){
		
		return estBoutonMenu;
	}
	
	/**
	 * Retourne la derni�re option cliqu� et null autrement.
	 * 
	 * @return Le texte dans le bouton cliqu� s'il y a lieu.
	 */
	public String getOptionMenuClique(){
		
		if(estBoutonMenu)
		    estBoutonMenu = false;
		else
			optionClique = null;
		
		return optionClique;
	}
	
	/**
	 * Retourne la valeur contenue dans une case.
	 * 
	 * @param coord La position de la case d�sir�e.
	 * 
	 * @return La valeur contenue dans la case montr�e par c.
	 */
	public String getValeur(Coord c){

		return grille[c.ligne][c.colonne].getText();
	}
	
	/**
	 * Permet de modifier la valeur d'une case de la grille.
	 * 
	 * @param coord La position de la case d�sir�e.
	 * 
	 * @param valeur La nouvelle valeur.
	 */
	public void setValeur(Coord c, String valeur){

		/*
		 * Comme c'est un Thread,  il se peut que la grille ne soit pas encore 
		 * cr��e alors on attend
		 */
		if(grille[c.ligne][c.colonne]==null)
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
		
			grille[c.ligne][c.colonne].setText(valeur);
	}

	/**
	 * Accesseur du nombre de lignes.
	 * 
	 * @return Le nombre de lignes de la grille.
	 */
	public int getNbLignes() {
		return nbLignes;
	}

	/**
	 * Accesseur du nombre de colonnes.
	 * 
	 * @return Le nombre de colonnes de la grille.
	 */
	public int getNbColonnes() {
		return nbColonnes;
	}
	
	/**
	 * Permet de changer la couleur de fond d'une case.
	 * 
	 * @param coord La position de la case
	 * @param couleur La nouvelle couleur
	 */
	public void setCouleurFond(Coord c, Color couleurFond){
		if(grille[c.ligne][c.colonne]==null)
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
	        grille[c.ligne][c.colonne].setBackground(couleurFond);
	}

	/**
	 * Permet de changer la couleur de texte d'une case.
	 * 
	 * @param coord La position de la case
	 * @param couleur La nouvelle couleur
	 */
	public void setCouleurTexte(Coord c, Color couleurTexte){
		
		grille[c.ligne][c.colonne].setForeground(couleurTexte);
	}
	
	/**
	 * Retourne si un des boutons a �t� cliqu� depuis le dernier appel
	 * � l'accesseur de position.
	 * 
	 * @return Si un des boutons a �t� s�lectionn�
	 */
	public boolean caseEstCliquee(){
		return estClique;
	}

	/*
	 *    AUX �L�VES
	 *    
	 *    � partir d'ici, le code suivant n'est pas int�ressant pour ce travail.
	 * 
	 *     Contentez-vous d'utilisez les m�thodes pr�c�dent es.
	 */
	
	
	public void run() {


		// On quitte sur X.
		cadre.setDefaultCloseOperation(modeFermeture);
		
		// Plein �cran
		cadre.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		// On ne bouge plus la taille.
		cadre.setResizable(false);

		// Obtention de la r�f�rence sur le contentPane (�vite pls appels).
		JPanel panneauPrincipal = (JPanel) cadre.getContentPane();

		// Le panneau contenant la grille.
		JPanel panneauHaut = new JPanel();
		
	    // Une disposition en grille pour celui du haut.
		panneauHaut.setLayout(new GridLayout(nbLignes, nbColonnes));

		// On ajoute les boutons vides.
		ajouterBoutons(panneauHaut);

		if(tabMenus != null){

			ajouterPanneaux(panneauPrincipal, panneauHaut);			
		}

		else {
			// Le panneau du haut est plein �cran s'il n'y a pas de menu.
			panneauPrincipal.add(panneauHaut);
		}
		

		
		cadre.setVisible(true);	
		
		
	}

	private void ajouterPanneaux(JPanel panneauPrincipal, JPanel panneauHaut) {
		
		// Les boutons de menu s'il y en a (FlowLayout par d�faut).
		JPanel panneauBas = new JPanel();

		// Obtenir les dimensions de l'�cran.
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();


		Dimension dh = new Dimension (dim.width, (int)(dim.height*.8));
		Dimension db = new Dimension (dim.width, (int)(dim.height*.1));

		// La dimension pour l'allure de la fen�tre.
		panneauHaut.setMinimumSize(dh);
		panneauHaut.setMaximumSize(dh);
		panneauHaut.setPreferredSize(dh);

		panneauBas.setMinimumSize(db);
		panneauBas.setMaximumSize(db);
		panneauBas.setPreferredSize(db);

		ajouterMenu(panneauBas);

		panneauPrincipal.add(panneauHaut, BorderLayout.PAGE_START);
		panneauPrincipal.add(panneauBas, BorderLayout.PAGE_END);		
	}

	/**
	 * Le clic de cette case n'aura plus d'effet.
	 * 
	 * @param x
	 * @param ligne
	 */
	public void desactiverCase(Coord c){
		
		grille[c.ligne][c.colonne].setEnabled(false);
	}
	
	/*
	 * Ajoute des boutons de menu (S'il y en a) au panneau 
	 * 
	 * Si on est ici, on est certain qu'il y a des options de menu.
	 */
	private void ajouterMenu(JPanel panneau){

		JButton b;

		for(int i = 0; i < tabMenus.length; i++){

			b =new JButton(tabMenus[i]);

			// La dimension d'un bouton d�pend de la taille de l'�cran,
			// on centre la grille.			
			b.addActionListener(new ActionListener(){

				
				public void actionPerformed(ActionEvent e) {

					estClique = false;
					optionClique = ((JButton)e.getSource()).getText();
					estBoutonMenu = true;
				}	
			});


			panneau.add(b);
		}

	}
	/*
	 * Ajoute les boutons dans la grille et dans le panneau
	 * 
	 * Principalement pour la lisibilit� du code
	 */
	private void ajouterBoutons(JPanel panneau){

		for(int i = 0; i < nbLignes;i++)
			for(int j = 0; j <nbColonnes;j++){

				grille[i][j] =  new MonJButton(i,j, " ",  
						                       couleurTexte, couleurFond);
				panneau.add(grille[i][j]);
			}	
	}

	/**
	 * Classe interne qui ajoute � un JButton la position (x,y) o� il se trouve 
	 * dans la grille.
	 * 
	 * Cela �vite de chercher cette position lors d'un clic.
	 */
	private class MonJButton extends JButton{

		// Juste pour enlever le warning.
		private static final long serialVersionUID = 1L;
		
		// Coordonn�e ligne colonne du bouton dans le gui.
		private int ligne;
		private int colonne;

		/**
		 * Constructeur avec la position du bouotn et sa valeur.
		 * @param y La position en ligne
		 * @param x La position en colonne
		 * @param valeur La valeur � afficher
		 */
		private MonJButton(int ligne, 
				           int colonne,
				           String valeur, 
				           Color couleurTexte, 
				           Color couleurFond){

			// On passe le texte � la classe parent.
			super(valeur);

			this.ligne = ligne;
			this.colonne = colonne;

			setForeground(couleurTexte);
			setBackground(couleurFond);
			setFont(new Font("sans serif", Font.BOLD, TAILLE_CAR));

			// La dimension d'un bouton d�pend de la taille de 
			// l'�cran, on centre la grille.			
			addActionListener(new ActionListener(){

				
				public void actionPerformed(ActionEvent e) {

					// On obtient la r�f�rence du bouton cliqu�.
					MonJButton b = (MonJButton) e.getSource();

					// On retient la position du clic.
					dernierClic =  new Coord(0,0);
					dernierClic.ligne = b.ligne;
					dernierClic.colonne = b.colonne;
					
					estClique = true;		
					estBoutonMenu = false;
				}	
			});
		}
	}
}