package model;

import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import vues.Observer;

public abstract class Subject {
    protected List<Controller> attached;

    public Subject() {
        attached = new ArrayList<>();
    }

    public void attach(Observer obs) {
        if (! attached.contains( obs)) {
            attached.add((Controller) obs);
        }
    }

    public void detach(Observer obs) {
        if (attached.contains( obs)) {
            attached.remove(obs);
        }
    }
    
	public void notifyForUpdate() {
		for(Observer c : attached) {
			c.update();
		}
	}

}
