package application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Translate_test {
	private Controller control = new Controller();

	@BeforeEach
	public void init() throws IOException {
		control.initFaces(new File("./ressources/ledeatintinNB.ply"));
		
	}
	
	@Test
	public void testTranslateX () {
		Controller tmp = new Controller();
		tmp.decalagePoints(50, 0, 0);
		for (int i = 0; i < control.getListeSommets().size(); i++) {
			assertEquals(tmp.getListeSommets().get(i).getX(),control.getListeSommets().get(i).getX()+50);
		}
	}
	
	@Test
	public void testTranslateY () {
		Controller tmp = new Controller();
		tmp.decalagePoints(0, 50, 0);
		for (int i = 0; i < control.getListeSommets().size(); i++) {
			assertEquals(tmp.getListeSommets().get(i).getY(),control.getListeSommets().get(i).getY()+50);
		}
	}
	
	
}
