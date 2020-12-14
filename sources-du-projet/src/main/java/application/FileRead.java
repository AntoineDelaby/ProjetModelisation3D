package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dessin.Face;
import dessin.Sommet;

/**
 * Classe de gestion du fichier PLY.
 * @author Groupe5
 */
public class FileRead {	
	/**
	 * le fichier PLY.
	 */
	private File file;
	/**
	 * nombre de faces du fichier PLY.
	 */
	private int nbFaces;
	/**
	 * nombre de sommets du fichier PLY.
	 */
	private int nbSommets;
	/**
	 * nombre de lignes de l'introduction du fichier PLY.
	 */
	private int nbLineIntro;
	/**
	 * liste des Faces du fichier PLY.
	 */
	private List<Sommet> listeSommets;
	/**
	 * liste des Faces du fichier PLY.
	 */
	private List<Face> listeFaces;
	
	/**
	 * Constructeur de FileRead
	 * @param file 		le fichier PLY actuellement présent dans la view.
	 * @param listeSommets		la liste des sommets correspondante au fichier PLY actuel
	 * @param listeFaces		la liste des faces correspondante au fichier PLY actuel
	 * @throws IOException
	 */
	public FileRead(File file, List<Sommet> listeSommets, List<Face> listeFaces) throws IOException {
		this.file = file;
		this.listeFaces = listeFaces;
		this.listeSommets = listeSommets;
		nbFaces = findNbFaces();
		nbLineIntro = findNbLineIntro();
		nbSommets = findNBSommets();
	}

	/**
	 * Calcule et retourne le nombre de faces dans le fichier PLY.
	 * @return nbLines
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
	 * Calcule et retourne le nombre de sommets dans le fichier PLY.
	 * @return
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
	 * Calcule et retourne le nombre de lignes d'introduction dans le fichier PLY.
	 * @return
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
	 * @return le nombre de faces du fichier PLY.
	 */
	public int getNbFaces() {
		return nbFaces;
	}

	/**
	 * @return le nombre de sommets du fichier PLY.
	 */
	public int getNbSommets() {
		return nbSommets;
	}

	/**
	 * @return le nombre de ligne d'introduction du fichier PLY.
	 */
	public int getNbLineIntro() {
		return nbLineIntro;
	}
	
	/**
	 * @return la liste des sommets du fichier PLY.
	 */
	public List<Sommet> getListeSommets() {
		return listeSommets;
	}

	/**
	 * @return la liste des faces du fichier PLY.
	 */
	public List<Face> getListeFaces() {
		return listeFaces;
	}
	
	/**
	 * Efface les données de la liste des sommets correspondante au fichier PLY.
	 */
	public void clearListeSommets() {
		listeSommets.clear();;
	}

	/**
	 * Efface les données de la liste des faces correspondante au fichier PLY.
	 */
	public void clearListeFaces() {
		listeFaces.clear();
	}
}
