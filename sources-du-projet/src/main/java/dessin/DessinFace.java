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
	private boolean activerEclairage;

	public DessinFace(Canvas c, Model model) {
		gc = c.getGraphicsContext2D();
		this.model = model;
		gcHeigth = (int) c.getHeight();
		gcWidth = (int) c.getWidth();
		centreObjet = new Sommet();
		facteur = new Sommet();
		this.activerEclairage = false;
	}

	public GraphicsContext getGc() {
		return gc;
	}

	public void setActiverEclairage(boolean activerEclairage) {
		this.activerEclairage = activerEclairage;
	}

	public int getGcHeigth() {
		return gcHeigth;
	}

	public int getGcWidth() {
		return gcWidth;
	}

	public float getMinX() {
		float res = gcWidth;
		for (Sommet s : model.getListeSommets()) {
			if (s.getX() < res)
				res = s.getX();
		}
		return res;
	}

	public float getMaxX() {
		float res = 0;
		for (Sommet s : model.getListeSommets()) {
			if (s.getX() > res)
				res = s.getX();
		}
		return res;
	}

	public float getMinY() {
		float res = gcHeigth;
		for (Sommet s : model.getListeSommets()) {
			if (s.getY() < res)
				res = s.getY();
		}
		return res;
	}

	public float getMaxY() {
		float res = 0;
		for (Sommet s : model.getListeSommets()) {
			if (s.getY() > res)
				res = s.getY();
		}
		return res;
	}

	public void decalagePoints(int x, int y, int z) {
		for (Sommet s : model.getListeSommets()) {
			s.setX(s.getX() + x);
			s.setY(s.getY() + y);
			s.setZ(s.getZ() + z);
		}
	}

	public void clearCanvas() {
		gc.clearRect(0, 0, gcWidth, gcHeigth);
	}

	public void dessinFace(Face f, Color color) {
		if(this.activerEclairage) {
			getColorFace(f, color);
		}else {
			gc.setFill(color);
		}
		gc.beginPath();
		double [] x = new double [] {model.getListeSommets().get(f.getSommets().get(0)).getX(),model.getListeSommets().get(f.getSommets().get(1)).getX(),model.getListeSommets().get(f.getSommets().get(2)).getX()};
		double [] y = new double [] {model.getListeSommets().get(f.getSommets().get(0)).getY(),model.getListeSommets().get(f.getSommets().get(1)).getY(),model.getListeSommets().get(f.getSommets().get(2)).getY()};
		gc.fillPolygon(x, y, 3);
		gc.moveTo(model.getListeSommets().get(f.getSommets().get(0)).getX(), model.getListeSommets().get(f.getSommets().get(0)).getY());
		gc.lineTo(model.getListeSommets().get(f.getSommets().get(1)).getX(), model.getListeSommets().get(f.getSommets().get(1)).getY());
		gc.lineTo(model.getListeSommets().get(f.getSommets().get(2)).getX(), model.getListeSommets().get(f.getSommets().get(2)).getY());
		gc.lineTo(model.getListeSommets().get(f.getSommets().get(0)).getX(), model.getListeSommets().get(f.getSommets().get(0)).getY());
		gc.stroke();
	}

	private void init() {
		float totalX = 0;
		float totalY = 0;
		if((int)totalX != gcHeigth && (int)totalY != gcWidth) 
			for (Sommet sommet : model.getListeSommets()) {
				totalX += sommet.getX();
				totalY += sommet.getY();
			}
		totalX /= model.getListeSommets().size();
		totalY /= model.getListeSommets().size();

		centreObjet.setX(totalX);
		centreObjet.setY(totalY);
	}

	private void centrer(Translation mouvement) {
		float[][] tmp = Matrice.toMatrice(model.getListeSommets());
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
		model.setListeSommets(Matrice.toList(tmp));
	}
	
	private float eclairage (Face f) {
		int numFace = model.getListeFaces().indexOf(f);
		return (float) (model.getListeVectNorm().get(numFace).getDirX()*lumiere.getDirX())+(model.getListeVectNorm().get(numFace).getDirY()*lumiere.getDirY())+(model.getListeVectNorm().get(numFace).getDirZ()*lumiere.getDirZ());
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
		float zRes = model.getListeSommets().get(sommetsDeF.get(0)).getZ();
		Sommet s2 = model.getListeSommets().get(sommetsDeF.get(1));
		Sommet s3 = model.getListeSommets().get(sommetsDeF.get(2));
		if(s2.getZ() < zRes) {
			zRes = s2.getZ();
		}
		if(s3.getZ() < zRes) {
			zRes = s3.getZ();
		}
		return zRes;
	}
	
	private float zSumm(Face f) {
		return model.getListeSommets().get(f.getS1()).getZ() + model.getListeSommets().get(f.getS2()).getZ() + model.getListeSommets().get(f.getS3()).getZ();
	}
	
	private Face findFaceToDraw(List<Face>listFace) {
		Face fRes = listFace.get(0);
		float minZ = findMinZOfFace(fRes);
		
		for(int i = 1; i < listFace.size(); i++) {
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
		listTempo.addAll(model.getListeFaces());
		while(listTempo.size() > 0) {
			dessinFace(findFaceToDraw(listTempo), faceColor);
		}
	}
}
