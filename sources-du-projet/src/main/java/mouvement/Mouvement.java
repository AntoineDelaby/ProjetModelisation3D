package mouvement;

import dessin.DessinFace;

/**
 * Mouvement est la classe de gestion des mouvements.
 * <p>Un mouvement est caract�ris� par les informations suivantes :</p>
 * <ul><li>Un Dessinateur de faces unique attribu� d�finitivement.</li></ul>
 * @see DessinFace
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Math�o Gallego
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
	 * Effectue un mouvement sp�cifique sur le mod�le pass� en param�tre.
	 * @param model
	 * 				 La liste des sommets.
	 */
	public void mouvement(float[][]model) {
		effectuerMouvement(model);
	}
	
	/**
	 * M�thode abstraite qui va appeler l'une des m�thodes homonymes dans : Rotation, Translation ou Zoom.
	 * @param model
	 * 				La liste des sommets.
	 * @see Rotation#effectuerMouvement(float[][])
	 * @see Translation#effectuerMouvement(float[][])
	 * @see Zoom#effectuerMouvement(float[][])
	 */
	public abstract void effectuerMouvement(float[][]model);
}
