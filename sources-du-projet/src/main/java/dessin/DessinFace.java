package dessin;

import java.util.ArrayList;
import java.util.List;

import application.Vecteur;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Model;
import mouvement.Matrice;
import mouvement.Mouvement;
import mouvement.Translation;

public class DessinFace {
	private GraphicsContext gc;
	private Model model;
	private int gcHeigth;
	private int gcWidth;
	private Sommet centreObjet;
	private Sommet facteur;
	private float norme = (float) Math.sqrt(1*1+(-1)*(-1)+1*1);
	private Vecteur lumiere = new Vecteur(1/norme,(-1)/norme, 1/norme);

	public DessinFace(Canvas c, Model model) {
		this.gc = c.getGraphicsContext2D();
		this.model = model;
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

	public float getMinX() {
		float res = gcWidth;
		for (Sommet s : this.model.getListeSommets()) {
			if (s.getX() < res)
				res = s.getX();
		}
		return res;
	}

	public float getMaxX() {
		float res = 0;
		for (Sommet s : this.model.getListeSommets()) {
			if (s.getX() > res)
				res = s.getX();
		}
		return res;
	}

	public float getMinY() {
		float res = gcHeigth;
		for (Sommet s : this.model.getListeSommets()) {
			if (s.getY() < res)
				res = s.getY();
		}
		return res;
	}

	public float getMaxY() {
		float res = 0;
		for (Sommet s : this.model.getListeSommets()) {
			if (s.getY() > res)
				res = s.getY();
		}
		return res;
	}

	public void decalagePoints(int x, int y, int z) {
		for (Sommet s : this.model.getListeSommets()) {
			s.setX(s.getX() + x);
			s.setY(s.getY() + y);
			s.setZ(s.getZ() + z);
		}
	}

	public void clearCanvas() {
		this.gc.clearRect(0, 0, this.gcWidth, this.gcHeigth);
	}

	public void dessinFace(Face f, Color color) {
		getColorFace(f, color);
		gc.beginPath();
		double [] x = new double [] {this.model.getListeSommets().get(f.getSommets().get(0)).getX(),this.model.getListeSommets().get(f.getSommets().get(1)).getX(),this.model.getListeSommets().get(f.getSommets().get(2)).getX()};
		double [] y = new double [] {this.model.getListeSommets().get(f.getSommets().get(0)).getY(),this.model.getListeSommets().get(f.getSommets().get(1)).getY(),this.model.getListeSommets().get(f.getSommets().get(2)).getY()};
		gc.fillPolygon(x, y, 3);
		gc.moveTo(this.model.getListeSommets().get(f.getSommets().get(0)).getX(), this.model.getListeSommets().get(f.getSommets().get(0)).getY());
		gc.lineTo(this.model.getListeSommets().get(f.getSommets().get(1)).getX(), this.model.getListeSommets().get(f.getSommets().get(1)).getY());
		gc.lineTo(this.model.getListeSommets().get(f.getSommets().get(2)).getX(), this.model.getListeSommets().get(f.getSommets().get(2)).getY());
		gc.lineTo(this.model.getListeSommets().get(f.getSommets().get(0)).getX(), this.model.getListeSommets().get(f.getSommets().get(0)).getY());
		gc.stroke();
	}

	private void init() {
		float totalX = 0;
		float totalY = 0;
		if((int)totalX != gcHeigth && (int)totalY != gcWidth) 
			for (Sommet sommet : this.model.getListeSommets()) {
				totalX += sommet.getX();
				totalY += sommet.getY();
			}
		totalX /= this.model.getListeSommets().size();
		totalY /= this.model.getListeSommets().size();

		centreObjet.setX(totalX);
		centreObjet.setY(totalY);
	}

	private void centrer(Translation mouvement) {
		float[][] tmp = Matrice.toMatrice(this.model.getListeSommets());
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
		this.model.setListeSommets(Matrice.toList(tmp));
	}
	
	private float eclairage (Face f) {
		int numFace = this.model.getListeFaces().indexOf(f);
		return (float) (this.model.getListeVectNorm().get(numFace).getDirX()*lumiere.getDirX())+(this.model.getListeVectNorm().get(numFace).getDirY()*lumiere.getDirY())+(this.model.getListeVectNorm().get(numFace).getDirZ()*lumiere.getDirZ());
	}

	private void getColorFace(Face f, Color faceColor) {
		if (eclairage(f)<1 && eclairage(f)>0.8)
			gc.setFill(new Color(faceColor.getRed(), faceColor.getGreen(), faceColor.getBlue(),1.0).darker().brighter());
		if (eclairage(f)<0.8 && eclairage(f)>0.6)
			gc.setFill(new Color(faceColor.getRed(), faceColor.getGreen(), faceColor.getBlue(),1.0).darker().darker().brighter());
		if (eclairage(f)<0.6 && eclairage(f)>0.4)
			gc.setFill(new Color(faceColor.getRed(), faceColor.getGreen(), faceColor.getBlue(),1.0).darker().darker().darker().brighter());
		if (eclairage(f)<0.4 && eclairage(f)>0.2)
			gc.setFill(new Color(faceColor.getRed(), faceColor.getGreen(), faceColor.getBlue(),1.0).darker().darker().darker().darker().brighter());
		if (eclairage(f)<0.2 && eclairage(f)>0.0)
			gc.setFill(new Color(faceColor.getRed(), faceColor.getGreen(), faceColor.getBlue(),1.0).darker().darker().darker().darker().darker().brighter());
		if (eclairage(f)<0.0)
			gc.setFill(new Color(faceColor.getRed(), faceColor.getGreen(), faceColor.getBlue(),1.0).darker().darker().darker().darker().darker().darker().brighter());

	}

	private float findMinZOfFace(Face f) {
		List<Integer>sommetsDeF = f.getSommets();
		float zRes = this.model.getListeSommets().get(sommetsDeF.get(0)).getZ();
		Sommet s2 = this.model.getListeSommets().get(sommetsDeF.get(1));
		Sommet s3 = this.model.getListeSommets().get(sommetsDeF.get(2));
		if(s2.getZ() < zRes) {
			zRes = s2.getZ();
		}
		if(s3.getZ() < zRes) {
			zRes = s3.getZ();
		}
		return zRes;
	}
	
	private float zSumm(Face f) {
		return this.model.getListeSommets().get(f.getS1()).getZ() + this.model.getListeSommets().get(f.getS2()).getZ() + this.model.getListeSommets().get(f.getS3()).getZ();
	}
	
	private Face findFaceToDraw(List<Face>listFace) {
		int size = listFace.size();
		Face fRes = listFace.get(0);
		float minZ = findMinZOfFace(fRes);
		
		for(int i = 1; i < size; i++) {
			float tempMin = findMinZOfFace(listFace.get(i));
			if(tempMin < minZ) {
				minZ = tempMin;
				fRes = listFace.get(i);
			}else if(tempMin == minZ) {
				if(zSumm(listFace.get(i)) < zSumm(fRes)) {
					minZ = tempMin;
					fRes = listFace.get(i);
				}
			}
		}
		listFace.remove(fRes);
		return fRes;
	}

	public void dessinerModele(Translation mouvement, Color faceColor) {
		clearCanvas();
		centrer(mouvement);
		List<Face>listTempo = new ArrayList<Face>();
		listTempo.addAll(this.model.getListeFaces());
		while(listTempo.size() > 0) {
			dessinFace(findFaceToDraw(listTempo), faceColor);
		}
	}
}
