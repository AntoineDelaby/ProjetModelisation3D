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

import javafx.collections.ListChangeListener;/*
import javafx.fxml.FXML;
import javafx.fxml.Initializable;*/
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class Controller implements Initializable{
	private String pathRessources = "./ressources/";
	@FXML	private Label nameFile;
	@FXML	private Label NBfaces;
	@FXML	private Label NBsommets;
	@FXML	private Label dateFile;
	@FXML	private Label description;
	@FXML	private ListView<String> listView;
	
	List<String> filteredFileList;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
				nameFile.setText(listView.getSelectionModel().getSelectedItem().substring(0, listView.getSelectionModel().getSelectedItem().length()-4));
				File f = new File (listView.getSelectionModel().getSelectedItem());
				File fbis= new File (pathRessources+f);
				Date date = new Date(fbis.lastModified());
				SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
				String date2 = sf.format(date);
				dateFile.setText(date2);
				try {
					NBfaces.setText(""+getNbFaces(f));
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					NBsommets.setText(""+getNBSommets(f));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		public int getNbFaces (File f) throws IOException{
			int nbLines=-1;
			FileReader fr= new FileReader (pathRessources+f);
			BufferedReader br = new BufferedReader(fr);
			while (( br.readLine())!=null ) {
				
				if (br.readLine().substring(0,2).equals("3 "))
					nbLines=nbLines+2;
			}
			fr.close();
			return nbLines;
			
		}
		
		public int getNBSommets (File f) throws IOException{
			int nbLines=-1;
			FileReader fr = new FileReader (pathRessources+f);
			BufferedReader br = new BufferedReader (fr);
			while ((br.readLine())!=null) {
				nbLines++;
		}
		fr.close();
		return nbLines-10-getNbFaces(f);
		
		}
}