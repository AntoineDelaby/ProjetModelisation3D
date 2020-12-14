package mouvement;

import java.util.List;

import dessin.Face;
import dessin.Sommet;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Rotation extends Movement{
	private double facteurRotation;
	private double pi = Math.PI;

	public Rotation(GraphicsContext gc ,Canvas c,List<Sommet> list,List<Face> list2 ) {
		super(gc,c, list, list2);
		facteurRotation=0;
	}

	public void rotate(char axe, double facteur) {
		gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		if(facteur != facteurRotation && facteur >0 && facteur<360) {
			facteurRotation = facteur;
			System.out.println(facteurRotation);
			float[][] matrice = rotateAxe(axe);

			rotateDegres(matrice);
		}
	}

	private float[][] rotateAxe(char axe) {
		float[][] matrice = null;
		if (axe == 'X') {
			matrice = new float[][] { { 1, 0, 0 },
				{ 0, (float) Math.cos(pi / facteurRotation), (float) -Math.sin(pi / facteurRotation) },
				{ 0, (float) Math.sin(pi / facteurRotation), (float) Math.cos(pi / facteurRotation) } };
		} else if (axe == 'Y') {
			matrice = new float[][] { { (float) Math.cos(pi / facteurRotation), 0, (float) -Math.sin(pi / facteurRotation) },
				{ 0, 1, 0 },
				{ (float) Math.sin(pi / facteurRotation), 0, (float) Math.cos(pi / facteurRotation) } };
		} else if (axe == 'Z'){
			matrice = new float[][] { { (float) Math.cos(pi / facteurRotation), (float) -Math.sin(pi / facteurRotation), 0 },
				{ (float) Math.sin(pi / facteurRotation), (float) Math.cos(pi / facteurRotation), 0 },
				{ 0, 0, 1 } };
		}
		return matrice;
	}

	private void rotateDegres(float[][] matrice) {
		for (Sommet s : listeSommets) {
			float xtempo = s.getX();
			float ytempo = s.getY();
			float ztempo = s.getZ();
			s.setX(xtempo * matrice[0][0] + ytempo * matrice[0][1] + ztempo * matrice[0][2]);
			s.setY(xtempo * matrice[1][0] + ytempo * matrice[1][1] + ztempo * matrice[1][2]);
			s.setZ(xtempo * matrice[2][0] + ytempo * matrice[2][1] + ztempo * matrice[2][2]);
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
