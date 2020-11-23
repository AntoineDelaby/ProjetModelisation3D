package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileRead {
	private File file;
	private int nbFaces;
	private int nbSommets;
	private int nbLineIntro;

	private ArrayList<Sommet> listeSommets;
	private ArrayList<Face> listeFaces;
	
	public FileRead(File file) throws IOException {
		this.file = file;
		nbFaces = findNbFaces();
		nbLineIntro = findNBSommets();
		nbSommets = findNbLineIntro();
		listeSommets = new ArrayList<Sommet>();
		listeFaces = new ArrayList<Face>();
		initSommets();
		initFaces();
	}

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
	
	private void initSommets() throws IOException {
		float facteurDecalage = 0;
		int cptEspaces;
		int idx = 0;
		String temp = "";
		String[] coord = new String[3];
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		//setNbLineIntro(f);
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
			listeSommets.add(
					new Sommet(Float.parseFloat(coord[0]), Float.parseFloat(coord[1]), Float.parseFloat(coord[2])));
		}
		facteurDecalage = getMin(listeSommets);
		for (Sommet s : listeSommets) {
			s.x = (s.x - facteurDecalage);
			s.y = (s.y - facteurDecalage);
			s.z = (s.z - facteurDecalage);
		}

		br.close();
	}
	
	private float getMin(ArrayList<Sommet> liste) {
		float res = 0;
		for (Sommet s : liste) {
			if (s.x < res)
				res = s.x;
			if (s.y < res)
				res = s.y;
			if (s.z < res)
				res = s.z;
		}
		return res;
	}
	
	private void initFaces() throws IOException {
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
			listeFaces.add(face);
		}
		br.close();
	}

	public int getNbFaces() {
		return nbFaces;
	}

	public int getNbSommets() {
		return nbSommets;
	}

	public int getNbLineIntro() {
		return nbLineIntro;
	}
	
	public ArrayList<Sommet> getListeSommets() {
		return listeSommets;
	}

	public ArrayList<Face> getListeFaces() {
		return listeFaces;
	}
	
	public void clearListeSommets() {
		listeSommets.clear();;
	}

	public void clearListeFaces() {
		listeFaces.clear();
	}

	
	
}
