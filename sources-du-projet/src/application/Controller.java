package application;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class Controller implements Initializable {
	private String pathRessources = "./ressources/";
	@FXML	private Label nameFile;
	@FXML	private Label NBfaces;
	@FXML	private Label nameAuthor;
	@FXML	private Label dateFile;
	@FXML	private Label description;
	@FXML	private ListView<String> listView;
	
	List<String> filteredFileList;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		listView = new ListView<String>();
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		File pathT = new File(pathRessources);
		String[] filelistT = pathT.list();
		filteredFileList = new ArrayList<String>();
		if (filelistT != null) {
			for (String fichier:filelistT) {
				filteredFileList.add(fichier.substring(0, fichier.length()));
			}
		}
		
		listView.getItems().addAll();
		//slistView.getSelectionModel().getSelectedItems().addListener(new openTask());
		
		
	}
	
}
