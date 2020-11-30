package mouvement;

import java.util.ArrayList;

import dessin.Face;
import dessin.Sommet;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Translation extends Movement{
	
	public Translation(GraphicsContext gc ,Canvas c,ArrayList<Sommet> listeSommets,ArrayList<Face> listeFaces ) {
		super(gc,c, listeSommets, listeFaces);
	}
	
	public void translateDroite() {
		gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		decalagePoints(50, 0, 0);
		if (getMaxX() > CANVAS_WIDTH)
			decalagePoints(-(int) getMaxX() + CANVAS_WIDTH, 0, 0);
		for (Face f : listeFaces)
			dessinFace(f);
	}

	public void translateGauche() {
		gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		decalagePoints(-50, 0, 0);
		if (getMinX() < 0)
			decalagePoints(-(int) (getMinX() - 1), 0, 0);
		for (Face f : listeFaces)
			dessinFace(f);
	}

	public void translateHaut() {
		gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		decalagePoints(0, -50, 0);
		if (getMinY() < 0)
			decalagePoints(0, -(int) (getMinY() - 1), 0);
		for (Face f : listeFaces)
			dessinFace(f);
	} 

	public void translateBas() {
		gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		decalagePoints(0, 50, 0);
		if (getMaxY() > CANVAS_HEIGHT)
			decalagePoints(0, -(int) getMaxY() + CANVAS_HEIGHT, 0);
		for (Face f : listeFaces)
			dessinFace(f);
	}
	
}
