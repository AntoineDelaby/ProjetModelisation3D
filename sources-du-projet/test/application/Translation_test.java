package application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dessin.Sommet;
import mouvement.Translation;

public class Translation_test {
	private float[][]matriceTest;
	private Translation translation;
	private Sommet sommet;
	
	@BeforeEach
	public void init() throws IOException {
		this.matriceTest = new float[][] {{1,2,3,4},{4,3,2,1},{5,6,7,8},{1,1,1,1}};
	}

	@Test
	public void testModelNull() {
		float[][]resAttendu = new float[][] {
			{1,2,3,4},
			{4,3,2,1},
			{5,6,7,8},
			{1,1,1,1},
		};
		this.translation = new Translation(null, 'g');
		this.translation.effectuerMouvement(null);
		for(int i = 0; i < matriceTest[0].length; i++) {
			assertEquals(this.matriceTest[0][i], resAttendu[0][i]);
			assertEquals(this.matriceTest[1][i], resAttendu[1][i]);
			assertEquals(this.matriceTest[2][i], resAttendu[2][i]);
			assertEquals(this.matriceTest[3][i], resAttendu[3][i]);
		}
	}
	
	@Test
	public void testTranslationGaucheSimple() {
		float[][]resAttendu = new float[][] {
			{-49,-48,-47,-46},
			{4,3,2,1},
			{5,6,7,8},
			{1,1,1,1},
		};
		this.translation = new Translation(null, 'g');
		this.translation.effectuerMouvement(matriceTest);
		for(int i = 0; i < matriceTest[0].length; i++) {
			assertEquals(this.matriceTest[0][i], resAttendu[0][i]);
			assertEquals(this.matriceTest[1][i], resAttendu[1][i]);
			assertEquals(this.matriceTest[2][i], resAttendu[2][i]);
			assertEquals(this.matriceTest[3][i], resAttendu[3][i]);
		}
	}
	
	@Test
	public void testTranslationDroiteSimple() {
		float[][]resAttendu = new float[][] {
			{51,52,53,54},
			{4,3,2,1},
			{5,6,7,8},
			{1,1,1,1},
		};
		this.translation = new Translation(null, 'd');
		this.translation.effectuerMouvement(matriceTest);
		for(int i = 0; i < matriceTest[0].length; i++) {
			assertEquals(this.matriceTest[0][i], resAttendu[0][i]);
			assertEquals(this.matriceTest[1][i], resAttendu[1][i]);
			assertEquals(this.matriceTest[2][i], resAttendu[2][i]);
			assertEquals(this.matriceTest[3][i], resAttendu[3][i]);
		}
	}
	
	@Test
	public void testTranslationHautSimple() {
		float[][]resAttendu = new float[][] {
			{1,2,3,4},
			{-46,-47,-48,-49},
			{5,6,7,8},
			{1,1,1,1},
		};
		this.translation = new Translation(null, 'h');
		this.translation.effectuerMouvement(matriceTest);
		for(int i = 0; i < matriceTest[0].length; i++) {
			assertEquals(this.matriceTest[0][i], resAttendu[0][i]);
			assertEquals(this.matriceTest[1][i], resAttendu[1][i]);
			assertEquals(this.matriceTest[2][i], resAttendu[2][i]);
			assertEquals(this.matriceTest[3][i], resAttendu[3][i]);
		}
	}
	
	@Test
	public void testTranslationBasSimple() {
		float[][]resAttendu = new float[][] {
			{1,2,3,4},
			{54,53,52,51},
			{5,6,7,8},
			{1,1,1,1},
		};
		this.translation = new Translation(null, 'b');
		this.translation.effectuerMouvement(matriceTest);
		System.out.println();
		for(int i = 0; i < matriceTest[0].length; i++) {
			assertEquals(this.matriceTest[0][i], resAttendu[0][i]);
			assertEquals(this.matriceTest[1][i], resAttendu[1][i]);
			assertEquals(this.matriceTest[2][i], resAttendu[2][i]);
			assertEquals(this.matriceTest[3][i], resAttendu[3][i]);
		}
	}
	
	@BeforeEach
	public void before() {
		translation = new Translation();
		sommet = new Sommet(50,50,50);
	}
	
	@Test
	public void testSommetNull() {
		translation.modifCentre(null);
		assertEquals(sommet.getX(), 50);
		assertEquals(sommet.getY(), 50);
		assertEquals(sommet.getZ(), 50);
	}
	
	@Test
	public void testModifCentreDroite() {
		translation.setDirection('d');
		translation.modifCentre(sommet);
		assertEquals(sommet.getX(), 100);
		assertEquals(sommet.getY(), 50);
		assertEquals(sommet.getZ(), 50);
	}
	
	@Test
	public void testModifCentreBas() {
		translation.setDirection('b');
		translation.modifCentre(sommet);
		assertEquals(sommet.getX(), 50);
		assertEquals(sommet.getY(), 100);
		assertEquals(sommet.getZ(), 50);
	}
	
	@Test
	public void testModifCentreHaut() {
		translation.setDirection('h');
		translation.modifCentre(sommet);
		assertEquals(sommet.getX(), 50)
		;assertEquals(sommet.getY(), 0);
		assertEquals(sommet.getZ(), 50);
	}
	
	@Test
	public void testModifCentreGauche() {
		translation.setDirection('g');
		translation.modifCentre(sommet);
		assertEquals(sommet.getX(), 0);
		assertEquals(sommet.getY(), 50);
		assertEquals(sommet.getZ(), 50);
	}
}
