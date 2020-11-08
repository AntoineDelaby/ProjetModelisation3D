package application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InfoFichierPly_test {
	
	private Controller control = new Controller();
	
	@BeforeEach
	public void init() throws IOException {
		control.initFaces(new File("./ledeatintinNB.ply")); 
	}
	
	@Test
	public void testNomFichier() { 
		assertEquals(control.getNameFile(), "ledeatintinNB");
	}
	
	@Test
	public void testNBfaces() {
		assertEquals(control.getNBfacesFile(), 16);
	}
}
