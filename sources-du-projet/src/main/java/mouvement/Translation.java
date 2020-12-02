package mouvement;

import java.util.List;

import dessin.Face;
import dessin.Sommet;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Translation extends Movement{
	
	private int decalage = 50;
	private char directionDroite = 'D', directionGauche = 'G', directionHaut = 'H', directionBas = 'B';
	
	public Translation(GraphicsContext graphicC, Canvas canvas, List<Sommet> list, List<Face> list2) {
		super(graphicC, canvas, list, list2);
	}
	
	public void translate(char direction) {
		gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		if (direction == directionDroite) {
			translateDroite();
		} else if (direction == directionGauche) {
			translateGauche();
		} else if (direction == directionHaut) {
			translateHaut();
		} else if (direction == directionBas) {
			translateBas();
		}
		for (Face f : listeFaces)
			dessinFace(f);
	}
	
	public void translateDroite() {
		if (getMaxX() + decalage > CANVAS_WIDTH) {
			decalagePoints(CANVAS_WIDTH - (int) getMaxX(), 0, 0);
		} else {
			decalagePoints(decalage, 0, 0);
		}
	}
	
	public void translateGauche() {
		if (getMinX() - decalage < 0) {
			decalagePoints(-(int) getMinX(), 0, 0);
		} else {
			decalagePoints(-decalage, 0, 0);
		}
	}
	
	public void translateHaut() {
		if (getMinY() - decalage < 0) {
			decalagePoints(0, -(int) getMinY(), 0);
		} else {
			decalagePoints(0, -decalage, 0);
		}
	}
	
	public void translateBas() {
		if (getMaxY() + decalage > CANVAS_HEIGHT) {
			decalagePoints(0, CANVAS_HEIGHT - (int) getMaxY(), 0);
		} else {
			decalagePoints(0, decalage, 0);
		}
	}
	
}
