package dessin;

import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import mouvement.Matrice;
import mouvement.Mouvement;
import mouvement.Translation;

public class DessinFace {
	private GraphicsContext gc;
	private List<Sommet> listeSommets;
	private List<Face> listeFaces;	
	private int gcHeigth;
	private int gcWidth;
	private Sommet centreObjet;
	private Sommet facteur;

	public DessinFace(Canvas c, List<Sommet> listeSommets,List<Face> listeFaces) {
		this.gc = c.getGraphicsContext2D();
		this.listeFaces = listeFaces;
		this.listeSommets = listeSommets;
		gcHeigth = (int) c.getHeight();
		gcWidth = (int) c.getWidth();
		centreObjet = new Sommet();
		facteur = new Sommet();
	}

	public GraphicsContext getGc() {
		return gc;
	}

	public int getGcHeigth() {
		return gcHeigth;
	}

	public int getGcWidth() {
		return gcWidth;
	}

	public List<Sommet> getListeSommets() {
		return listeSommets;
	}

	public List<Face> getListeFaces() {
		return listeFaces;
	}

	public float getMinX() {
		float res = gcWidth;
		for (Sommet s : listeSommets) {
			if (s.getX() < res)
				res = s.getX();
		}
		return res;
	}

	public float getMaxX() {
		float res = 0;
		for (Sommet s : listeSommets) {
			if (s.getX() > res)
				res = s.getX();
		}
		return res;
	}

	public float getMinY() {
		float res = gcHeigth;
		for (Sommet s : listeSommets) {
			if (s.getY() < res)
				res = s.getY();
		}
		return res;
	}

	public float getMaxY() {
		float res = 0;
		for (Sommet s : listeSommets) {
			if (s.getY() > res)
				res = s.getY();
		}
		return res;
	}

	public void setListeSommets(List<Sommet> listeSommets) {
		this.listeSommets = listeSommets;
	}

	public void setListeFaces(List<Face> listeFaces) {
		this.listeFaces = listeFaces;
	}

	public void decalagePoints(int x, int y, int z) {
		for (Sommet s : listeSommets) {
			s.setX(s.getX() + x);
			s.setY(s.getY() + y);
			s.setZ(s.getZ() + z);
		}
	}

	public void clearCanvas() {
		this.gc.clearRect(0, 0, this.gcWidth, this.gcHeigth);
	}

	public void dessinFace(Face f) {
		gc.beginPath();
		double [] x = new double [] {listeSommets.get(f.getSommets().get(0)).getX(),listeSommets.get(f.getSommets().get(1)).getX(),listeSommets.get(f.getSommets().get(2)).getX()};
		double [] y = new double [] {listeSommets.get(f.getSommets().get(0)).getY(),listeSommets.get(f.getSommets().get(1)).getY(),listeSommets.get(f.getSommets().get(2)).getY()};
		gc.fillPolygon(x, y, 3);
		gc.moveTo(listeSommets.get(f.getSommets().get(0)).getX(), listeSommets.get(f.getSommets().get(0)).getY());
		gc.lineTo(listeSommets.get(f.getSommets().get(1)).getX(), listeSommets.get(f.getSommets().get(1)).getY());
		gc.lineTo(listeSommets.get(f.getSommets().get(2)).getX(), listeSommets.get(f.getSommets().get(2)).getY());
		gc.lineTo(listeSommets.get(f.getSommets().get(0)).getX(), listeSommets.get(f.getSommets().get(0)).getY());
		gc.stroke();
	}

	private void init() {
		float totalX = 0;
		float totalY = 0;
		if((int)totalX != gcHeigth && (int)totalY != gcWidth) 
			for (Sommet sommet : listeSommets) {
				totalX += sommet.getX();
				totalY += sommet.getY();
			}
		totalX /= listeSommets.size();
		totalY /= listeSommets.size();

		centreObjet.setX(totalX);
		centreObjet.setY(totalY);
	}

	private void centrer(Translation mouvement) {
		float[][] tmp = Matrice.toMatrice(listeSommets);
		init();
		if(mouvement != null) {
			mouvement.modifCentre(facteur);
		}
		int facteurX = (int) (centreObjet.getX()-facteur.getX() - Math.round(gcWidth/2));
		Mouvement translation;
		translation = new Translation(this, 'g', facteurX);
		translation.mouvement(tmp);

		int facteurY = (int) (centreObjet.getY()-facteur.getY() - Math.round(gcHeigth/2));
		translation = new Translation(this, 'h', facteurY);
		translation.mouvement(tmp);
		listeSommets = Matrice.toList(tmp);
	}


	public void dessinerModele(Translation mouvement) {
		clearCanvas();
		centrer(mouvement);
		for(int i = 0; i < this.listeFaces.size(); i++) {
			dessinFace(this.listeFaces.get(i));
		}
	}
}
