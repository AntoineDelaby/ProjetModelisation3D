package mouvement;

import java.util.ArrayList;
import java.util.List;

import dessin.Sommet;

public class Matrice {
	
	public float[][]toMatrice(List<Sommet>listeSommets){
		float[][]res = new float[4][listeSommets.size()];
		for(int i = 0; i < listeSommets.size(); i++) {
			res[0][i] = listeSommets.get(i).getX();
			res[1][i] = listeSommets.get(i).getY();
			res[2][i] = listeSommets.get(i).getZ();
			res[3][i] = 1;
		}
		return res;
	}
	
	public List<Sommet>toList(float[][]matrice){
		List<Sommet>res = new ArrayList<Sommet>();
		for(int i = 0; i < matrice[0].length; i++) {
			res.add(new Sommet(matrice[0][i], matrice[1][i], matrice[2][i]));
		}
		return res;
	}
}
