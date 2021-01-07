package mouvement;

/**
 * Vecteur est la classe pour les vecteurs du modèle.
 * <p> Un Vecteur est caractérisé par les informations suivantes :
 * <ul><li>Une direction X, suceptible d'être changée.</li>
 * <li>Une direction Y, suceptible d'être changée.</li>
 * <li>Une direction Z, suceptible d'être changée.</li></ul></p>
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Mathéo Gallego
 */
public class Vecteur {
	/**
	 * La direction X du Vecteur.
	 * @see Vecteur#getDirX()
	 * @see Vecteur#setDirX(float)
	 */
	private float dirX;
	/**
	 * La direction Y du Vecteur.
	 * @see Vecteur#getDirY()
	 * @see Vecteur#setDirY(float)
	 */
	private float dirY;
	/**
	 * La direction Z du Vecteur.
	 * @see Vecteur#getDirZ()
	 * @see Vecteur#setDirZ(float)
	 */
	private float dirZ;
	
	/**
	 * Constructeur Vecteur.
	 * @param dirX
	 * 				La direction X donnée au Vecteur.
	 * @param dirY
	 * 				La direction Y donnée au Vecteur.
	 * @param dirZ
	 * 				La direction Z donnée au Vecteur.
	 */
	public Vecteur(float dirX, float dirY, float dirZ) {
		this.dirX = dirX;
		this.dirY = dirY;
		this.dirZ = dirZ;
	}

	/**
	 * Retourne la valeur de "dirX" du Vecteur.
	 * @return la valeur de la direction X du Vecteur, sous la forme d'un float.
	 */
	public float getDirX() {
		return dirX;
	}
	/**
	 * Retourne la valeur de "dirY" du Vecteur.
	 * @return la valeur de la direction Y du Vecteur, sous la forme d'un float.
	 */
	public float getDirY() {
		return dirY;
	}
	/**
	 * Retourne la valeur de "dirZ" du Vecteur.
	 * @return la valeur de la direction Z du Vecteur, sous la forme d'un float.
	 */
	public float getDirZ() {
		return dirZ;
	}
	/**
	 * Met à jour la direction X du Vecteur.
	 * @param dirX
	 * 				La nouvelle direction X du Vecteur.
	 */
	public void setDirX(float dirX) {
		this.dirX = dirX;
	}
	/**
	 * Met à jour la direction Y du Vecteur.
	 * @param dirY
	 * 				La nouvelle direction Y du Vecteur.
	 */
	public void setDirY(float dirY) {
		this.dirY = dirY;
	}
	/**
	 * Met à jour la direction Z du Vecteur.
	 * @param dirZ
	 * 				La nouvelle direction Z du Vecteur.
	 */
	public void setDirZ(float dirZ) {
		this.dirZ = dirZ;
	}
	
	/**
	 * Retourne une chaine de caractères contenant les 3 directions du Vecteur.
	 * @return Les 3 directions du Vecteur séparées par des virgules, sous la forme d'une chaine de caractère.
	 */
	public String toString() {
		return "Vecteur ["+dirX+", "+dirY+", "+dirZ+"]";
	}
}