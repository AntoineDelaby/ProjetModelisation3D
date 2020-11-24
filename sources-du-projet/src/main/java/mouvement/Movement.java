package mouvement;

import java.util.ArrayList;

import dessin.Face;
import dessin.Sommet;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public abstract class Movement {
	protected GraphicsContext gc;
	protected int CANVAS_WIDTH;
	protected int CANVAS_HEIGHT;
	protected Canvas canvas;
	
	protected ArrayList<Sommet> listeSommets ;
	protected ArrayList<Face> listeFaces;
	
	protected Movement(GraphicsContext gc ,Canvas c,ArrayList<Sommet> listeSommets,ArrayList<Face> listeFaces ) {
		this.gc = gc;
		this.gc = c.getGraphicsContext2D();
		CANVAS_WIDTH = (int) c.getWidth();
		CANVAS_HEIGHT = (int) c.getHeight();
		this.listeSommets = listeSommets;
		this.listeFaces = listeFaces;
		canvas =c;
	}
	
	protected float getMaxY() {
		float res = 0;
		for (Sommet s : listeSommets) {
			if (s.y > res)
				res = s.y;
		}
		return res;
	}

	protected float getMaxX() {
		float res = 0;
		for (Sommet s : listeSommets) {
			if (s.x > res)
				res = s.x;
		}
		return res;
	}

	protected float getMinX() {
		float res = 0;
		for (Sommet s : listeSommets) {
			if (s.x < res)
				res = s.x;
		}
		return res;
	}
	
	public float getMin() {
		float res = 0;
		for (Sommet s : listeSommets) {
			if (s.x < res)
				res = s.x;
			if (s.y < res)
				res = s.y;
			if (s.z < res)
				res = s.z;
		}
		return res;
	}
	
	protected float getMinZ() {
		float min = listeSommets.get(0).getZ();
		for (int i = 1; i < listeSommets.size(); i++) {
			if (listeSommets.get(i).getZ() < min) {
				min = listeSommets.get(i).getZ();
			}
		}
		return min;
	}
	
	protected float getMinY() {
		float res = 0;
		for (Sommet s : listeSommets) {
			if (s.y < res)
				res = s.y;
		}
		return res;
	}
	
	protected void decalagePoints(int x, int y, int z) {
		for (Sommet s : listeSommets) {
			s.x += x;
			s.y += y;
			s.z += z;
		}
	}
	
	protected void dessinFace(Face f) {
		gc.setStroke(Paint.valueOf(""+Color.RED));
		gc.beginPath();
		gc.moveTo(listeSommets.get(f.getSommets().get(0)).getX(), listeSommets.get(f.getSommets().get(0)).getY());
		gc.lineTo(listeSommets.get(f.getSommets().get(1)).getX(), listeSommets.get(f.getSommets().get(1)).getY());
		gc.lineTo(listeSommets.get(f.getSommets().get(2)).getX(), listeSommets.get(f.getSommets().get(2)).getY());
		gc.lineTo(listeSommets.get(f.getSommets().get(0)).getX(), listeSommets.get(f.getSommets().get(0)).getY());
		gc.stroke();
	}

	public void setGc(GraphicsContext gc) {
		this.gc = gc;
	}
	
	

}
