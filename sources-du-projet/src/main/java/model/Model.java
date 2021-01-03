package model;

import java.util.ArrayList;
import java.util.List;

import application.Vecteur;
import dessin.Face;
import dessin.Sommet;

public class Model {

	private List<Sommet> listeSommets;
	private List<Face> listeFaces;
	private List<Vecteur> listeVectNorm;

	public Model() {
		this.listeSommets = new ArrayList<Sommet>();
		this.listeFaces = new ArrayList<Face>();
		this.listeVectNorm = new ArrayList<Vecteur>();
	}

	public List<Vecteur> getListeVectNorm() {
		return listeVectNorm;
	}

	public void setListeVectNorm(List<Vecteur> listeVectNorm) {
		this.listeVectNorm = listeVectNorm;
	}

	public List<Sommet> getListeSommets() {
		return listeSommets;
	}

	public void setListeSommets(List<Sommet> listeSommets) {
		this.listeSommets = listeSommets;
	}

	public List<Face> getListeFaces() {
		return listeFaces;
	}

	public void setListeFaces(List<Face> listeFaces) {
		this.listeFaces = listeFaces;
	}
}
