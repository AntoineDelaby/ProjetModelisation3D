package vues;

import controller.Subject;
import javafx.scene.canvas.Canvas;

public interface Observer {
    public void update(Subject subj);
    public void update(Subject subj, Canvas data);
}
