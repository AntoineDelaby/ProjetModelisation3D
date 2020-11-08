package application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Rotate_test {
	private Controller control = new Controller();
	private final double pi =Math.PI;
	private final int facteurAngle=10;
	@BeforeEach
	public void init() throws IOException {
		control.initFaces(new File("./ressources/ledeatintinNB.ply"));
	}

	@Test
	public void rotateX() throws IOException {
		Controller tmp = new Controller();
		tmp.initFaces(new File("./ressources/ledeatintinNB.ply"));
		float[][] matrice = new float[][] {{1,0,0}, {0,(float) Math.cos(pi/facteurAngle),(float) - Math.sin(pi/facteurAngle)},{0,(float) Math.sin(pi/facteurAngle),(float) Math.cos(pi/facteurAngle)}};
		control.rotateDegret(facteurAngle, matrice);
		for (int i = 0; i < control.getListeSommets().size(); i++) {		
			float xtempo = tmp.getListeSommets().get(i).getX();
			float ytempo = tmp.getListeSommets().get(i).getY();
			float ztempo = tmp.getListeSommets().get(i).getZ();
			assertEquals(xtempo*matrice[0][0]+ytempo*matrice[0][1]+ztempo*matrice[0][2],control.getListeSommets().get(i).getX());
			assertEquals(matrice[1][0]+ytempo*matrice[1][1]+ztempo*matrice[1][2],control.getListeSommets().get(i).getY());
			assertEquals(matrice[2][0]+ytempo*matrice[2][1]+ztempo*matrice[2][2],control.getListeSommets().get(i).getZ());
		}
	}
	
	@Test
	public void rotateY() throws IOException {
		Controller tmp = new Controller();
		tmp.initFaces(new File("./ressources/ledeatintinNB.ply"));
		float[][] matrice = new float[][] {{(float)Math.cos(pi/facteurAngle),0,(float)-Math.sin(pi/facteurAngle)},{0,1,0},{(float)Math.sin(pi/facteurAngle),0,(float)Math.cos(pi/facteurAngle)}};
		control.rotateDegret(facteurAngle, matrice);
		for (int i = 0; i < control.getListeSommets().size(); i++) {		
			float xtempo = tmp.getListeSommets().get(i).getX();
			float ytempo = tmp.getListeSommets().get(i).getY();
			float ztempo = tmp.getListeSommets().get(i).getZ();
			assertEquals(xtempo*matrice[0][0]+ytempo*matrice[0][1]+ztempo*matrice[0][2],control.getListeSommets().get(i).getX());
			assertEquals(matrice[1][0]+ytempo*matrice[1][1]+ztempo*matrice[1][2],control.getListeSommets().get(i).getY());
			assertEquals(matrice[2][0]+ytempo*matrice[2][1]+ztempo*matrice[2][2],control.getListeSommets().get(i).getZ());
		}	
	}
	
	@Test
	public void rotateZ() throws IOException {
		Controller tmp = new Controller();
		tmp.initFaces(new File("./ressources/ledeatintinNB.ply"));
		float[][] matrice = {{(float)Math.cos(pi/facteurAngle),(float)-Math.sin(pi/facteurAngle),0},{(float)Math.sin(pi/facteurAngle),(float)Math.cos(pi/facteurAngle),0},{0,0,1}};
		control.rotateDegret(facteurAngle, matrice);
		for (int i = 0; i < control.getListeSommets().size(); i++) {		
			float xtempo = tmp.getListeSommets().get(i).getX();
			float ytempo = tmp.getListeSommets().get(i).getY();
			float ztempo = tmp.getListeSommets().get(i).getZ();
			assertEquals(xtempo*matrice[0][0]+ytempo*matrice[0][1]+ztempo*matrice[0][2],control.getListeSommets().get(i).getX());
			assertEquals(matrice[1][0]+ytempo*matrice[1][1]+ztempo*matrice[1][2],control.getListeSommets().get(i).getY());
			assertEquals(matrice[2][0]+ytempo*matrice[2][1]+ztempo*matrice[2][2],control.getListeSommets().get(i).getZ());
		}
	}
}
