package mouvement;

import java.util.ArrayList;

import dessin.Face;
import dessin.Sommet;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Rotation extends Movement{
	
	public Rotation(GraphicsContext gc ,Canvas c,ArrayList<Sommet> listeSommets,ArrayList<Face> listeFaces ) {
		super(gc,c, listeSommets, listeFaces);
	}

	public void rotateModelY() {
		rotateAxe('Y');
	}

	public void rotateModelZ() {
		rotateAxe('Z');
	}

	public void rotateModelX() {
		rotateAxe('X');
	}

	private void rotateAxe(char c) {
		gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		double pi = Math.PI;
		int facteurAngle = 10;
		float[][] matrice = { { (float) Math.cos(pi / facteurAngle), (float) -Math.sin(pi / facteurAngle), 0 },
				{ (float) Math.sin(pi / facteurAngle), (float) Math.cos(pi / facteurAngle), 0 }, { 0, 0, 1 } };
		if (c == 'X') {
			matrice = new float[][] { { 1, 0, 0 },
					{ 0, (float) Math.cos(pi / facteurAngle), (float) -Math.sin(pi / facteurAngle) },
					{ 0, (float) Math.sin(pi / facteurAngle), (float) Math.cos(pi / facteurAngle) } };

		} else if (c == 'Y') {
			matrice = new float[][] { { (float) Math.cos(pi / facteurAngle), 0, (float) -Math.sin(pi / facteurAngle) },
					{ 0, 1, 0 }, { (float) Math.sin(pi / facteurAngle), 0, (float) Math.cos(pi / facteurAngle) } };
		}
		rotateDegres(facteurAngle, matrice);
		for (Face f : listeFaces)
			dessinFace(f);
	}
	
	private void rotateDegres(int facteurAngle, float[][] matrice) {
		for (Sommet s : listeSommets) {
			float xtempo = s.x;
			float ytempo = s.y;
			float ztempo = s.z;
			s.x = xtempo * matrice[0][0] + ytempo * matrice[0][1] + ztempo * matrice[0][2];
			s.y = xtempo * matrice[1][0] + ytempo * matrice[1][1] + ztempo * matrice[1][2];
			s.z = xtempo * matrice[2][0] + ytempo * matrice[2][1] + ztempo * matrice[2][2];
		}
		if (getMinX() < 0)
			decalagePoints(-(int) (getMinX() - 1), 0, 0);
		if (getMinY() < 0)
			decalagePoints(0, -(int) (getMinY() - 1), 0);
		if (getMaxX() > CANVAS_WIDTH)
			decalagePoints(-(int) getMaxX() + CANVAS_WIDTH, 0, 0);
		if (getMaxY() > CANVAS_HEIGHT)
			decalagePoints(0, -(int) getMaxY() + CANVAS_HEIGHT, 0);
	}
}
