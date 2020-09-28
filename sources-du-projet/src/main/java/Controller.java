package application;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ListChangeListener;/*
import javafx.fxml.FXML;
import javafx.fxml.Initializable;*/
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class Controller {
	private String pathRessources = "./sources-du-projet/ressources/";
		private Label nameFile;
		private Label NBfaces;
		private Label nameAuthor;
		private Label dateFile;
		private Label description;
		private ListView<String> listView;
	
	List<String> filteredFileList;
	
	/*@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		listView = new ListView<String>();
		File pathT = new File(pathRessources);
		String[] filelist = pathT.list();
		filteredFileList = new ArrayList<String>();
		if (filelist != null) {
			for (String fichier:filelist) {
				filteredFileList.add(fichier);
			}
		}
		
		System.out.println(filteredFileList);
		listView.getItems().add("test");
		listView.getItems().addAll(filteredFileList);
		listView.getSelectionModel().getSelectedItems().addListener(new openModel());
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}
	
	//Event select task in listView
		class openModel implements ListChangeListener<String> {
			public void onChanged( ListChangeListener.Change<? extends String> c) {
				
			}
		}*/
}
