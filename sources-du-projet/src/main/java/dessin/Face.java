package dessin;

import java.util.ArrayList;

/**
 * Face est la classe pour les Faces du modèle.
 * <p> Une Face est caractérisée par :
 * <ul><li>Une liste de d'entiers.</li></ul></p>
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Mathéo Gallego
 */
public class Face {
	/**
	 * La liste des Sommets de la Face.
	 * @see Face#getSommets()
	 */
	private ArrayList<Integer> sommets;
	
	/**
	 * Constructeur Face.
	 * <p> A la construction d'un objet Face, la liste des Sommets est créée vide.</p>
	 */
	public Face() {
		this.sommets = new ArrayList<Integer>();
	}
	
	/**
	 * Ajoute un Sommet à la liste des Sommets de la Face.
	 * @param s
	 * 			L'id du Sommet du fichier ply à ajouter à la Face.
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
	 * Retourne l'id du Sommet n°i de la liste des Sommets de la Face.
	 * @param i
	 * 			le numéro du sommet dont on souhaite obtenir l'id.
	 * @return L'id du Sommet de la liste des Sommets de la Face, sous forme d'entier.
	 */
	public int getSommetI(int i) {
		return sommets.get(i);
	}

	/**
	 * Retourne une chaine de caractères contenant les id des Sommets de la Face.
	 * @return Les id des Sommet de la Face [id1, id2, id3, ...] séparées par des virgules, sous la forme d'une chaine de caractère.
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
