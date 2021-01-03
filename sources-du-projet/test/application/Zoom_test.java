package application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mouvement.Zoom;

public class Zoom_test {
	private float[][]matriceTest;
	private Zoom zoom;

	@BeforeEach
	public void init() throws IOException {
		this.matriceTest = new float[][] {{1,2,3,4},{4,3,2,1},{5,6,7,8},{1,1,1,1}};
	}
	
	@Test
	public void testModelNull() {
		zoom = new Zoom(Zoom.FACTEUR_DEZOOM);
		zoom.zoom(null);
		float[][]resAttendu = new float[][] {
			{1,2,3,4},
			{4,3,2,1},
			{5,6,7,8},
			{1,1,1,1},
		};
		for(int i = 0; i < matriceTest[0].length; i++) {
			assertEquals(this.matriceTest[0][i], resAttendu[0][i]);
			assertEquals(this.matriceTest[1][i], resAttendu[1][i]);
			assertEquals(this.matriceTest[2][i], resAttendu[2][i]);
			assertEquals(this.matriceTest[3][i], resAttendu[3][i]);
		}
	}
	
	@Test
	public void testFacteurNull() {
		zoom = new Zoom(Zoom.FACTEUR_DEZOOM);
		zoom.zoom(null);
		float[][]resAttendu = new float[][] {
			{1,2,3,4},
			{4,3,2,1},
			{5,6,7,8},
			{1,1,1,1},
		};
		for(int i = 0; i < matriceTest[0].length; i++) {
			assertEquals(this.matriceTest[0][i], resAttendu[0][i]);
			assertEquals(this.matriceTest[1][i], resAttendu[1][i]);
			assertEquals(this.matriceTest[2][i], resAttendu[2][i]);
			assertEquals(this.matriceTest[3][i], resAttendu[3][i]);
		}
	}
	
	@Test
	public void testZoom() {
		zoom = new Zoom(Zoom.FACTEUR_ZOOM);
		zoom.zoom(matriceTest);
		float[][]resAttendu = new float[][] {
			{(float) 1.1,(float) 2.2,(float) 3.3000002,(float) 4.4},
			{(float) 4.4,(float) 3.3000002,(float) 2.2,(float) 1.1},
			{(float) 5.5,(float) 6.6000004,(float) 7.7000003,(float) 8.8},
			{(float) 1.0,(float) 1.0,(float) 1.0,(float) 1.0},
		};
		for(int i = 0; i < matriceTest[0].length; i++) {
			assertEquals(this.matriceTest[0][i], resAttendu[0][i]);
			assertEquals(this.matriceTest[1][i], resAttendu[1][i]);
			assertEquals(this.matriceTest[2][i], resAttendu[2][i]);
			assertEquals(this.matriceTest[3][i], resAttendu[3][i]);
		}
	}

	@Test
	public void testDezoom() {
		zoom = new Zoom(Zoom.FACTEUR_DEZOOM);
		zoom.zoom(matriceTest);
		float[][]resAttendu = new float[][] {
			{(float) 0.9,(float) 1.8,(float) 2.6999998,(float) 3.6},
			{(float) 3.6,(float) 2.6999998,(float) 1.8,(float) 0.9},
			{(float) 4.5,(float) 5.3999996,(float) 6.2999997,(float) 7.2},
			{(float) 1.0,(float) 1.0,(float) 1.0,(float) 1.0},
		};
		for(int i = 0; i < matriceTest[0].length; i++) {
			assertEquals(this.matriceTest[0][i], resAttendu[0][i]);
			assertEquals(this.matriceTest[1][i], resAttendu[1][i]);
			assertEquals(this.matriceTest[2][i], resAttendu[2][i]);
			assertEquals(this.matriceTest[3][i], resAttendu[3][i]);
		}
	}
	
}
