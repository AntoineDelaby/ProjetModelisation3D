package application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Zoom_test {
	private Controller control;

	@BeforeEach
	public void init() throws IOException {
		control.initFaces(new File("./ressources/ledeatintinNB.ply"));
		
	}
	
	@Test
	public void zoom() throws IOException {
		Controller tmp = control;
		control.zoomOnModel();
		/*for (int i = 0; i < control.listeSommets.size(); i++) {
			assertEquals(tmp.listeSommets.get(i).getX()*1,control.listeSommets.get(i).getX());
			assertEquals(tmp.listeSommets.get(i).getY()*1,control.listeSommets.get(i).getY());
			assertEquals(tmp.listeSommets.get(i).getZ()*1,control.listeSommets.get(i).getZ());
		}*/
	}
	
	@Test
	public void deZoom() {
		
	}
	
}
