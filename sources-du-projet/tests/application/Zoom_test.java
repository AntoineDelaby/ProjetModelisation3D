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
		for (int i = 0; i < control.getListeSommets().size(); i++) {
			assertEquals(tmp.getListeSommets().get(i).getX()*3,control.getListeSommets().get(i).getX());
			assertEquals(tmp.getListeSommets().get(i).getY()*3,control.getListeSommets().get(i).getY());
			assertEquals(tmp.getListeSommets().get(i).getZ()*3,control.getListeSommets().get(i).getZ());
		}
	}
	
	@Test
	public void deZoom() throws IOException {
		Controller tmp = new Controller();
		tmp.initFaces(new File("./ledeatintinNB.ply"));
		control.newCoordonZoom(-3);
		for (int i = 0; i < control.getListeSommets().size(); i++) {
			assertEquals(tmp.getListeSommets().get(i).getX()*3,control.getListeSommets().get(i).getX());
			assertEquals(tmp.getListeSommets().get(i).getY()*3,control.getListeSommets().get(i).getY());
			assertEquals(tmp.getListeSommets().get(i).getZ()*3,control.getListeSommets().get(i).getZ());
		}
	}
	
}
