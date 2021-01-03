package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import dessin.Face;
import dessin.Sommet;
import model.Model;

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
	
	private Model model;
	
	/**
	 * Constructeur de FileRead
	 * @throws IOException
	 */
	public FileRead(Model model) throws IOException {
		file = model.getFile();
		this.model = model;
		nbFaces = findNbFaces();
		nbLineIntro = findNbLineIntro();
		nbSommets = findNBSommets();
	}

	public void setFile() {
		try {
			initSommets();
			initFaces();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void initFaces() throws IOException {
		String[] listeSommets;
		int lignesAvantFaces = nbLineIntro + nbSommets;
		BufferedReader br = new BufferedReader(new FileReader(file));
		for (int i = 0; i < lignesAvantFaces; i++) {
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
	
	public void initSommets() throws IOException {
		float facteurDecalage = 0;
		int cptEspaces;
		int idx = 0;
		String temp = "";
		String[] coord = new String[3];
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		int lignesIntro = nbLineIntro;
		while (idx < lignesIntro) {
			br.readLine();
			idx++;
		}
		for (int x = idx; x < idx + nbSommets; x++) {
			cptEspaces = 0;
			temp = br.readLine();
			for (int i = 0; i < temp.length(); i++) {
				if (temp.charAt(i) == ' ') {
					cptEspaces++;
					if (temp.charAt(i + 1) != ' ')
						break;
				}
			}
			if (cptEspaces == 3)
				coord = temp.split("   ");
			if (cptEspaces == 2)
				coord = temp.split("  ");
			if (cptEspaces == 1)
				coord = temp.split(" ");
			model.getListeSommets().add(new Sommet(Float.parseFloat(coord[0]), Float.parseFloat(coord[1]), Float.parseFloat(coord[2])));
		}
		facteurDecalage = getMin(model.getListeSommets());
		for (Sommet s : model.getListeSommets()) {
			s.setX(s.getX() - facteurDecalage);
			s.setY(s.getY() - facteurDecalage);
			s.setZ(s.getZ() - facteurDecalage);
		}

		br.close();
	}
	
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
}
