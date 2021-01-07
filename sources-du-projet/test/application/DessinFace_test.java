package application;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dessin.DessinFace;
import dessin.Sommet;

public class DessinFace_test {
	private DessinFace df;
	private List<Sommet> listSommet;

	@Before
	public void init() throws IOException {
		listSommet = new ArrayList<Sommet>();
		for (int i = 0; i < 10; i++) {
			listSommet.add(new Sommet(i,i+2,i+3));
		}
	}
	
	@Test 
	public void testActivEclairage() {
		df = new DessinFace();
		assertFalse(df.isActiverEclairage());
		df.setActiverEclairage(true);
		assertTrue(df.isActiverEclairage());
		df.setActiverEclairage(false);
		assertFalse(df.isActiverEclairage());
	}
	
	@Test 
	public void testgetMinX() {
		df = new DessinFace();
		df.setListeSommets(listSommet);
		assertEquals(listSommet.get(0).getX(), df.getMinX());
		assertNotEquals(listSommet.get(2).getX(), df.getMinX());
	}
	
	@Test 
	public void testgetMaxX() {
		df = new DessinFace();
		df.setListeSommets(listSommet);
		assertEquals(listSommet.get(listSommet.size()-1).getX(), df.getMaxX());
		assertNotEquals(listSommet.get(listSommet.size()-2).getX(), df.getMaxX());
	}
	
//	@Test 
	public void testgetMinY() {
		df = new DessinFace();
		df.setListeSommets(listSommet);
		assertEquals(listSommet.get(0).getY(), df.getMinY());
		assertNotEquals(listSommet.get(2).getY(), df.getMinY());
	}
	
	@Test 
	public void testgetMaxY() {
		df = new DessinFace();
		df.setListeSommets(listSommet);
		assertEquals(listSommet.get(listSommet.size()-1).getY(), df.getMaxY());
		assertNotEquals(listSommet.get(listSommet.size()-2).getY(), df.getMaxY());
	}
	
	@Test 
	public void testinitCentre() {
		df = new DessinFace();
		df.setListeSommets(listSommet);
		df.init();
		assertEquals(df.getCentreObjet().getX(), 4.5);
		assertEquals(df.getCentreObjet().getY(), 6.5);
	}
	
}	
