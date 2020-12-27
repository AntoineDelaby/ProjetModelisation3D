package application;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mouvement.Rotation;

public class Rotation_test {
	private float[][]matriceTest;
	private Rotation rotation;
	
	private float cosT = (float) Math.cos(Math.PI/Rotation.FACTEURROTATION);
	private float sinT = (float) Math.sin(Math.PI/Rotation.FACTEURROTATION);
	
	@BeforeEach
	public void init() throws IOException {
		this.matriceTest = new float[][] {{1,2,3,4},{4,3,2,1},{5,6,7,8},{1,1,1,1}};
	}

	@Test
	public void testRotationXSimple() {
		float[][]resAttendu = new float[][] {
			{1,2,3,4},
			{4 * cosT - 5 * sinT, 3 * cosT - 6 * sinT, 2 * cosT - 7 * sinT, 1 * cosT - 8 * sinT},
			{4 * sinT + 5 * cosT, 3 * sinT + 6 * cosT, 2 * sinT + 7 * cosT, 1 * sinT + 8 * cosT},
			{1,1,1,1},
		};
		this.rotation = new Rotation(null, 'x', Rotation.FACTEURROTATION);
		this.rotation.effectuerMouvement(matriceTest);
		/*printTab(matriceTest);
		System.out.println();
		printTab(resAttendu);*/
		for(int i = 0; i < matriceTest[0].length; i++) {
			assertEquals(this.matriceTest[0][i], resAttendu[0][i]);
			assertEquals(this.matriceTest[1][i], resAttendu[1][i]);
			assertEquals(this.matriceTest[2][i], resAttendu[2][i]);
			assertEquals(this.matriceTest[3][i], resAttendu[3][i]);
		}
	}
	
	@Test
	public void testRotationYSimple() {
		float[][]resAttendu = new float[][] {
			{1 * cosT - 5 * sinT, 2 * cosT - 6 * sinT, 3 * cosT - 7 * sinT, 4 * cosT - 8 * sinT},
			{4,3,2,1},
			{1 * sinT + 5 * cosT, 2 * sinT + 6 * cosT, 3 * sinT + 7 * cosT, 4 * sinT + 8 * cosT},
			{1,1,1,1},
		};
		this.rotation = new Rotation(null, 'y', Rotation.FACTEURROTATION);
		this.rotation.effectuerMouvement(matriceTest);
		/*printTab(matriceTest);
		System.out.println();
		printTab(resAttendu);*/
		for(int i = 0; i < matriceTest[0].length; i++) {
			assertEquals(this.matriceTest[0][i], resAttendu[0][i]);
			assertEquals(this.matriceTest[1][i], resAttendu[1][i]);
			assertEquals(this.matriceTest[2][i], resAttendu[2][i]);
			assertEquals(this.matriceTest[3][i], resAttendu[3][i]);
		}
	}
	
	@Test
	public void testRotationZSimple() {
		float[][]resAttendu = new float[][] {
			{1 * cosT - 4 * sinT, 2 * cosT - 3 * sinT, 3 * cosT - 2 * sinT, 4 * cosT - 1 * sinT},
			{1 * sinT + 4 * cosT, 2 * sinT + 3 * cosT, 3 * sinT + 2 * cosT, 4 * sinT + 1 * cosT},
			{5,6,7,8},
			{1,1,1,1},
		};
		this.rotation = new Rotation(null, 'z', Rotation.FACTEURROTATION);
		this.rotation.effectuerMouvement(matriceTest);
		/*printTab(matriceTest);
		System.out.println();
		printTab(resAttendu);*/
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