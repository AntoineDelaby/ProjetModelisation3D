package dessin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Model;
import mouvement.Matrice;
import mouvement.Mouvement;
import mouvement.Translation;
import mouvement.Vecteur;
import mouvement.Zoom;

/**
 * DessinFace est la classe de gestion de l'affichage dans la Scène.
 * <p> Un DessinFace est caractérisé par les informations suivantes :
 * <ul><li>Un Contexte Graphique unique attribué définitivement.</li>
 * <li>Un Modèle unique attribué définitivement.</li>
 * <li>Une Hauteur de contexte graphique unique attribuée définitivement.</li>
 * <li>Une Largeur de contexte graphique unique attribuée définitivement.</li>
 * <li>Un Sommet correspondant au Centre du modèle, suceptible d'être modifié.</li>
 * <li>Un Sommet correspondant au Facteur du modèle, unique attribué définitivement.</li>
 * <li>Une Norme utilisée pour le vecteur lumineux, unique attribuée définitivement.</li>
 * <li>Un Vecteur Lumineux, unique attribué définitivement.</li>
 * <li>Une Couleur pour les Faces, suceptible d'être changée.</li>
 * <p>Par défaut fixée à null</p>
 * <li>Une Couleur pour les Lignes, suceptible d'être changée.</li>
 * <p>Par défaut fixée à null</p>
 * <li>Un boolean activerEclairage, suceptible d'être modifié.</li>
 * <p>Par défaut fixé à false</p>
 * <li>Un boolean afficherLignes, suceptible d'être modifié.</li>
 * <p>Par défaut fixé à true</p>
 * <li>Un boolean afficherFaces, suceptible d'être modifié.</li>
 * <p>Par défaut fixé à true</p>
 * </ul></p>
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Mathéo Gallego
 */
public class DessinFace {
	/**
	 * Le Contexte Graphique de la scène.
	 */
	private GraphicsContext gc;
	/**
	 * Le modèle.
	 */
	private Model model;
	/**
	 * La Hauteur du contexte graphique.
	 */
	private int gcHeigth;
	/**
	 * La Largeur du contexte graphique.
	 */
	private int gcWidth;
	/**
	 * Le Sommet correspondant au Centre de l'objet
	 */
	private Sommet centreObjet;
	/**
	 * Le Sommet correspondant au facteur.
	 * <p>Sommet par défaut qui aura comme coordonnées x=1, y=1, z=0</p>
	 * @see Sommet#Sommet()
	 */
	private Sommet facteur;
	
	private float norme = (float) Math.sqrt(1*1+(-1)*(-1)+1*1);
	private Vecteur lumiere = new Vecteur(1/norme,(-1)/norme, 1/norme);
	private Color colorFace;
	private Color colorLigne;
	private boolean activerEclairage;
	private boolean afficherLignes;
	private boolean afficherFaces;

	public DessinFace(Canvas c, Model model) {
		gc = c.getGraphicsContext2D();
		this.model = model;
		gcHeigth = (int) c.getHeight();
		gcWidth = (int) c.getWidth();
		centreObjet = new Sommet();
		facteur = new Sommet();
		this.colorFace = null;
		this.colorLigne = null;
		this.activerEclairage = false;
		this.afficherFaces = true;
		this.afficherLignes = true;
	}

	public DessinFace() {
		this(new Canvas(),new Model());
	}

	public GraphicsContext getGc() {
		return gc;
	}

	public void setActiverEclairage(boolean activerEclairage) {
		this.activerEclairage = activerEclairage;
	}

	public void setAfficherLignes(boolean afficherLignes) {
		this.afficherLignes = afficherLignes;
	}

