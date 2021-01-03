package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import application.Vecteur;
import dessin.DessinFace;
import dessin.Face;
import dessin.Sommet;
import mouvement.Matrice;
import mouvement.Mouvement;
import mouvement.Translation;
import mouvement.Zoom;

public class Model {

	private List<Sommet> listeSommets;
	private List<Face> listeFaces;
	private List<Vecteur> listeVectNorm;

	private String pathRessources = "./ressources/";
	private File file;
	private Mouvement mouvement;
	private DessinFace df;
	
	public Model() {
		this.listeSommets = new ArrayList<Sommet>();
		this.listeFaces = new ArrayList<Face>();
		this.listeVectNorm = new ArrayList<Vecteur>();
		file = null;
	}

	public List<String> filterList() {
		List<String> filteredFileList = new ArrayList<String>();
		String[] filelist = new File(pathRessources).list();
		if (filelist != null) {
			for (String fichier : filelist) 
				filteredFileList.add(fichier);
		}
		return filteredFileList;
	}
	
	public void affiche() {
		df.clearCanvas();
		if (listeSommets.get(0).getX() < 2.0 && listeSommets.get(0).getY() < 2.0) {
			setMouvement(new Zoom(df, 1500));
			effectuerMouvement();
		} else if (listeSommets.get(0).getX() < 5.0 && listeSommets.get(0).getY() < 5.0) {
			setMouvement(new Zoom(df, 120));
			effectuerMouvement();
		}
		df.setActiverEclairage(false);
		df.dessinerModele(null);
	}

	public void effectuerMouvement() {
		float[][]model = Matrice.toMatrice(listeSommets);
		listeVectNorm.clear();		
		mouvement.mouvement(model);	
		setListeSommets(Matrice.toList(model));
		initNorm();
		
		if(!(mouvement instanceof Translation))	df.dessinerModele(null);
		else df.dessinerModele((Translation) mouvement);
	}
	
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

	public List<Vecteur> getListeVectNorm() {
		return listeVectNorm;
	}

	public void setListeVectNorm(List<Vecteur> listeVectNorm) {
		this.listeVectNorm = listeVectNorm;
	}

	public File getFile() {
		return file;
	}

	public void setFile(String name) {
		this.file = new File(pathRessources  + name);
	}

	public Mouvement getMouvement() {
		return mouvement;
	}

	public void setMouvement(Mouvement mouvement) {
		this.mouvement = mouvement;
	}

	public DessinFace getDf() {
		return df;
	}

	public void setDf(DessinFace df) {
		this.df = df;
	}
	
	
}
