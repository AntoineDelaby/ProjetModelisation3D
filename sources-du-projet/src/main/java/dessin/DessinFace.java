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
 * <p> Un DessinFace est caract�ris� par les informations suivantes :</p>
 * <ul><li>Un Contexte Graphique unique attribu� d�finitivement.</li>
 * <li>Un Mod�le unique attribu� d�finitivement.</li>
 * <li>Une Hauteur de contexte graphique unique attribu�e d�finitivement.</li>
 * <li>Une Largeur de contexte graphique unique attribu�e d�finitivement.</li>
 * <li>Un Sommet correspondant au Centre du mod�le, suceptible d'�tre modifi�.</li>
 * <li>Un Sommet correspondant au Facteur du mod�le, unique attribu� d�finitivement.</li>
 * <li>Une Norme utilis�e pour le vecteur lumineux, unique attribu�e d�finitivement.</li>
 * <li>Un Vecteur Lumineux, unique attribu� d�finitivement.</li>
 * <li>Une Couleur pour les Faces, suceptible d'�tre chang�e.
 * <p>Par d�faut fix�e � null</p></li>
 * <li>Une Couleur pour les Lignes, suceptible d'�tre chang�e.
 * <p>Par d�faut fix�e � null</p></li>
 * <li>Un boolean activerEclairage, suceptible d'�tre modifi�.
 * <p>Par d�faut fix� � false</p></li>
 * <li>Un boolean afficherLignes, suceptible d'�tre modifi�.
 * <p>Par d�faut fix� � true</p></li>
 * <li>Un boolean afficherFaces, suceptible d'�tre modifi�.
 * <p>Par d�faut fix� � true</p></li></ul>
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Math�o Gallego
 */
public class DessinFace {
	/**
	 * Le Contexte Graphique de la sc�ne.
	 * @see DessinFace#getGc()
	 * @see DessinFace#setGc(GraphicsContext)
	 */
	private GraphicsContext gc;
	/**
	 * Le Mod�le.
	 */
	private Model model;
	/**
	 * La Hauteur du contexte graphique.
	 * @see DessinFace#getGcHeigth()
	 */
	private int gcHeigth;
	/**
	 * La Largeur du contexte graphique.
	 * @see DessinFace#getGcWidth()
	 */
	private int gcWidth;
	/**
	 * Le Sommet correspondant au Centre de l'objet
	 * @see DessinFace#getCentreObjet()
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
	 * @see DessinFace#getColorFace()
	 * @see DessinFace#setColorFace(Color)
	 */
	private Color colorFace;
	/**
	 * La Couleur pour les Lignes.
	 * @see DessinFace#getColorLigne()
	 * @see DessinFace#setColorLigne(Color)
	 */
	private Color colorLigne;
	/**
	 * Le boolean servant � l'activation de l'�clairage.
	 * @see DessinFace#isActiverEclairage()
	 * @see DessinFace#setActiverEclairage(boolean)
	 */
	private boolean activerEclairage;
	/**
	 * Le boolean servant � l'affichage des Faces.
	 * @see DessinFace#setAfficherFaces(boolean)
	 */
	private boolean afficherFaces;
	/**
	 * Le boolean servant � l'affichage des Lignes.
	 * @see DessinFace#setAfficherLignes(boolean)
	 */
	private boolean afficherLignes;

