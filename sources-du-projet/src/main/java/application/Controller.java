package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class Controller implements Initializable{
	private String pathRessources = "./ressources/";
	@FXML	private Label nameFile;
	@FXML	private Label NBfaces;
	@FXML	private Label NBsommets;
	@FXML	private Label dateFile;
	@FXML	private Label description;
	@FXML	private ListView<String> listView;
	@FXML	private Pane pane;
	@FXML	private Button rotateY;
	@FXML	private Button rotateX;
	@FXML 	private Button rotateZ;
	@FXML	private Slider translation;
	@FXML	private Canvas canvas;
	@FXML 	private Button executeTranslate;
	@FXML	private TextField trX;
	@FXML	private TextField trY;
	@FXML	private TextField trZ;
	@FXML   private Button zoom;
	@FXML   private Button deZoom;
	private ArrayList <Sommet> listeSommets = new ArrayList<Sommet>();
	private ArrayList <Face> listeFaces = new ArrayList<Face>();
	private GraphicsContext gc;
	private double factZoom;
	
	public Label getNameFile() {
		return nameFile;
	}

	public Label getNBfacesFile() {
		return NBfaces;
	}

	public Label getNBsommets() {
		return NBsommets;
	}

	public Label getDateFile() {
		return dateFile;
	}

	public Label getDescription() {
		return description;
	}

	public ArrayList<Sommet> getListeSommets() {
		return listeSommets;
	}

	public ArrayList<Face> getListeFaces() {
		return listeFaces;
	}

	List<String> filteredFileList;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.factZoom = 1;
		trX.setText("0");
		trY.setText("0");
		File pathT = new File(pathRessources);
		String[] filelist = pathT.list();
		filteredFileList = new ArrayList<String>();
		if (filelist != null) {
			for (String fichier:filelist) {
				filteredFileList.add(fichier);
			}
		}		
		listView.getItems().addAll(filteredFileList);
		listView.getSelectionModel().getSelectedItems().addListener(new openModel());
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}

	//Event select task in listView
	class openModel implements ListChangeListener<String> {
		public void onChanged( ListChangeListener.Change<? extends String> c) {
			listeSommets.clear();
			listeFaces.clear();
			gc = canvas.getGraphicsContext2D();
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			String name = listView.getSelectionModel().getSelectedItem().substring(0, listView.getSelectionModel().getSelectedItem().length()-4);
			File f= new File (pathRessources + listView.getSelectionModel().getSelectedItem());
			initFile(f, name);
		}

	}
	
	public void initFile(File f, String name) {
		nameFile.setText(name);
		Date date = new Date(f.lastModified());
		SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
		String date2 = sf.format(date);
		dateFile.setText(date2);
		try {
			NBfaces.setText(""+getNbFaces(f));
			NBsommets.setText(""+getNBSommets(f));
			initSommets(f);
			initFaces(f);
			for(int i = 0; i < listeFaces.size(); i++) {
				dessinFace(listeFaces.get(i));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void rotateAxe(char c) {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		double pi =Math.PI;
		int facteurAngle=10;
		float[][] matrice = {{(float)Math.cos(pi/facteurAngle),(float)-Math.sin(pi/facteurAngle),0},{(float)Math.sin(pi/facteurAngle),(float)Math.cos(pi/facteurAngle),0},{0,0,1}};
		if(c=='X') {
			matrice = new float[][] {{1,0,0}, {0,(float) Math.cos(pi/facteurAngle),(float) - Math.sin(pi/facteurAngle)},{0,(float) Math.sin(pi/facteurAngle),(float) Math.cos(pi/facteurAngle)}};

		}else if(c=='Y'){
			matrice = new float[][] {{(float)Math.cos(pi/facteurAngle),0,(float)-Math.sin(pi/facteurAngle)},{0,1,0},{(float)Math.sin(pi/facteurAngle),0,(float)Math.cos(pi/facteurAngle)}};
		}
		rotateDegret(facteurAngle, matrice);					
		for (Face f : listeFaces)
			dessinFace(f);
	}
	
	public void rotateDegret(int facteurAngle, float[][]matrice) {
		for (Sommet s : listeSommets) {
			float xtempo = s.x;
			float ytempo = s.y;
			float ztempo = s.z;
			s.x=xtempo*matrice[0][0]+ytempo*matrice[0][1]+ztempo*matrice[0][2];
			s.y=xtempo*matrice[1][0]+ytempo*matrice[1][1]+ztempo*matrice[1][2];
			s.z=xtempo*matrice[2][0]+ytempo*matrice[2][1]+ztempo*matrice[2][2];
			System.out.println(s.x+","+s.y+","+s.z);
		}	
	}

	@FXML public void rotateModelY () {
		rotateAxe('Y');
	}

	@FXML public void rotateModelZ () {
		rotateAxe('Z');
	}

	@FXML	public void rotateModelX () {
		rotateAxe('X');
	}

	@FXML	public void translateModel() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		 int x=0;
		int y=0;
		int z=0;
		for (int i=0 ; i<trX.getText().length();i++) {					
			if ((trX.getText().charAt(i)>='0' && trX.getText().charAt(i)<='9') || trX.getText().charAt(0)=='-') {
				if ( trX.getText().charAt(i)=='-' ) {
					i++;
					if(trX.getText().charAt(i)<'0' || trX.getText().charAt(i)>'9')
						break;
					else if (i==trX.getText().length()-1)
						x=Integer.parseInt(trX.getText());
				}else
					if(trX.getText().charAt(i)<'0' || trX.getText().charAt(i)>'9')
						break;
					else if (i==trX.getText().length()-1)
						x=Integer.parseInt(trX.getText());
			}else {
				break;
			}
		}
		for (int i=0 ; i<trY.getText().length();i++) {
			if (trY.getText().charAt(i)>='0' && trY.getText().charAt(i)<='9' || trY.getText().charAt(0)=='-') {
				if (trY.getText().charAt(i)=='-' ) {		
					i++;
					if(trY.getText().charAt(i)<'0' || trY.getText().charAt(i)>'9')
						break;
					else if (i==trY.getText().length()-1)
						y=Integer.parseInt(trY.getText());		 
				}else 
					if (trY.getText().charAt(i)<'0' || trY.getText().charAt(i)>'9')
						break;
					else if (i==trY.getText().length()-1)
						y=Integer.parseInt(trY.getText());
			}else {
				break;
			}
		}
		decalagePoints(x, y, z);
		for (Face f : listeFaces) 
			dessinFace(f);

	}
	
	public void decalagePoints (int x, int y , int z) {
		for (Sommet s : listeSommets) {
			s.x+=x;
			s.y+=y;
			s.z+=z;
		}
	}

	@FXML public void zoomOnModel () throws IOException {        
        gc.clearRect(0, 0, canvas.getWidth(),canvas.getHeight());
        newCoordonZoom(factZoom);
        for(int i = 0; i < listeFaces.size(); i++) {
            dessinFace(listeFaces.get(i));
        }
    }

	
	public void newCoordonZoom(double zoom) {
		for (Sommet s : listeSommets) {
			s.x*=zoom;
			s.y*=zoom;
			s.z*=zoom;
		}
	}
	
	@FXML public void zoomButton() throws IOException {
        factZoom = 2;
        zoomOnModel();
    }

    @FXML public void deZoomButton() throws IOException {
        factZoom = 0.5;
        zoomOnModel();
    }

	public void initSommets(File f) throws IOException{
		float facteurDecalage=0;
		int cptEspaces;
		int idx = 0;
		String temp = "";
		String[]coord= new String [3];
		FileReader fr= new FileReader (f);
		BufferedReader br = new BufferedReader(fr);
		int lignesIntro = getNbLineIntro(f);
		while(idx < lignesIntro) {
			br.readLine();
			idx ++;
		}			
		for(int x = idx; x < idx + getNBSommets(f); x ++) {
			cptEspaces=0;
			temp = br.readLine();
			for (int i =0;i<temp.length();i++) {
				if (temp.charAt(i)==' ') {
					cptEspaces++;
					if (temp.charAt(i+1)!=' ')
						break;
				}		
			}				
			if(cptEspaces==3)
				coord = temp.split("   ");
			if (cptEspaces==2)
				coord=temp.split("  ");
			if (cptEspaces==1)
				coord=temp.split(" ");
			listeSommets.add(new Sommet(Float.parseFloat(coord[0]), Float.parseFloat(coord[1]), Float.parseFloat(coord[2])));				
		}
		facteurDecalage=getMin(listeSommets);
		for (Sommet s : listeSommets) {
			s.x=(s.x-facteurDecalage);
			s.y=(s.y-facteurDecalage);
			s.z=(s.z-facteurDecalage);
		}

		br.close();
	}
	public float getMaxY (ArrayList <Sommet> liste) {
		float res= 0;
		for (Sommet s : liste) {
			if (s.y>res)
				res=s.y;
		}
		return res;
	}

	public float getMin (ArrayList <Sommet> liste) {
		float res= 0;
		for (Sommet s : liste) {
			if (s.x<res)
				res=s.x;
			if (s.y<res)
				res=s.y;
			if (s.z<res)
				res=s.z;
		}
		return res;
	}
	public void initFaces(File f) throws IOException{
		int idx = 0;
		String temp = "";
		String[]sommetsListe;
		FileReader fr= new FileReader (f);
		BufferedReader br = new BufferedReader(fr);
		int lignesAvantFaces = getNbLineIntro(f) + getNBSommets(f);
		while(idx < lignesAvantFaces) {
			br.readLine();
			idx ++;
		}
		for(int x = idx; x < idx + getNbFaces(f); x ++) {
			Face face = new Face();
			temp = br.readLine();
			sommetsListe = temp.split(" ");
			for(int j = 1; j < sommetsListe.length; j++) {
				face.addSommet(Integer.parseInt(sommetsListe[j]));
			}
			listeFaces.add(face);
		}
		br.close();
	}

	public float getMinZ() {
		float min = listeSommets.get(0).getZ();
		for(int i = 1; i < listeSommets.size(); i++) {
			if(listeSommets.get(i).getZ() < min) {
				min = listeSommets.get(i).getZ();
			}
		}
		return min;
	}

	public void dessinFace(Face f) {
		gc.beginPath();
		gc.moveTo(listeSommets.get(f.getSommets().get(0)).getX(), listeSommets.get(f.getSommets().get(0)).getY());
		gc.lineTo(listeSommets.get(f.getSommets().get(1)).getX(), listeSommets.get(f.getSommets().get(1)).getY());
		gc.lineTo(listeSommets.get(f.getSommets().get(2)).getX(), listeSommets.get(f.getSommets().get(2)).getY());
		gc.lineTo(listeSommets.get(f.getSommets().get(0)).getX(), listeSommets.get(f.getSommets().get(0)).getY());
		gc.stroke();
	}

	public int getNbFaces (File f) throws IOException{
		int nbLines= 0;
		FileReader fr= new FileReader (f);
		BufferedReader br = new BufferedReader(fr);
		String temp = "";
		while((temp = br.readLine())!=null) {
			if(temp.substring(0,2).equals("3 ")) {
				nbLines ++;
			}
		}
		fr.close();
		return nbLines;
	}

	public int getNBSommets (File f) throws IOException{
		int nbLines= 0;
		FileReader fr = new FileReader (f);
		BufferedReader br = new BufferedReader (fr);
		while ((br.readLine())!=null) {
			nbLines++;
		}
		fr.close();
		return nbLines-getNbLineIntro(f)-getNbFaces(f);
	}

	public int getNbLineIntro(File f) throws IOException {
		int nbLines = 0;
		FileReader fr = new FileReader (f);
		BufferedReader br = new BufferedReader(fr);
		while(!br.readLine().equals("end_header")) {
			nbLines ++;
		}
		br.close();
		return ++nbLines;
	}

}