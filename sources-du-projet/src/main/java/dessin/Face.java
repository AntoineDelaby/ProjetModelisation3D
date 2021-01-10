package dessin;

import java.util.ArrayList;

/**
 * Face est la classe pour les Faces du mod�le.
 * <p> Une Face est caract�ris�e par :
 * <ul><li>Une liste de d'entiers.</li></ul></p>
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Math�o Gallego
 */
public class Face {
	/**
	 * La liste des Sommets de la Face.
	 * @see Face#getSommets()
	 */
	private ArrayList<Integer> sommets;
	
	/**
	 * Constructeur Face.
	 * <p> A la construction d'un objet Face, la liste des Sommets est cr��e vide.</p>
	 */
	public Face() {
		this.sommets = new ArrayList<Integer>();
	}
	
	/**
	 * Ajoute un Sommet � la liste des Sommets de la Face.
	 * @param s
	 * 			L'id du Sommet du fichier ply � ajouter � la Face.
	 */
	public void addSommet(int s) {
		sommets.add(s);
	}

	/**
	 * Retourne la liste des id des Sommets de la Face.
	 * @return les id des Sommets de la Face, sous forme d'une liste d'entiers.
	 */
	public ArrayList<Integer> getSommets() {
		return sommets;
	}

	/**
	 * Retourne l'id du Sommet n�i de la liste des Sommets de la Face.
	 * @param i
	 * 			le num�ro du sommet dont on souhaite obtenir l'id.
	 * @return L'id du Sommet de la liste des Sommets de la Face, sous forme d'entier.
	 */
	public int getSommetI(int i) {
		return sommets.get(i);
	}

	/**
	 * Retourne une chaine de caract�res contenant les id des Sommets de la Face.
	 * @return Les id des Sommet de la Face [id1, id2, id3, ...] s�par�es par des virgules, sous la forme d'une chaine de caract�re.
	 */
	public String toString() {
		String res = "Face : [" + this.sommets.get(0);
		for(int i = 1; i < this.sommets.size(); i++) {
			res += "," + this.sommets.get(i);
		}
		res += "]";
		return res;
	}
}
