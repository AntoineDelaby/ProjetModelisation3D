package application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mouvement.Translation;

public class Translation_test {
	private float[][]matriceTest;
	private Translation translation;
	
	@BeforeEach
	public void init() throws IOException {
		this.matriceTest = new float[][] {{1,2,3,4},{4,3,2,1},{5,6,7,8},{1,1,1,1}};
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
		printTab(matriceTest);
		System.out.println();
		printTab(resAttendu);
		for(int i = 0; i < matriceTest[0].length; i++) {
			assertEquals(this.matriceTest[0][i], resAttendu[0][i]);
			assertEquals(this.matriceTest[1][i], resAttendu[1][i]);
			assertEquals(this.matriceTest[2][i], resAttendu[2][i]);
			assertEquals(this.matriceTest[3][i], resAttendu[3][i]);
		}
	}
	
	private void printTab(float[][]model) {
		for(int i = 0; i < model.length; i++) {
			for(int j = 0; j < model[0].length; j++) {
				System.out.println("|" + model[i][j]);
			}
			System.out.println();
		}
	}
}
