package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

public class Zoom extends Movement{
	private double factZoom;
	
	public Zoom(Canvas c) {
		super(c);
		factZoom = 1;
	}
	
	public void zoomMolette() {
		canvas.setOnScroll(e -> {
			e.consume();
			if (e.getDeltaY() == 0) {
				return;
			}
			if (e.getDeltaY() > 0)
				try {
					zoomButton();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			else
				try {
					deZoomButton();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		});
	}
	
	@FXML
	public void zoomOnModel() throws IOException {
		gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		newCoordonZoom(factZoom);
		for (int i = 0; i < listeFaces.size(); i++) {
			dessinFace(listeFaces.get(i));
		}
	}
	
	public void zoomButton() throws IOException {
		factZoom = 1.1;
		zoomOnModel();
	}

	public void deZoomButton() throws IOException {
		factZoom = 0.9;
		zoomOnModel();
	}
	
	public void newCoordonZoom(double zoom) {
		for (Sommet s : listeSommets) {
			s.x *= zoom;
			s.y *= zoom;
			s.z *= zoom;
		}
	}
}
