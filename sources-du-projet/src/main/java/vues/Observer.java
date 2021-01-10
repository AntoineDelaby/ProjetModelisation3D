package vues;

import model.Subject;

public interface Observer {
		public void update();
        public void update(Subject subj);
}
