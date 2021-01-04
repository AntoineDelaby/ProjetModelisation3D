package mouvement;

import dessin.DessinFace;
import dessin.Sommet;
import model.Model;

/**
 * Translation est la classe de gestion des Mouvements de Translation sur le mod�le.
 * <p> Une Translation est caract�ris�e par les informations suivantes :
 * <ul><li>Une direction, suceptible d'�tre chang�.</li>
 * <li>Un d�calage, suceptible d'�tre chang�. D�pend de {@linkplain Translation#FACTEURDECALAGE}.</li>
 * <li>Un Facteur de d�calage unique attribu� d�finitivement � {@value Translation#FACTEURDECALAGE}.</li></ul>
 * De plus, Translation h�rite de la classe Mouvement.</p>
 * @see Mouvement
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Math�o Gallego
 */
public class Translation extends Mouvement {
	/**
	 * La direction de la translation.
	 * <p>Elle prendra les valeurs suivantes :
	 * <ul><li>'h' pour haut</li><li>'b' pour bas</li><li>'g' pour gauche</li><li>'d' pour droite</li></ul></p>
	 * @see Translation#getDirection()
	 * @see Translation#setDirection(char)
	 */
	private char direction;
	/**
	 * Le nombre de pixel de d�calage pour chaque translation.
	 * @see Translation#getDecalage()
	 * @see Translation#setDecalage(int)
	 */
	private int decalage;
	/**
	 * Le Facteur de d�calage
	 * <p>Il servira au Constructeur pour attribuer sa valeur enti�re pr�d�finie � {@value Translation#FACTEURDECALAGE} � {@linkplain Translation#decalage}.</p>
	 */
	private final static int FACTEURDECALAGE = 50;

	/**
     * Constructeur Translation.
     * <p>La construction d'un objet Translation appelle le constructeur de la classe dont il h�rite : Mouvement(DessinFace).</p>
	 * @param df
	 * 				Le dessinateur des Faces.
	 * @param direction
	 * 				La direction de la translation.
	 * @param decalage
	 * 				Le nombre de pixel de d�calage pour chaque translation.
	 * @see Mouvement#df
	 * @see Translation#direction
	 * @see Translation#decalage
	 */
	public Translation(DessinFace df, char direction, int decalage) {
		super(df);
		this.direction = direction;
		this.decalage = decalage;
	}
	/**
     * Constructeur Translation.
     * <p>La construction d'un objet Translation appelle son constructeur homonyme.</p>
	 * @param df
	 * 				Le dessinateur des Faces.
	 * @param direction
	 * 				La direction de la translation.
	 * @see Translation(DessinFace, char, int)
	 * @see Mouvement#df
	 * @see Translation#direction
	 */
	public Translation(DessinFace df, char direction) {
		this(df,direction,FACTEURDECALAGE);
	}
	/**
     * Constructeur Translation.
     * <p>La construction d'un objet Translation appelle son constructeur homonyme.</p>
	 * @param direction
	 * 				La direction de la translation.
	 * @see Translation(DessinFace, char, int)
	 * @see Translation#direction
	 */
	public Translation(char direction) {
		this(null,direction,FACTEURDECALAGE);
	}
	/**
     * Constructeur Translation.
     * <p>La construction d'un objet Translation appelle son constructeur homonyme.</p>
	 * @see Translation(DessinFace, char, int)
	 */
	public Translation() {
		this(null,' ',FACTEURDECALAGE);
	}

	/**
	 * Effectue le mouvement de Translation sur le mod�le.
	 * @param model
	 * 				La liste des sommets.
	 */
	public void effectuerMouvement(float[][] model) {
		if(model != null) {
			if(direction == 'g') {
				translationGauche(model);
			}else if(direction == 'd') {
				translationDroite(model);
			}else if(direction == 'h') {
				translationHaut(model);
			}else if(direction == 'b') {
				translationBas(model);
			}
		}
	}

	/**
	 * Modifie les coordonn�es du Centre.
	 * @param sCentre
	 * 					Le sommet correspondant au centre du mod�le.
	 */
	public void modifCentre(Sommet sCentre) {
		if(sCentre != null) {
			if(direction == 'g') {
				sCentre.setX(sCentre.getX()-decalage);
			}else if(direction == 'd') {
				sCentre.setX(sCentre.getX()+decalage);
			}else if(direction == 'h') {
				sCentre.setY(sCentre.getY()-decalage);
			}else if(direction == 'b') {
				sCentre.setY(sCentre.getY()+decalage);
			}
		}
	}

	/**
	 * Translate le mod�le sur la gauche de {@value Translation#FACTEURDECALAGE} pixels.
	 * @param model
	 * 				La liste des sommets.
	 */
	private void translationGauche(float[][]model) {
		for(int i = 0; i < model[0].length; i++) {
			model[0][i] = model[0][i] - decalage;
		}
	}
	
	/**
	 * Translate le mod�le sur la droite de {@value Translation#FACTEURDECALAGE} pixels.
	 * @param model
	 * 				La liste des sommets.
	 */
	private void translationDroite(float[][]model) {
		for(int i = 0; i < model[0].length; i++) {
			model[0][i] = model[0][i] + decalage;
		}
	}
	
	/**
	 * Translate le mod�le vers le haut de {@value Translation#FACTEURDECALAGE} pixels.
	 * @param model
	 * 				La liste des sommets.
	 */
	private void translationHaut(float[][]model) {
		for(int i = 0; i < model[0].length; i++) {
			model[1][i] = model[1][i] - decalage;
		}
	}

	/**
	 * Translate le mod�le vers le bas de {@value Translation#FACTEURDECALAGE} pixels.
	 * @param model
	 * 				La liste des sommets.
	 */
	private void translationBas(float[][]model) {
		for(int i = 0; i < model[0].length; i++) {
			model[1][i] = model[1][i] + decalage;
		}
	}

	/**
	 * Retourne le d�calage.
	 * @return Le nombre de pixel de d�calage pour chaque translation, sous la forme d'un entier.
	 */
	public int getDecalage() {
		return decalage;
	}
	
	/**
	 * Retourne la direction.
	 * @return La direction de la translation, sous la forme d'un caract�re.
	 */
	public char getDirection() {
		return direction;
	}

	/**
	 * Met � jour la Direction de la Translation.
	 * @param direction
	 * 					La nouvelle direction de la Translation.
	 */
	public void setDirection(char direction) {
		this.direction = direction;
	}

	/**
	 * Met � jour le d�calage de la Translation.
	 * @param direction
	 * 					Le nouveau nombre de pixel de d�calage pour chaque translation.
	 */
	public void setDecalage(int decalage) {
		this.decalage = decalage;
	}

}
