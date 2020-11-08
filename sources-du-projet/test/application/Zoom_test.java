package application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Zoom_test {
	private Controller control = new Controller();

	@BeforeEach
	public void init() throws IOException {
		control.initFaces(new File("./ledeatintinNB.ply"));
		
	}
	
	@Test
	public void zoom() throws IOException {
		Controller tmp = new Controller();
		tmp.initFaces(new File("./ledeatintinNB.ply"));
		control.newCoordonZoom(3);
		for (int i = 0; i < control.listeSommets.size(); i++) {
			assertEquals(tmp.listeSommets.get(i).getX()*3,control.listeSommets.get(i).getX());
			assertEquals(tmp.listeSommets.get(i).getY()*3,control.listeSommets.get(i).getY());
			assertEquals(tmp.listeSommets.get(i).getZ()*3,control.listeSommets.get(i).getZ());
		}
	}
	
	@Test
	public void deZoom() throws IOException {
		Controller tmp = new Controller();
		tmp.initFaces(new File("./ledeatintinNB.ply"));
		control.newCoordonZoom(-3);
		for (int i = 0; i < control.listeSommets.size(); i++) {
			assertEquals(tmp.listeSommets.get(i).getX()*3,control.listeSommets.get(i).getX());
			assertEquals(tmp.listeSommets.get(i).getY()*3,control.listeSommets.get(i).getY());
			assertEquals(tmp.listeSommets.get(i).getZ()*3,control.listeSommets.get(i).getZ());
		}
	}
	
}
