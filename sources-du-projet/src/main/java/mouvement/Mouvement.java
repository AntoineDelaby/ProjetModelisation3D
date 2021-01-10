package mouvement;

import dessin.DessinFace;

/**
 * Mouvement est la classe de gestion des mouvements.
 * <p>Un mouvement est caractérisé par les informations suivantes :</p>
 * <ul><li>Un Dessinateur de faces unique attribué définitivement.</li></ul>
 * @see DessinFace
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Mathéo Gallego
 */
public abstract class Mouvement {
	/**
	 * Le dessinateur des Faces.
	 */
	protected DessinFace df;
	
	/**
     * Constructeur Mouvement.
     * @param df
     *				Le dessinateur des Faces.
     * @see DessinFace
     */
	public Mouvement(DessinFace df) {
		this.df = df;
	}
	
	/**
	 * Effectue un mouvement spécifique sur le modèle passé en paramètre.
	 * @param model
	 * 				 La liste des sommets.
	 */
	public void mouvement(float[][]model) {
		effectuerMouvement(model);
	}
	
	/**
	 * Méthode abstraite qui va appeler l'une des méthodes homonymes dans : Rotation, Translation ou Zoom.
	 * @param model
	 * 				La liste des sommets.
	 * @see Rotation#effectuerMouvement(float[][])
	 * @see Translation#effectuerMouvement(float[][])
	 * @see Zoom#effectuerMouvement(float[][])
	 */
	public abstract void effectuerMouvement(float[][]model);
}
