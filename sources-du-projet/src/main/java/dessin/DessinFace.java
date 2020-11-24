package Dessin;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public class DessinFace {
	private GraphicsContext gc;
	private ArrayList<Sommet> listeSommets;
	private ArrayList<Face> listeFaces;
	
	public DessinFace(GraphicsContext gc, ArrayList<Sommet> listeSommets,ArrayList<Face> listeFaces) {
		this.gc = gc;
		this.listeFaces = listeFaces;
		this.listeSommets = listeSommets;
	}

	public void getColorFace (ColorPicker face) {
		gc.setFill(face.getValue());
		for (Face f : listeFaces)
			dessinFace(f);
	}

	public void getColorLigne (ColorPicker ligne) {
		gc.setStroke(ligne.getValue());
		for (Face f : listeFaces)
			dessinFace(f);
	}

	public void dessinFace(Face f) {
		gc.beginPath();
		gc.moveTo(listeSommets.get(f.getSommets().get(0)).getX(), listeSommets.get(f.getSommets().get(0)).getY());
		gc.lineTo(listeSommets.get(f.getSommets().get(1)).getX(), listeSommets.get(f.getSommets().get(1)).getY());
		gc.lineTo(listeSommets.get(f.getSommets().get(2)).getX(), listeSommets.get(f.getSommets().get(2)).getY());
		gc.lineTo(listeSommets.get(f.getSommets().get(0)).getX(), listeSommets.get(f.getSommets().get(0)).getY());
		double [] x = new double [] {listeSommets.get(f.getSommets().get(0)).getX(),listeSommets.get(f.getSommets().get(1)).getX(),listeSommets.get(f.getSommets().get(2)).getX()};
		double [] y = new double [] {listeSommets.get(f.getSommets().get(0)).getY(),listeSommets.get(f.getSommets().get(1)).getY(),listeSommets.get(f.getSommets().get(2)).getY()};
		gc.fillPolygon(x, y, 3);
		gc.stroke();

	}

	public ArrayList<Sommet> getListeSommets() {
		return listeSommets;
	}

	public ArrayList<Face> getListeFaces() {
		return listeFaces;
	}
}
