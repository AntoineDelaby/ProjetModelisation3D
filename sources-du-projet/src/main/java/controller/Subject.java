package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import vues.Observer;


public class Subject {
	protected List<Observer> attached;

	public Subject() {
		attached = new ArrayList<>();
	}

	public void attach(Observer obs) {
		if (! attached.contains( obs)) {
			attached.add(obs);
		}
	}

	public void detach(Observer obs) {
		if (attached.contains( obs)) {
			attached.remove(obs);
		}
	}

	public void notifyObservers() {
		for (Observer o : attached) {
			o.update(this);
		}
	}


	public void notifyObservers(Canvas data) {
		for (Observer o : attached) {
			o.update(this, data);
		}
	}

}
