package application;

import java.util.ArrayList;
import java.util.List;

import dessin.Face;
import dessin.Sommet;

public class Model {

	private List<Sommet> listeSommets;
	private List<Face> listeFaces;
	
	public Model() {
		this.listeSommets = new ArrayList<Sommet>();
		this.listeFaces = new ArrayList<Face>();
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
