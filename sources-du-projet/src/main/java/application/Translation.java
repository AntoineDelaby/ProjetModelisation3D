package application;

import javafx.scene.canvas.Canvas;

public class Translation extends Movement{
	
	public Translation(Canvas c) {
		super(c);
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
		if (getMaxY() < 0)
			decalagePoints(0, -(int) (getMaxY() - 1), 0);
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