	/**
	 * Constructeur DessinFace.
	 * <p>La construction d'un objet DessinFace va aussi cr�er deux Sommets : centreObjet et facteur.</p>
	 * @param c
	 * 				Le Canvas de la Sc�ne.
	 * @see DessinFace#centreObjet
	 * @see DessinFace#facteur
	 * @see Sommet#Sommet()
	 * @see Canvas
	 * @see DessinFace#model
	 */
	public DessinFace(Canvas c) {
		gc = c.getGraphicsContext2D();
		model = Model.getInstance();
		gcHeigth = (int) c.getHeight();
		gcWidth = (int) c.getWidth();
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
     * @see DessinFace#DessinFace(Canvas)
     * @see Canvas#Canvas()
	 */
	public DessinFace() {
		this(new Canvas());
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
		if(!afficherLignes) {
			gc.setStroke(c);
		}else {
			gc.setStroke(colorLigne);
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
		if(activerEclairage) {
			c = getColorFace(f, colorFace);
		} else {
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
		if(!afficherFaces) {
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

	/**
	 * Retourne le niveau d'�clairage de la Face pass�e en param�tre.
	 * @param f
	 * 			La Face dont on veut r�cup�rer le niveau d'�clairage.
	 * @return Le niveau d'�clairage de la Face pass�e en param�tren sous forme d'un float compris entre -1 et 1.
	 */
	private float eclairage(Face f) {
		int numFace = model.getListeFaces().indexOf(f);
		return (float) (model.getListeVectNorm().get(numFace).getDirX()*lumiere.getDirX())+(model.getListeVectNorm().get(numFace).getDirY()*lumiere.getDirY())+(model.getListeVectNorm().get(numFace).getDirZ()*lumiere.getDirZ());	
	}

	/**
	 * M�thode qui va permettre l'affichage du mod�le.
	 * <ul><li>Clear le canvas.</li>
	 * <li>Centre le mod�le.</li>
	 * <li>Effectue l'algorithme du peintre.</li>
	 * <li>Affiche les faces dans le bon ordre.</li></ul>
	 * @param mouvement
	 * 				La Translation effectu�e sur le mod�le.
	 */
	public void dessinerModele(Translation mouvement) {
		clearCanvas();
		centrer(mouvement);
		List<Face>listTempo = new ArrayList<Face>();
		listTempo.addAll(model.getListeFaces());
		Collections.sort(listTempo, new Comparator<Face>() {			
			@Override
			public int compare(Face face1, Face face2) {
				float minO1 = findMinZOfFace(face1);
				float minO2 = findMinZOfFace(face2);

				if (minO1 < minO2)
					return -1;
				else if (minO1>minO2)
					return 1;
				return 0;
			}
		});
		for (Face f : listTempo)
			dessinerFace(f);
	}
	public void dessinerModele() {
		dessinerFace(null);
	}
	/**
	 * Retourne la plus petite valeur de Z parmi les Sommets de la Face pass�e en param�tre.
	 * @param f
	 * 			La Face dont on veut savoir la plus petite valeur de Z parmi ses Sommets.
	 * @return la plus petite valeur de Z parmi les Sommets de la Face pass�e en param�tre, sous forme d'un float.
	 * @see Face
	 */
	private float findMinZOfFace(Face f) {
		List<Integer>sommetsDeF = f.getSommets();
		float zRes = model.getListeSommets().get(sommetsDeF.get(0)).getZ();
		float zS2 = model.getListeSommets().get(sommetsDeF.get(1)).getZ();
		float zS3 = model.getListeSommets().get(sommetsDeF.get(2)).getZ();
		if(zS2 < zRes) {
			zRes = zS2;
		}
		if(zS3 < zRes) {
			zRes = zS3;
		}
		return zRes;
	}
	
	/**
	 * Retourne la Couleur de la face pass�e en param�tre.
	 * @param f
	 * 			La Face dont on veut savoir la couleur.
	 * @param faceColor
	 * 			La couleur de la Face.
	 * @return La Couleur de la face pass�e en param�tre, sous forme d'une Color.
	 */
	private Color getColorFace(Face f, Color faceColor) {
		Color colorRes = null;
		if (eclairage(f)<=1 && eclairage(f)>0.8) {
			colorRes = new Color(faceColor.getRed(), faceColor.getGreen(), faceColor.getBlue(),1.0);
			gc.setFill(colorRes);
		} else if (eclairage(f)<=0.8 && eclairage(f)>0.6) {
			colorRes = new Color(faceColor.getRed(), faceColor.getGreen(), faceColor.getBlue(),1.0).darker();
			gc.setFill(colorRes);
		} else if (eclairage(f)<=0.6 && eclairage(f)>0.4) {
			colorRes = new Color(faceColor.getRed(), faceColor.getGreen(), faceColor.getBlue(),1.0).darker().darker();
			gc.setFill(colorRes);
		} else if (eclairage(f)<=0.4 && eclairage(f)>0.2) {
			colorRes = new Color(faceColor.getRed(), faceColor.getGreen(), faceColor.getBlue(),1.0).darker().darker().darker();
			gc.setFill(colorRes);
		} else if (eclairage(f)<=0.2 && eclairage(f)>0.0) { 
			colorRes = new Color(faceColor.getRed(), faceColor.getGreen(), faceColor.getBlue(),1.0).darker().darker().darker().darker();
			gc.setFill(colorRes);
		} else if (eclairage(f)<=0.0 && eclairage(f)>-0.2) {
			colorRes = new Color(faceColor.getRed(), faceColor.getGreen(), faceColor.getBlue(),1.0).darker().darker().darker().darker().darker();
			gc.setFill(colorRes);
		} else if (eclairage(f)<=-0.2 && eclairage(f)>-0.4) {
			colorRes = new Color(faceColor.getRed(), faceColor.getGreen(), faceColor.getBlue(),1.0).darker().darker().darker().darker().darker().darker();
			gc.setFill(colorRes);
		} else if (eclairage(f)<=-0.4 && eclairage(f)>-0.6) {
			colorRes = new Color(faceColor.getRed(), faceColor.getGreen(), faceColor.getBlue(),1.0).darker().darker().darker().darker().darker().darker().darker();
			gc.setFill(colorRes);
		} else if (eclairage(f)<=-0.6 && eclairage(f)>-0.8) {
			colorRes = new Color(faceColor.getRed(), faceColor.getGreen(), faceColor.getBlue(),1.0).darker().darker().darker().darker().darker().darker().darker().darker();
			gc.setFill(colorRes);
		} else {
			colorRes = new Color(faceColor.getRed(), faceColor.getGreen(), faceColor.getBlue(),1.0).darker().darker().darker().darker().darker().darker().darker().darker().darker();
			gc.setFill(colorRes);
		}
		
		return colorRes;
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
	 * Retourne la le Contexte Graphique.
	 * @return le Contexte Graphique, sous forme d'un GraphicContext.
	 * @see GraphicsContext
	 */
	public GraphicsContext getGc() {
		return gc;
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
	 * Retourne le Sommet correspondant au centre du mod�le.
	 * @return Une instance de Sommet, qui correspond au centre du mod�le.
	 * @see Sommet
	 */
	public Sommet getCentreObjet() {
		return centreObjet;
	}
	/**
	 * Retourne la couleur des faces du Dessinateur.
	 * @return La couleur des faces du Dessinateur, sous forme d'une Color.
	 * @see Color
	 */
	public Color getColorFace() {
		return colorFace;
	}
	/**
	 * Retourne la couleur des lignes du Dessinateur.
	 * @return La couleur des lignes du Dessinateur, sous forme d'une Color.
	 * @see Color
	 */
	public Color getColorLigne() {
		return colorLigne;
	}
	/**
	 * Retourne la valeur du boolean servant � l'activation de l'�clairage.
	 * @return La valeur du boolean servant � l'activation de l'�clairage, sous forme d'un boolean.
	 */
	public boolean isActiverEclairage() {
		return activerEclairage;
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
	 * Met � jour le Contexte Graphique.
	 * @param gc
	 * 			Le nouveau Contexte Graphique.
	 * @see GraphicsContext
	 */
	public void setGc(GraphicsContext gc) {
		this.gc = gc;
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
	 * Met � jour la liste des Sommets du Mod�le.
	 * @param listeSommets
	 * 				La nouvelle liste des Sommets du Mod�le.
	 */
	public void setListeSommets(List<Sommet> listeSommets) {
		model.setListeSommets(listeSommets);
	}
	/**
	 * Met � jour la couleur du Dessinateur.
	 * @param color
	 * 			La nouvelle couleur du Dessinateur.
	 */
	public void setColorFace(Color color) {
		colorFace = color;
	}
	/**
	 * Met � jour la couleur de Ligne du Dessinateur.
	 * @param colorLigne
	 * 			La nouvelle couleur de ligne du Dessinateur.
	 */
	public void setColorLigne(Color colorLigne) {
		this.colorLigne = colorLigne;
	}
}