	public void setAfficherFaces(boolean afficherFaces) {
		this.afficherFaces = afficherFaces;
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
	
	public void centrageEtendu() {
        float[][] tmp = Matrice.toMatrice(model.getListeSommets());
        float facteurZoom = 0;
        Mouvement mouvement;
        if((gcWidth - (getMaxX()-getMinX())) > (gcHeigth - (getMaxY()-getMinY()))){
            facteurZoom = (float) (gcWidth / (getMaxX()-getMinX())*0.92);
            mouvement = new Zoom(this,facteurZoom);
            mouvement.mouvement(tmp);
            model.setListeSommets(Matrice.toList(tmp));
        }
        if(getMaxY()>gcHeigth || getMinY() < 0){
            tmp = Matrice.toMatrice(model.getListeSommets());
            facteurZoom = (float) (gcHeigth / (getMaxY()-getMinY())*0.80);
            if(facteurZoom <1) {
                mouvement = new Zoom(this,facteurZoom);
                mouvement.mouvement(tmp);
            }
        }
        model.setListeSommets(Matrice.toList(tmp));
    }

	public void clearCanvas() {
		gc.clearRect(0, 0, gcWidth, gcHeigth);
	}
	
	private void changeLineColor(Color c) {
		if(!this.afficherLignes) {
			gc.setStroke(c);
		}else {
			gc.setStroke(this.colorLigne);
		}
	}
	
	private void setEclairage(Face f) {
		Color c = null;
		if(this.activerEclairage) {
			c = getColorFace(f, colorFace);
		}else {
			gc.setFill(colorFace);
			c = colorFace;
		}
		changeLineColor(c);
	}

	public void dessinerFace(Face f) {
		if(!this.afficherFaces) {
			gc.setFill(Color.TRANSPARENT);
			changeLineColor(Color.TRANSPARENT);
		}else {
			setEclairage(f);
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


	public void init() {
		float totalX = 0;
		float totalY = 0;
		for (Sommet sommet : model.getListeSommets()) {
			totalX += sommet.getX();
			totalY += sommet.getY();
		}
		totalX /= model.getListeSommets().size();
		totalY /= model.getListeSommets().size();

		centreObjet.setX(totalX);
		centreObjet.setY(totalY);
	}

	public Sommet getCentreObjet() {
		return centreObjet;
	}

	public void centrer() {
		float[][] tmp = Matrice.toMatrice(model.getListeSommets());
		init();
		int facteurX = (int) (centreObjet.getX()-facteur.getX() - Math.round(gcWidth/2));
		Mouvement translation;
		translation = new Translation(this, 'g', facteurX);
		translation.mouvement(tmp);

		int facteurY = (int) (centreObjet.getY()-facteur.getY() - Math.round(gcHeigth/2));
		translation = new Translation(this, 'h', facteurY);
		translation.mouvement(tmp);
		model.setListeSommets(Matrice.toList(tmp));
	}
	
	public void centrer(Translation mouvement) {
		if(mouvement != null) {
			mouvement.modifCentre(facteur);
		}
		centrer();
	}

	private float eclairage (Face f) {
		int numFace = model.getListeFaces().indexOf(f);
		return (float) (model.getListeVectNorm().get(numFace).getDirX()*lumiere.getDirX())+(model.getListeVectNorm().get(numFace).getDirY()*lumiere.getDirY())+(model.getListeVectNorm().get(numFace).getDirZ()*lumiere.getDirZ());
	}

	private Color getColorFace(Face f, Color faceColor) {
		Color colorRes = null;
		if (eclairage(f)<1 && eclairage(f)>0.8) {
			colorRes = new Color(faceColor.getRed(), faceColor.getGreen(), faceColor.getBlue(),1.0).darker().brighter();
			gc.setFill(colorRes);
		}
		if (eclairage(f)<0.8 && eclairage(f)>0.6) {
			colorRes = new Color(faceColor.getRed(), faceColor.getGreen(), faceColor.getBlue(),1.0).darker().darker().brighter();
			gc.setFill(colorRes);
		}
		if (eclairage(f)<0.6 && eclairage(f)>0.4) {
			colorRes = new Color(faceColor.getRed(), faceColor.getGreen(), faceColor.getBlue(),1.0).darker().darker().darker().brighter();
			gc.setFill(colorRes);
		}
		if (eclairage(f)<0.4 && eclairage(f)>0.2) {
			colorRes = new Color(faceColor.getRed(), faceColor.getGreen(), faceColor.getBlue(),1.0).darker().darker().darker().darker().brighter();
			gc.setFill(colorRes);
		}
		if (eclairage(f)<0.2 && eclairage(f)>0.0) {
			colorRes = new Color(faceColor.getRed(), faceColor.getGreen(), faceColor.getBlue(),1.0).darker().darker().darker().darker().darker().brighter();
			gc.setFill(colorRes);
		}
		if (eclairage(f)<0.0) {
			colorRes = new Color(faceColor.getRed(), faceColor.getGreen(), faceColor.getBlue(),1.0).darker().darker().darker().darker().darker().darker().brighter();
			gc.setFill(colorRes);
		}
		return colorRes;
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

	public Color getColor() {
		return colorFace;
	}

	public void setColorFace(Color color) {
		this.colorFace = color;
	}

	public void setColorLigne(Color colorLigne) {
		this.colorLigne = colorLigne;
	}

	public void dessinerModele(Translation mouvement) {
		clearCanvas();
		centrer(mouvement);
		List<Face>listTempo = new ArrayList<Face>();
		listTempo.addAll(model.getListeFaces());
		Collections.sort(listTempo, new Comparator<Face>() {			
			@Override
			public int compare(Face o1, Face o2) {
				float minO1 = findMinZOfFace(o1);
				float minO2 = findMinZOfFace(o2);

				if (minO1<minO2)
					return -1;
				else if (minO1>minO2)
					return 1;
				return 0;
			}
		});
		for (Face f : listTempo)
			dessinerFace(f);
	}

	public boolean isActiverEclairage() {
		return activerEclairage;
	}

	public void setListeSommets(List<Sommet> listeSommets) {
		model.setListeSommets(listeSommets);
	}
}
