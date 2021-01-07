package mouvement;

import dessin.DessinFace;

/**
 * Zoom est la classe de gestion des Mouvements de Zoom sur le modèle.
 * <p> Un Zoom est caractérisé par les informations suivantes :
 * <ul><li>Un Facteur de Zoom, unique attribué définitivement à {@value ROTATION#FACTEUR_ZOOM}.</li>
 * <li>Un Facteur de Dezoom, unique attribué définitivement à {@value ROTATION#FACTEUR_DEZOOM}.</li>
 * <li>Un Facteur Neutre, unique attribué définitivement à {@value ROTATION#FACTEUR_NEUTRE}.</li>
 * <li>Un Facteur d'Erreur, unique attribué définitivement à {@value ROTATION#FACTEUR_ERREUR}.</li>
 * <li>Un facteur qui prendra soit la valeur du Facteur de Zoom soit celle du Facteur de Dezoom.</li></ul>
 * De plus, Zoom hérite de la classe Mouvement.</p>
 * @see Mouvement
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Mathéo Gallego
 */
public class Zoom extends Mouvement{
	/**
	 * La valeur du facteur de Zoom.
	 */
	public final static float FACTEUR_ZOOM = (float) 1.1;
	/**
	 * La valeur du facteur de Dezoom.
	 */
	public final static float FACTEUR_DEZOOM = (float) 0.9;
	/**
	 * Le facteur Neutre.
	 */
	public final static float FACTEUR_NEUTRE = (float) 1;
	/**
	 * Le facteur d'Erreur.
	 */
	public final static float FACTEUR_ERREUR= (float) 0;
	/**
	 * le facteur de Zoom ou de Dezoom.
	 */
	private float facteur;
	
	/**
	 * Constructeur Zoom.
     * <p>La construction d'un objet Zoom appelle le constructeur de la classe dont il hérite : Mouvement(DessinFace).</p>
	 * @param df
	 * 				Le dessinateur des Faces.
	 * @param facteur
	 * 				Un facteur de Zoom ou de Dezoom.
	 * @see Mouvement#df
	 * @see Zoom#facteur
	 */
	public Zoom(DessinFace df, float facteur) {
		super(df);
		this.facteur = facteur;
	}
	/**
	 * Constructeur Zoom.
     * <p>La construction d'un objet Rotation appelle son constructeur homonyme.</p>
	 * @param facteur
	 * 				Un facteur de Zoom ou de Dezoom.
	 * @see Zoom(DessinFace, float)
	 * @see Zoom#facteur
	 */
	public Zoom(float facteur) {
		this(null,facteur);
	}
	/**
	 * Constructeur Zoom.
     * <p>La construction d'un objet Rotation appelle son constructeur homonyme.</p>
	 * @see Zoom(DessinFace, float)
	 * @see Zoom#FACTEUR_NEUTRE
	 */
	public Zoom() {
		this(null,FACTEUR_NEUTRE);
	}

	/**
	 * Effectue le mouvement de Zoom sur le modèle.
	 * <p>En appellant la méthode qui va appliquer le zoom</p>
	 * @param model
	 * 				La liste des sommets.
	 * @see Zoom#zoom(float[][])
	 */
	@Override
	public void effectuerMouvement(float[][] model) {
		zoom(model);
	}

	/**
	 * Applique le mouvement de Zoom sur le modèle.
	 * <p>En effectuant sur l'ensemble des sommets du modèle le facteur de Zoom {@link Zoom#facteur}</p>
	 * @param model
	 * 				La liste des sommets.
	 */
	public void zoom(float[][]model) {
		if(model != null && facteur != FACTEUR_ERREUR) {
			for(int i = 0; i < model[0].length; i++) {
				model[0][i] = model[0][i] * this.facteur;
				model[1][i] = model[1][i] * this.facteur;
				model[2][i] = model[2][i] * this.facteur;
			}
		}
	}
}
