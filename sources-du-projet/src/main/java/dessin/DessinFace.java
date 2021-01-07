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
 * DessinFace est la classe de gestion de l'affichage dans la Sc�ne.
 * <p> Un DessinFace est caract�ris� par les informations suivantes :
 * <ul><li>Un Contexte Graphique unique attribu� d�finitivement.</li>
 * <li>Un Mod�le unique attribu� d�finitivement.</li>
 * <li>Une Hauteur de contexte graphique unique attribu�e d�finitivement.</li>
 * <li>Une Largeur de contexte graphique unique attribu�e d�finitivement.</li>
 * <li>Un Sommet correspondant au Centre du mod�le, suceptible d'�tre modifi�.</li>
 * <li>Un Sommet correspondant au Facteur du mod�le, unique attribu� d�finitivement.</li>
 * <li>Une Norme utilis�e pour le vecteur lumineux, unique attribu�e d�finitivement.</li>
 * <li>Un Vecteur Lumineux, unique attribu� d�finitivement.</li>
 * <li>Une Couleur pour les Faces, suceptible d'�tre chang�e.</li>
 * <p>Par d�faut fix�e � null</p>
 * <li>Une Couleur pour les Lignes, suceptible d'�tre chang�e.</li>
 * <p>Par d�faut fix�e � null</p>
 * <li>Un boolean activerEclairage, suceptible d'�tre modifi�.</li>
 * <p>Par d�faut fix� � false</p>
 * <li>Un boolean afficherLignes, suceptible d'�tre modifi�.</li>
 * <p>Par d�faut fix� � true</p>
 * <li>Un boolean afficherFaces, suceptible d'�tre modifi�.</li>
 * <p>Par d�faut fix� � true</p>
 * </ul></p>
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Math�o Gallego
 */
public class DessinFace {
	/**
	 * Le Contexte Graphique de la sc�ne.
	 */
	private GraphicsContext gc;
	/**
	 * Le mod�le.
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
	 * <p>Sommet par d�faut qui aura comme coordonn�es x=1, y=1, z=0</p>
	 * @see Sommet#Sommet()
	 */
	private Sommet facteur;
	/**
	 * La formule Math�matique correspondant � la Norme. 
	 */
	private float norme = (float) Math.sqrt(1*1+(-1)*(-1)+1*1);
	/**
	 * Le Vecteur Lumineux.
	 */
	private Vecteur lumiere = new Vecteur(1/norme,(-1)/norme, 1/norme);
	/**
	 * La Couleur pour les Faces.
	 */
	private Color colorFace;
	/**
	 * La Couleur pour les Lignes.
	 */
	private Color colorLigne;
	/**
	 * Le boolean servant � l'activation de l'�clairage.
	 */
	private boolean activerEclairage;
	/**
	 * Le boolean servant � l'affichage des Faces.
	 */
	private boolean afficherFaces;
	/**
	 * Le boolean servant � l'affichage des Lignes.
	 */
	private boolean afficherLignes;

	/**
	 * Constructeur DessinFace.
	 * <p>La construction d'un objet DessinFace va aussi cr�er deux Sommets : centreObjet et facteur.</p>
	 * @param c
	 * 				Le Canvas de la Sc�ne.
	 * @param model
	 * 				Le mod�le � dessiner.
	 * @see DessinFace#centreObjet
	 * @see DessinFace#facteur
	 * @see Sommet#Sommet()
	 * @see Canvas
	 * @see DessinFace#model
	 */
	public DessinFace(Canvas c, Model model) {
		gc = c.getGraphicsContext2D();
		this.model = model;
		gcHeigth = (int) c.getHeight();
		gcWidth = (int) c.getWidth();
		System.out.println("Nouveau Centre Cree");
		centreObjet = new Sommet();
		facteur = new Sommet();
		colorFace = null;
		colorLigne = null;
		activerEclairage = false;
		afficherFaces = true;
		afficherLignes = true;
	}
	/**
	 * Constructeur DessinFace.
     * <p>La construction d'un objet DessinFace() appelle son constructeur homonyme.</p>
     * @see Canvas#Canvas()
     * @see Model#Model()
	 */
	public DessinFace() {
		this(new Canvas(),new Model());
	}

