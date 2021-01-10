package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import controller.Controller;
import dessin.Face;
import dessin.Sommet;
import mouvement.Matrice;
import mouvement.Mouvement;
import mouvement.Vecteur;

/**
 * Model est la classe de gestion du Mod�le.
 * <p> Un Model est caract�ris� par les informations suivantes :
 * <ul><li>Une liste de Sommets, suceptible d'�tre chang�e</li>
 * <li>Une liste de Faces, suceptible d'�tre chang�e</li>
 * <li>Une liste de Vecteurs Normaux</li>
 * <li>Un chemin d'acc�s aux ressources, unique attribu� d�finitivement.</li>
 * <li>Un Fichier, suceptible d'�tre chang�</li>
 * <li>Un mouvement</li>
 * <li>Une liste de Controlleurs</li>
 * <li>Un Singleton du mod�le, unique attribu� d�finitivement.</li></ul></p>
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Math�o Gallego
 */
public class Model {
	/**
	 * La liste des Sommets du Mod�le.
	 * @see Model#setListeSommets(List)
	 */
	private List<Sommet> listeSommets;
	/**
	 * La liste des Faces du Mod�le.
	 */
	private List<Face> listeFaces;
	/**
	 * La liste des Vecteurs Normaux du Mod�le.
	 */
	private List<Vecteur> listeVectNorm;
	/**
	 * La liste des Controlleurs du Mod�le.
	 * @see Model#getListControlleurs()
	 */
	private List<Controller>listControlleurs;
	/**
	 * Le Chemin d'acc�s aux ressources (fichiers ply).
	 */
	private final String pathRessources = "./ressources/";
	/**
	 * Le fichier ply du Mod�le.
	 * @see Model#setFile(String)
	 */
	private File file;
	/**
	 * Le mouvement associ� au Mod�le.
	 * @see Model#setMouvement(Mouvement)
	 */
	private Mouvement mouvement;
	/**
	 * Le Singleton du Mod�le.
	 * @see Model#getInstance()
	 */
	private static final Model instance = new Model();


	/**
	 * Constructeur Model.
	 * <p><ul><li>Cr�� une nouvelle liste de Controlleurs, vide.</li>
	 * <li>Cr�� une nouvelle liste de Sommets, vide.</li>
	 * <li>Cr�� une nouvelle liste de Faces, vide.</li>
	 * <li>Cr�� une nouvelle liste de Vecteurs Normaux, vide.</li>
	 * <li>Affecte "null" au fichier du Mod�le.</li></ul></p>
	 */
	private Model() {
		listControlleurs = new ArrayList<>();
		listeSommets = new ArrayList<>();
		listeFaces = new ArrayList<>();
		listeVectNorm = new ArrayList<>();
		file = null;
	}

	/**
	 * Retourne la Liste des Fichiers PLY pr�sents dans le chemin d'acc�s des ressources.
	 * @return La Liste des Fichiers PLY pr�sents dans le chemin d'acc�s des ressources, sous forme d'une Liste de cha�nes de Caract�res.
	 */
	public List<String> filterList() {
		List<String> filteredFileList = new ArrayList<String>();
		String[] filelist = new File(pathRessources).list();
		if (filelist != null) {
			for (String fichier : filelist) 
				filteredFileList.add(fichier);
		}
		return filteredFileList;
	}

	/**
	 * Effectue le mouvement sur le Mod�le.
	 */
	public void effectuerMouvement() {
		try {
			float[][]model = Matrice.toMatrice(listeSommets);
			listeVectNorm.clear();		
			mouvement.mouvement(model);	
			setListeSommets(Matrice.toList(model));
			initNorm();
			notifyForUpdate();
		}catch(Exception e) {
		}
	}
	
	/**
	 * Met � jour le mod�le.
	 * <p>M�thode appel�e lorsqu'aucun mouvement sur le mod�le n'est effectu�.
	 * Par exemple lorsque l'on change la couleur des Faces o� que l'on active l'�clairage, etc..</p>
	 */
	public void updateForNoMovment() {
		for(Controller c : this.getListControlleurs()) {
			c.changeLineAndFacesColor();
			c.getDf().dessinerModele(null);
		}
	}

