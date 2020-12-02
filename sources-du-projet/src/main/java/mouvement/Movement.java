package mouvement;

import java.util.List;

import dessin.Face;
import dessin.Sommet;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class Movement {
	protected GraphicsContext gc;
	protected int CANVAS_WIDTH;
	protected int CANVAS_HEIGHT;
	protected Canvas canvas;
	protected float[][]matriceSommets;
	
	protected List<Sommet> listeSommets ;
	protected List<Face> listeFaces;
	
	protected Movement(GraphicsContext gc ,Canvas c,List<Sommet> list,List<Face> list2 ) {
		this.gc = gc;
		this.gc = c.getGraphicsContext2D();
		CANVAS_WIDTH = (int) c.getWidth();
		CANVAS_HEIGHT = (int) c.getHeight();
		this.listeSommets = list;
		this.listeFaces = list2;
		canvas =c;
	}
	
	protected float getMaxY() {
		float res = 0;
		for (Sommet s : listeSommets) {
			if (s.getY() > res)
				res = s.getY();
		}
		return res;
	}

	protected float getMaxX() {
		float res = 0;
		for (Sommet s : listeSommets) {
			if (s.getX() > res)
				res = s.getX();
		}
		return res;
	}

	protected float getMinX() {
		float res = CANVAS_WIDTH;
		for (Sommet s : listeSommets) {
			if (s.getX() < res)
				res = s.getX();
		}
		return res;
	}
	
	public float getMin() {
		float res = 0;
		for (Sommet s : listeSommets) {
			if (s.getX() < res)
				res = s.getX();
			if (s.getY() < res)
				res = s.getY();
			if (s.getZ() < res)
				res = s.getZ();
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
		float res = CANVAS_HEIGHT;
		for (Sommet s : listeSommets) {
			if (s.getY() < res)
				res = s.getY();
		}
		return res;
	}
	
	protected void decalagePoints(int x, int y, int z) {
		for (Sommet s : listeSommets) {
			s.setX(s.getX() + x);
			s.setY(s.getY() + y);
			s.setZ(s.getZ() + z);
		}
	}
	
	protected void dessinFace(Face f) {
		gc.beginPath();
		gc.moveTo(listeSommets.get(f.getSommets().get(0)).getX(), listeSommets.get(f.getSommets().get(0)).getY());
		gc.lineTo(listeSommets.get(f.getSommets().get(1)).getX(), listeSommets.get(f.getSommets().get(1)).getY());
		gc.lineTo(listeSommets.get(f.getSommets().get(2)).getX(), listeSommets.get(f.getSommets().get(2)).getY());
		gc.lineTo(listeSommets.get(f.getSommets().get(0)).getX(), listeSommets.get(f.getSommets().get(0)).getY());
		double [] x = new double [] {listeSommets.get(f.getSommets().get(0)).getX(),listeSommets.get(f.getSommets().get(1)).getX(),listeSommets.get(f.getSommets().get(2)).getX()};
		double [] y = new double [] {listeSommets.get(f.getSommets().get(0)).getY(),listeSommets.get(f.getSommets().get(1)).getY(),listeSommets.get(f.getSommets().get(2)).getY()};
		gc.fillPolygon(x, y, 3);
		gc.stroke(); 
	}

	public void setGc(GraphicsContext gc) {
		this.gc = gc;
	}
	
	

}