	/**
	 * Retourne le Contexte Graphique du Dessinateur.
	 * @return le contexte graphique, sous forme de GraphicsContext.
	 */
	public GraphicsContext getGc() {
		return gc;
	}
	/**
	 * Met � jour la valeur du boolean servant � l'activation de l'�clairage.
	 * @param activerEclairage
	 * 				La nouvelle valeur du boolean servant � l'activation de l'�clairage.
	 */
	public void setActiverEclairage(boolean activerEclairage) {
		this.activerEclairage = activerEclairage;
	}
	/**
	 * Met � jour la valeur du boolean servant � l'affichage des Faces.
	 * @param afficherFaces
	 * 				La nouvelle valeur du boolean servant � l'affichage des Faces.
	 */
	public void setAfficherFaces(boolean afficherFaces) {
		this.afficherFaces = afficherFaces;
	}
	/**
	 * Met � jour la valeur du boolean servant � l'affichage des Lignes.
	 * @param afficherLignes
	 * 				La nouvelle valeur du boolean servant � l'affichage des Lignes.
	 */
	public void setAfficherLignes(boolean afficherLignes) {
		this.afficherLignes = afficherLignes;
	}
	/**
	 * Retourne la Hauteur du Contexte Graphique.
	 * @return la valeur de la Hauteur du Contexte Graphique, sous forme d'un entier.
	 */
	public int getGcHeigth() {
		return gcHeigth;
	}
	/**
	 * Retourne la Largeur du Contexte Graphique.
	 * @return la valeur de la Largeur du Contexte Graphique, sous forme d'un entier.
	 */
	public int getGcWidth() {
		return gcWidth;
	}
	/**
	 * Retourne la plus petite valeur de X parmi la liste des sommets du mod�le.
	 * @return la plus petite valeur de X parmi la liste des sommets du mod�le, sous forme d'un float.
	 */
	public float getMinX() {
		float res = gcWidth;
		for (Sommet s : model.getListeSommets()) {
			if (s.getX() < res)
				res = s.getX();
		}
		return res;
	}
	/**
	 * Retourne la plus grande valeur de X parmi la liste des sommets du mod�le.
	 * @return la plus grande valeur de X parmi la liste des sommets du mod�le, sous forme d'un float.
	 */
	public float getMaxX() {
		float res = 0;
		for (Sommet s : model.getListeSommets()) {
			if (s.getX() > res)
				res = s.getX();
		}
		return res;
	}
	/**
	 * Retourne la plus petite valeur de Y parmi la liste des sommets du mod�le.
	 * @return la plus petite valeur de Y parmi la liste des sommets du mod�le, sous forme d'un float.
	 */
	public float getMinY() {
		float res = gcHeigth;
		for (Sommet s : model.getListeSommets()) {
			if (s.getY() < res)
				res = s.getY();
		}
		return res;
	}
	/**
	 * Retourne la plus grande valeur de Y parmi la liste des sommets du mod�le.
	 * @return la plus grande valeur de Y parmi la liste des sommets du mod�le, sous forme d'un float.
	 */
	public float getMaxY() {
		float res = 0;
		for (Sommet s : model.getListeSommets()) {
			if (s.getY() > res)
				res = s.getY();
		}
		return res;
	}

	/**
	 * Centre le mod�le et l'adapte au maximum � la taille du Contexte Graphique.
	 */
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

	/**
	 * Efface tout ce qui �tait affich� dans le Contexte Graphique.
	 */
	public void clearCanvas() {
		gc.clearRect(0, 0, gcWidth, gcHeigth);
	}
	
	/**
	 * Change la couleur des lignes avec celle pass�e en param�tre.
	 * @param c
	 * 			La nouvelle couleur des lignes.
	 * @see Color
	 */
	private void changeLineColor(Color c) {
		if(!this.afficherLignes) {
			gc.setStroke(c);
		}else {
			gc.setStroke(this.colorLigne);
		}
	}
	
	/**
	 * Affiche l'�clairage sur une Face donn�e en param�tre.
	 * @param f
	 * 			La Face sur laquelle l'�clairage sera appliqu�.
	 * @see Face
	 */
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

	/**
	 * Dessine la Face pass�e en param�tre.
	 * @param f
	 * 			La Face qu'il faut dessiner.
	 * @see Face
	 */
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

	/**
	 * Initialise les coordonn�es du Sommet centreObjet.
	 * @see DessinFace#centreObjet
	 */
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

	/**
	 * Retourne le Sommet correspondant au centre du mod�le.
	 * @return Une instance de Sommet, qui correspond au centre du mod�le.
	 * @see Sommet
	 */
	public Sommet getCentreObjet() {
		return centreObjet;
	}
	/**
	 * Remet � sa valeur d'origine le Centrage.
	 * <p>Cr�� une nouvelle instance pour le Sommet facteur</p>
	 * @see Sommet#Sommet()
	 */
	public void resetCentrage() {
		facteur = new Sommet();
	}
	
	/**
	 * Centre le mod�le.
	 */
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
	
	/**
	 * Centre le mod�le par rapport � la translation effectu�e.
	 * <p>Va donc modifier les coordonn�es du centre avant d'effectuer le centrage du mod�le.</p>
	 * @param mouvement
	 * 				La translation qui a �t� effectu�e sur le mod�le.
	 * @see Translation#modifCentre(Sommet)
	 * @see DessinFace#centrer()
	 */
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
