package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import dessin.Face;
import dessin.Sommet;

/**
 * FileRead est la classe de gestion du fichier PLY.
 * <p> Un FileRead est caractérisé par les informations suivantes :
 * <ul>
 * <li>Un Fichier unique attribué définitivement.</li>
 * <li>Un nombre de Faces.</li>
 * <li>Un nombre de Sommets.</li>
 * <li>Un nombre de lignes d'Introduction.</li>
 * <p> De plus un FileRead est composé d'un Modèle, auquel les Faces et Sommets du Fichier seront attribués.</p>
 * </ul></p>
 * @see Model
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Mathéo Gallego
 */
public class FileRead {	
	/**
	 * Le fichier PLY.
	 */
	private File file;
	/**
	 * Le nombre de faces du fichier PLY.
	 * @see FileRead#getNbFaces()
	 */
	private int nbFaces;
	/**
	 * Le nombre de sommets du fichier PLY.
	 * @see FileRead#getNbSommets()
	 */
	private int nbSommets;
	/**
	 * Le nombre de lignes de l'introduction du fichier PLY.
	 * @see FileRead#getNbLineIntro()
	 */
	private int nbLineIntro;
	/**
	 * Le modele.
	 */
	private Model model;
	
	/**
     * Constructeur FileRead.
     * <p>
     * A la construction d'un objet FileRead, le nombre de faces "nbFaces",
     * le nombre de lignes d'intro "nbLineIntro",
     * et le nombre de sommets "nbSommets" du fichier ply, sont calculés.
     * Le fichier est récupéré grâce au {@linkplain FileRead#model}.
     * </p>
     * 
     * @param model
     *				Le modèle du fichier.
     * 
     * @see FileRead#file
     * @see FileRead#model
     * @see FileRead#nbFaces
     * @see FileRead#nbLineIntro
     * @see FileRead#nbSommets
     */
	public FileRead(Model model) throws IOException {
		file = model.getFile();
		this.model = model;
		nbFaces = findNbFaces();
		nbLineIntro = findNbLineIntro();
		nbSommets = findNBSommets();
	}

	/**
     * Met à jour les Sommets et les Faces en executant
     * initSommets() et initFaces() et traite les Exceptions.
     * Cette fonction est appelée par le Controller à chaque ouverture d'un nouveau fichier PLY.
     * @see FileRead#initSommets()
     * @see FileRead#initFaces()
     */
	public void setFile() {
		try {
			initSommets();
			initFaces();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialise la liste des Sommets dans le modèle.
	 * @throws IOException
	 * 
	 * @see {@link Model#getListeSommets()}
	 */
	public void initSommets() throws IOException {
		String[] coord = new String[3];
		BufferedReader br = new BufferedReader(new FileReader(file));
		for (int idx = 0; idx < nbLineIntro; idx++) {
			br.readLine();
		}
		for (int idx = 0; idx < nbSommets; idx++) {
			int cptEspaces = 0;
			String temp = br.readLine();
			for (int i = 0; i < temp.length(); i++) {
				if (temp.charAt(i) == ' ') {
					cptEspaces++;
					if (temp.charAt(i + 1) != ' ')
						break;
				}
			}
			if (cptEspaces == 1)
				coord = temp.split(" ");
			else if (cptEspaces == 2)
				coord = temp.split("  ");
			else
				coord = temp.split("   ");
			model.getListeSommets().add(new Sommet(Float.parseFloat(coord[0]), Float.parseFloat(coord[1]), Float.parseFloat(coord[2])));
		}
		float facteurDecalage = getMin(model.getListeSommets());
		for (Sommet s : model.getListeSommets()) {
			s.setX(s.getX() - facteurDecalage);
			s.setY(s.getY() - facteurDecalage);
			s.setZ(s.getZ() - facteurDecalage);
		}
		br.close();
	}
	
	/**
	 * Initialise la liste des Faces dans le modèle.
	 * @throws IOException
	 * 
	 * @see {@link Model#getListeFaces()}
	 */
	public void initFaces() throws IOException {
		String[] listeSommets;
		BufferedReader br = new BufferedReader(new FileReader(file));
		for (int i = 0; i < nbLineIntro + nbSommets; i++) {
			br.readLine();
		}
		for (int i = 0; i < nbFaces; i++) {
			Face face = new Face();
			listeSommets = br.readLine().split(" ");
			for (int j = 1; j < listeSommets.length; j++) {
				face.addSommet(Integer.parseInt(listeSommets[j]));
			}
			model.getListeFaces().add(face);
		}
		br.close();
	}
	
	/**
	 * Retourne la coordonnée minimale (x, y ou z) parmis tous les sommets d'une liste.
	 * @param liste
	 * 				La liste des sommets du Modèle.
	 * @return la coordonnée minimale de la liste.
	 */
	public float getMin(List<Sommet> liste) {
		float res = 0;
		for (Sommet s : liste) {
			if (s.getX() < res)
				res = s.getX();
			if (s.getY() < res)
				res = s.getY();
			if (s.getZ() < res)
				res = s.getZ();
		}
		return res;
	}
	
	/**
	 * Calcule et retourne le nombre de faces du fichier PLY.
	 * @return Le nombre de faces du fichier PLY.
	 * @throws IOException
	 */
	private int findNbFaces() throws IOException {
		int nbLines = 0;
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String temp = "";
		while ((temp = br.readLine()) != null) {
			if (temp.substring(0, 2).equals("3 ")) {
				nbLines++;
			}
		}
		fr.close();
		return nbLines;
	}

	/**
	 * Calcule et retourne le nombre de sommets du fichier PLY.
	 * @return Le nombre de sommets du fichier PLY.
	 * @throws IOException
	 */
	private int findNBSommets() throws IOException {
		int nbLines = 0;
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		while ((br.readLine()) != null) {
			nbLines++;
		}
		fr.close();
		findNbLineIntro();
		return nbLines - nbLineIntro - nbFaces;
	}

	/**
	 * Calcule et retourne le nombre de lignes d'introduction du fichier PLY.
	 * @return Le nombre de lignes d'introduction du fichier PLY.
	 * @throws IOException
	 */
	private int findNbLineIntro() throws IOException {
		int nbLines = 0;
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		while (!br.readLine().equals("end_header")) {
			nbLines++;
		}
		br.close();
		++nbLines;
		return nbLines;
	}
	
	/**
	 * Retourne le nombre de faces du fichier PLY.
	 * @return le nombre de faces du fichier PLY.
	 */
	public int getNbFaces() {
		return nbFaces;
	}

	/**
	 * Retourne le nombre de sommets du fichier PLY.
	 * @return le nombre de sommets du fichier PLY.
	 */
	public int getNbSommets() {
		return nbSommets;
	}

	/**
	 * Retourne le nombre de ligne d'introduction du fichier PLY.
	 * @return le nombre de ligne d'introduction du fichier PLY.
	 */
	public int getNbLineIntro() {
		return nbLineIntro;
	}
}
