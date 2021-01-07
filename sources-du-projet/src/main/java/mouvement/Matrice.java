package mouvement;

import java.util.ArrayList;
import java.util.List;
import dessin.Sommet;

/**
 * Matrice est la classe pour les matrices du mod�le.
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Math�o Gallego
 */
public class Matrice {
	
	/**
	 * Cr�� et retourne une Matrice � partir d'une liste de Sommets.
	 * @param listeSommets
	 * 					La liste des sommets du mod�le.
	 * @return une matrice flotante statique sous la forme {{x1,y1,z1,1},{x2,y2,z2,1},{...}}
	 */
	public static float[][] toMatrice(List<Sommet> listeSommets){
		float[][]res = new float[4][listeSommets.size()];
		for(int i = 0; i < listeSommets.size(); i++) {
			res[0][i] = listeSommets.get(i).getX();
			res[1][i] = listeSommets.get(i).getY();
			res[2][i] = listeSommets.get(i).getZ();
			res[3][i] = 1;
		}
		return res;
	}
	/**
	 * Cr�� et retourne une Liste de Sommets � partir d'une Matrice.
	 * @param matrice
	 * 					La matrice des sommets du mod�le.
	 * @return une liste List statique sous la forme {s1,s2,s3,(...)}
	 */
	public static List<Sommet> toList(float[][] matrice){
		List<Sommet>res = new ArrayList<Sommet>();
		for(int i = 0; i < matrice[0].length; i++) {
			res.add(new Sommet(matrice[0][i], matrice[1][i], matrice[2][i]));
		}
		return res;
	}
}
