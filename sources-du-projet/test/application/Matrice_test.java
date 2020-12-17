package application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dessin.Sommet;
import mouvement.Matrice;

public class Matrice_test {
	private Matrice mt = new Matrice();
	private float[][]matriceTest;
	private List<Sommet>listeTest;
	
	@BeforeEach
	public void init() throws IOException {
		this.matriceTest = new float[][] {{1,2,3,4},{4,3,2,1},{5,6,7,8},{1,1,1,1}};
		this.listeTest = new ArrayList<Sommet>();
		listeTest.add(new Sommet(1,4,5));
		listeTest.add(new Sommet(2,3,6));
		listeTest.add(new Sommet(3,2,7));
		listeTest.add(new Sommet(4,1,8));
	}

	@Test
	public void testToMatrice() {
		float[][]res = mt.toMatrice(listeTest);
		for(int i = 0; i < res[0].length; i++) {
			assertEquals(res[0][i], listeTest.get(i).getX());
			assertEquals(res[1][i], listeTest.get(i).getY());
			assertEquals(res[2][i], listeTest.get(i).getZ());
			assertEquals(res[3][i], 1);
		}
	}
	
	@Test
	public void testToList() {
		List<Sommet>res = mt.toList(matriceTest);
		for(int i = 0; i < res.size(); i++) {
			assertEquals(res.get(i).getX(), matriceTest[0][i]);
			assertEquals(res.get(i).getY(), matriceTest[1][i]);
			assertEquals(res.get(i).getZ(), matriceTest[2][i]);
		}
	}
}