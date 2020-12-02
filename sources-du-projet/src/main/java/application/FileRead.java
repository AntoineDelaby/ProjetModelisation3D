package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dessin.Face;
import dessin.Sommet;

public class FileRead {
	private File file;
	private int nbFaces;
	private int nbSommets;
	private int nbLineIntro;

	private List<Sommet> listeSommets;
	private List<Face> listeFaces;
	
	public FileRead(File file, List<Sommet> list, List<Face> list2) throws IOException {
		this.file = file;
		this.listeFaces = list2;
		this.listeSommets = list;
		nbFaces = findNbFaces();
		nbLineIntro = findNbLineIntro();
		nbSommets = findNBSommets();
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
