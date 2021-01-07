package dessin;

/**
 * Sommet est la classe pour les Sommets du modèle.
 * <p> Un Sommet est caractérisé par les informations suivantes :
 * <ul><li>Une coordonée X, suceptible d'être changée.</li>
 * <li>Une coordonée Y, suceptible d'être changée.</li>
 * <li>Une coordonée Z, suceptible d'être changée.</li></ul></p>
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Mathéo Gallego
 */
public class Sommet {
	/**
	 * La coordonée X du Sommet.
	 */
	private float x;
	/**
	 * La coordonée Y du Sommet.
	 */
	private float y;
	/**
	 * La coordonée Z du Sommet.
	 */
	private float z;

	/**
	 * Constructeur Sommet.
	 * @param x
	 * 				La coordonée X donnée au Sommet.
	 * @param y
	 * 				La coordonée Y donnée au Sommet.
	 * @param z
	 * 				La coordonée Z donnée au Sommet.
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
	 * Retourne la coordonnée X du Sommet.
	 * @return la coordonnée X du Sommet, sous forme d'un float.
	 */
	public float getX() {
		return x;
	}
	/**
	 * Retourne la coordonnée Y du Sommet.
	 * @return la coordonnée Y du Sommet, sous forme d'un float.
	 */
	public float getY() {
		return y;
	}
	/**
	 * Retourne la coordonnée Z du Sommet.
	 * @return la coordonnée Z du Sommet, sous forme d'un float.
	 */
	public float getZ() {
		return z; 
	}
	/**
	 * Met à jour la coordonnée X du Sommet.
	 * @param x
	 * 				La nouvelle coordonnée X du Sommet.
	 */
	public void setX(float x) {
		this.x = x;
	}
	/**
	 * Met à jour la coordonnée Y du Sommet.
	 * @param y
	 * 				La nouvelle coordonnée Y du Sommet.
	 */
	public void setY(float y) {
		this.y = y;
	}
	/**
	 * Met à jour la coordonnée Z du Sommet.
	 * @param z
	 * 				La nouvelle coordonnée Z du Sommet.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * Retourne une chaine de caractères contenant les 3 coordonnées du Sommet.
	 * @return Les 3 coordonnées du Sommet [x, y, z] séparées par des virgules, sous la forme d'une chaine de caractère.
	 */
	public String toString() {
		return "Sommet ["+x+", "+y+", "+z+"]";
	}


}