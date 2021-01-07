package mouvement;

/**
 * Vecteur est la classe pour les vecteurs du mod�le.
 * <p> Un Vecteur est caract�ris� par les informations suivantes :
 * <ul><li>Une direction X, suceptible d'�tre chang�e.</li>
 * <li>Une direction Y, suceptible d'�tre chang�e.</li>
 * <li>Une direction Z, suceptible d'�tre chang�e.</li></ul></p>
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Math�o Gallego
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
	 * 				La direction X donn�e au Vecteur.
	 * @param dirY
	 * 				La direction Y donn�e au Vecteur.
	 * @param dirZ
	 * 				La direction Z donn�e au Vecteur.
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
	 * Met � jour la direction X du Vecteur.
	 * @param dirX
	 * 				La nouvelle direction X du Vecteur.
	 */
	public void setDirX(float dirX) {
		this.dirX = dirX;
	}
	/**
	 * Met � jour la direction Y du Vecteur.
	 * @param dirY
	 * 				La nouvelle direction Y du Vecteur.
	 */
	public void setDirY(float dirY) {
		this.dirY = dirY;
	}
	/**
	 * Met � jour la direction Z du Vecteur.
	 * @param dirZ
	 * 				La nouvelle direction Z du Vecteur.
	 */
	public void setDirZ(float dirZ) {
		this.dirZ = dirZ;
	}
	
	/**
	 * Retourne une chaine de caract�res contenant les 3 directions du Vecteur.
	 * @return Les 3 directions du Vecteur s�par�es par des virgules, sous la forme d'une chaine de caract�re.
	 */
	public String toString() {
		return "Vecteur ["+dirX+", "+dirY+", "+dirZ+"]";
	}
}