	/**
	 * Initialise la liste des Vecteurs Normaux du Mod�le.
	 */
	public void initNorm () {
		float tab1 [] = new float [3];
		float tab2 [] = new float [3];
		float tabF [] = new float [3];
		float norme ;
		for (int i = 0 ; i < listeFaces.size() ; i++) {		
			Sommet s1=listeSommets.get(listeFaces.get(i).getS1());
			Sommet s2=listeSommets.get(listeFaces.get(i).getS2());
			Sommet s3=listeSommets.get(listeFaces.get(i).getS3());
			tab1 [0] = s2.getX() - s1.getX(); 
			tab1 [1] = s2.getY() - s1.getY(); 
			tab1 [2] = s2.getZ() - s1.getZ();
			tab2 [0] = s3.getX() - s1.getX(); 
			tab2 [1] = s3.getY() - s1.getY(); 
			tab2 [2] = s3.getZ() - s1.getZ();
			tabF[0]= tab1[1]*tab2[2]-tab1[2]*tab2[1];
			tabF[1]= tab1[2]*tab2[0]-tab1[0]*tab2[2];
			tabF[2]= tab1[0]*tab2[1]-tab1[1]*tab2[0];
			norme = (float) Math.sqrt(tabF[0]*tabF[0]+tabF[1]*tabF[1]+tabF[2]*tabF[2]);
			listeVectNorm.add(new Vecteur(tabF[0]/norme, tabF[1]/norme, tabF[2]/norme));
		}
	}
	
	/**
	 * Va update le Mod�le pour tous les Controller de la Liste des Controlleurs.
	 */
	public void notifyForUpdate() {
		for(Controller c : this.listControlleurs) {
			c.update();
		}
	}


	/**
	 * Retourne la Liste des Sommets du Mod�le.
	 * @return La Liste des Sommets du Mod�le, sous forme d'une Liste de Sommets.
	 */
	public List<Sommet> getListeSommets() {
		return listeSommets;
	}
	/**
	 * Retourne la Liste des Faces du Mod�le.
	 * @return La Liste des Faces du Mod�le, sous forme d'une Liste de Faces.
	 */
	public List<Face> getListeFaces() {
		return listeFaces;
	}
	/**
	 * Retourne la Liste des Vecteurs Normaux du Mod�le.
	 * @return La Liste des Vecteurs Normaux du Mod�le, sous forme d'une Liste de Vecteurs.
	 */
	public List<Vecteur> getListeVectNorm() {
		return listeVectNorm;
	}
	/**
	 * Retourne la Liste des Controlleurs.
	 * @return La Liste des Controlleurs, sous forme d'une Liste de Controllers.
	 */
	public List<Controller> getListControlleurs() {
		return listControlleurs;
	}
	/**
	 * Retourne le Fichier du Mod�le.
	 * @return Le Fichier du Mod�le, sous forme d'un File.
	 */
	public File getFile() {
		return file;
	}
	/**
	 * Retourne le Mouvement du Mod�le.
	 * @return Le Mouvement du Mod�le, sous forme d'un Mouvement.
	 */
	public Mouvement getMouvement() {
		return mouvement;
	}
	/**
	 * Retourne l'instance unique du Mod�le.
	 * @return L'instance unique du Mod�le, sous forme d'un Model final et static.
	 */
	public static final Model getInstance(){
		return instance;
	}

	/**
	 * Met � jour la Liste des Sommets du Mod�le.
	 * @param listeSommets
	 * 				La nouvelle Liste des Sommets du Mod�le.
	 */
	public void setListeSommets(List<Sommet> listeSommets) {
		this.listeSommets = listeSommets;
	}
	/**
	 * Met � jour le Fichier du Mod�le.
	 * <p>Va cr�er une nouvelle instance de File</p>
	 * @param name
	 * 				Le nom du nouveau Fichier du Mod�le.
	 * @see File#File(String)
	 */
	public void setFile(String name) {
		file = new File(pathRessources + name);
	}
	/**
	 * Met � jour le Mouvement du Mod�le.
	 * @param mouvement
	 * 				Le nouveau Mouvement du Mod�le.
	 */
	public void setMouvement(Mouvement mouvement) {
		this.mouvement = mouvement;
	}

}