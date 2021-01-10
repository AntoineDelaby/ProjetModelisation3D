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
 * Model est la classe de gestion du Modèle.
 * <p> Un Model est caractérisé par les informations suivantes :
 * <ul><li>Une liste de Sommets, suceptible d'être changée</li>
 * <li>Une liste de Faces, suceptible d'être changée</li>
 * <li>Une liste de Vecteurs Normaux</li>
 * <li>Un chemin d'accès aux ressources, unique attribué définitivement.</li>
 * <li>Un Fichier, suceptible d'être changé</li>
 * <li>Un mouvement</li>
 * <li>Une liste de Controlleurs</li>
 * <li>Un Singleton du modèle, unique attribué définitivement.</li></ul></p>
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Mathéo Gallego
 */
public class Model {
	/**
	 * La liste des Sommets du Modèle.
	 * @see Model#setListeSommets(List)
	 */
	private List<Sommet> listeSommets;
	/**
	 * La liste des Faces du Modèle.
	 */
	private List<Face> listeFaces;
	/**
	 * La liste des Vecteurs Normaux du Modèle.
	 */
	private List<Vecteur> listeVectNorm;
	/**
	 * La liste des Controlleurs du Modèle.
	 * @see Model#getListControlleurs()
	 */
	private List<Controller>listControlleurs;
	/**
	 * Le Chemin d'accès aux ressources (fichiers ply).
	 */
	private final String pathRessources = "./ressources/";
	/**
	 * Le fichier ply du Modèle.
	 * @see Model#setFile(String)
	 */
	private File file;
	/**
	 * Le mouvement associé au Modèle.
	 * @see Model#setMouvement(Mouvement)
	 */
	private Mouvement mouvement;
	/**
	 * Le Singleton du Modèle.
	 * @see Model#getInstance()
	 */
	private static final Model instance = new Model();


	/**
	 * Constructeur Model.
	 * <p><ul><li>Créé une nouvelle liste de Controlleurs, vide.</li>
	 * <li>Créé une nouvelle liste de Sommets, vide.</li>
	 * <li>Créé une nouvelle liste de Faces, vide.</li>
	 * <li>Créé une nouvelle liste de Vecteurs Normaux, vide.</li>
	 * <li>Affecte "null" au fichier du Modèle.</li></ul></p>
	 */
	private Model() {
		listControlleurs = new ArrayList<>();
		listeSommets = new ArrayList<>();
		listeFaces = new ArrayList<>();
		listeVectNorm = new ArrayList<>();
		file = null;
	}

	/**
	 * Retourne la Liste des Fichiers PLY présents dans le chemin d'accès des ressources.
	 * @return La Liste des Fichiers PLY présents dans le chemin d'accès des ressources, sous forme d'une Liste de chaînes de Caractères.
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
	 * Effectue le mouvement sur le Modèle.
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
	 * Met à jour le modèle.
	 * <p>Méthode appelée lorsqu'aucun mouvement sur le modèle n'est effectué.
	 * Par exemple lorsque l'on change la couleur des Faces où que l'on active l'éclairage, etc..</p>
	 */
	public void updateForNoMovment() {
		for(Controller c : this.getListControlleurs()) {
			c.changeLineAndFacesColor();
			c.getDf().dessinerModele(null);
		}
	}

	/**
	 * Initialise la liste des Vecteurs Normaux du Modèle.
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
	 * Va update le Modèle pour tous les Controller de la Liste des Controlleurs.
	 */
	public void notifyForUpdate() {
		for(Controller c : this.listControlleurs) {
			c.update();
		}
	}


	/**
	 * Retourne la Liste des Sommets du Modèle.
	 * @return La Liste des Sommets du Modèle, sous forme d'une Liste de Sommets.
	 */
	public List<Sommet> getListeSommets() {
		return listeSommets;
	}
	/**
	 * Retourne la Liste des Faces du Modèle.
	 * @return La Liste des Faces du Modèle, sous forme d'une Liste de Faces.
	 */
	public List<Face> getListeFaces() {
		return listeFaces;
	}
	/**
	 * Retourne la Liste des Vecteurs Normaux du Modèle.
	 * @return La Liste des Vecteurs Normaux du Modèle, sous forme d'une Liste de Vecteurs.
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
	 * Retourne le Fichier du Modèle.
	 * @return Le Fichier du Modèle, sous forme d'un File.
	 */
	public File getFile() {
		return file;
	}
	/**
	 * Retourne le Mouvement du Modèle.
	 * @return Le Mouvement du Modèle, sous forme d'un Mouvement.
	 */
	public Mouvement getMouvement() {
		return mouvement;
	}
	/**
	 * Retourne l'instance unique du Modèle.
	 * @return L'instance unique du Modèle, sous forme d'un Model final et static.
	 */
	public static final Model getInstance(){
		return instance;
	}

	/**
	 * Met à jour la Liste des Sommets du Modèle.
	 * @param listeSommets
	 * 				La nouvelle Liste des Sommets du Modèle.
	 */
	public void setListeSommets(List<Sommet> listeSommets) {
		this.listeSommets = listeSommets;
	}
	/**
	 * Met à jour le Fichier du Modèle.
	 * <p>Va créer une nouvelle instance de File</p>
	 * @param name
	 * 				Le nom du nouveau Fichier du Modèle.
	 * @see File#File(String)
	 */
	public void setFile(String name) {
		file = new File(pathRessources + name);
	}
	/**
	 * Met à jour le Mouvement du Modèle.
	 * @param mouvement
	 * 				Le nouveau Mouvement du Modèle.
	 */
	public void setMouvement(Mouvement mouvement) {
		this.mouvement = mouvement;
	}

}