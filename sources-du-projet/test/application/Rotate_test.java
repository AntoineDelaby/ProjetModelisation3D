package application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Rotate_test {
	private Controller control = new Controller();
	private double pi =Math.PI;
	private int facteurAngle=10;
	@BeforeEach
	public void init() throws IOException {
		control.initFaces(new File("./ledeatintinNB.ply"));
	}

	@Test
	public void rotateX() throws IOException {
		Controller tmp = new Controller();
		tmp.initFaces(new File("./ledeatintinNB.ply"));
		float[][] matrice = new float[][] {{1,0,0}, {0,(float) Math.cos(pi/facteurAngle),(float) - Math.sin(pi/facteurAngle)},{0,(float) Math.sin(pi/facteurAngle),(float) Math.cos(pi/facteurAngle)}};
		for (Sommet s : control.listeSommets) {
			float xtempo = s.x;
			float ytempo = s.y;
			float ztempo = s.z;
			s.x=xtempo*matrice[0][0]+ytempo*matrice[0][1]+ztempo*matrice[0][2];
			s.y=xtempo*matrice[1][0]+ytempo*matrice[1][1]+ztempo*matrice[1][2];
			s.z=xtempo*matrice[2][0]+ytempo*matrice[2][1]+ztempo*matrice[2][2];
			System.out.println(s.x+","+s.y+","+s.z);
		}	
	}
	
	@Test
	public void rotateY() throws IOException {
		Controller tmp = new Controller();
		tmp.initFaces(new File("./ledeatintinNB.ply"));
		float[][] matrice = new float[][] {{(float)Math.cos(pi/facteurAngle),0,(float)-Math.sin(pi/facteurAngle)},{0,1,0},{(float)Math.sin(pi/facteurAngle),0,(float)Math.cos(pi/facteurAngle)}};
		for (Sommet s : control.listeSommets) {
			float xtempo = s.x;
			float ytempo = s.y;
			float ztempo = s.z;
			s.x=xtempo*matrice[0][0]+ytempo*matrice[0][1]+ztempo*matrice[0][2];
			s.y=xtempo*matrice[1][0]+ytempo*matrice[1][1]+ztempo*matrice[1][2];
			s.z=xtempo*matrice[2][0]+ytempo*matrice[2][1]+ztempo*matrice[2][2];
		}	
	}
	
	@Test
	public void rotateZ() throws IOException {
		Controller tmp = new Controller();
		tmp.initFaces(new File("./ledeatintinNB.ply"));
		float[][] matrice = {{(float)Math.cos(pi/facteurAngle),(float)-Math.sin(pi/facteurAngle),0},{(float)Math.sin(pi/facteurAngle),(float)Math.cos(pi/facteurAngle),0},{0,0,1}};
		for (Sommet s : control.listeSommets) {
			float xtempo = s.x;
			float ytempo = s.y;
			float ztempo = s.z;
			s.x=xtempo*matrice[0][0]+ytempo*matrice[0][1]+ztempo*matrice[0][2];
			s.y=xtempo*matrice[1][0]+ytempo*matrice[1][1]+ztempo*matrice[1][2];
			s.z=xtempo*matrice[2][0]+ytempo*matrice[2][1]+ztempo*matrice[2][2];
		}	
	}
}
