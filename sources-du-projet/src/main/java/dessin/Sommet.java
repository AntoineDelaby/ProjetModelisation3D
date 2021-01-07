package dessin;

/**
 * Sommet est la classe pour les Sommets du mod�le.
 * <p> Un Sommet est caract�ris� par les informations suivantes :
 * <ul><li>Une coordon�e X, suceptible d'�tre chang�e.</li>
 * <li>Une coordon�e Y, suceptible d'�tre chang�e.</li>
 * <li>Une coordon�e Z, suceptible d'�tre chang�e.</li></ul></p>
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Math�o Gallego
 */
public class Sommet {
	/**
	 * La coordon�e X du Sommet.
	 */
	private float x;
	/**
	 * La coordon�e Y du Sommet.
	 */
	private float y;
	/**
	 * La coordon�e Z du Sommet.
	 */
	private float z;

	/**
	 * Constructeur Sommet.
	 * @param x
	 * 				La coordon�e X donn�e au Sommet.
	 * @param y
	 * 				La coordon�e Y donn�e au Sommet.
	 * @param z
	 * 				La coordon�e Z donn�e au Sommet.
	 */
	public Sommet (float x , float y , float z)	{
		this.x=x;
		this.y=y;
		this.z=z;
	}
	/**
	 * Constructeur Sommet.
	 * <p>La construction d'un objet Sommet() appelle son constructeur homonyme.
	 * <ul><li>Sommet(1, 1, 0)</li></ul></p>
	 * @see Sommet#Sommet(float, float, float)
	 */
	public Sommet() {
		this(1,1,0);
	}

	/**
	 * Retourne la coordonn�e X du Sommet.
	 * @return la coordonn�e X du Sommet, sous forme d'un float.
	 */
	public float getX() {
		return x;
	}
	/**
	 * Retourne la coordonn�e Y du Sommet.
	 * @return la coordonn�e Y du Sommet, sous forme d'un float.
	 */
	public float getY() {
		return y;
	}
	/**
	 * Retourne la coordonn�e Z du Sommet.
	 * @return la coordonn�e Z du Sommet, sous forme d'un float.
	 */
	public float getZ() {
		return z; 
	}
	/**
	 * Met � jour la coordonn�e X du Sommet.
	 * @param x
	 * 				La nouvelle coordonn�e X du Sommet.
	 */
	public void setX(float x) {
		this.x = x;
	}
	/**
	 * Met � jour la coordonn�e Y du Sommet.
	 * @param y
	 * 				La nouvelle coordonn�e Y du Sommet.
	 */
	public void setY(float y) {
		this.y = y;
	}
	/**
	 * Met � jour la coordonn�e Z du Sommet.
	 * @param z
	 * 				La nouvelle coordonn�e Z du Sommet.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * Retourne une chaine de caract�res contenant les 3 coordonn�es du Sommet.
	 * @return Les 3 coordonn�es du Sommet [x, y, z] s�par�es par des virgules, sous la forme d'une chaine de caract�re.
	 */
	public String toString() {
		return "Sommet ["+x+", "+y+", "+z+"]";
	}


}