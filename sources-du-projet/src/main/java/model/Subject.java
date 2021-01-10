package model;

import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import vues.Observer;

/**
 * Subject est la classe abstraite de gestion des Sujets (les Observ�s).
 * <p> Un Subject est caract�ris� par l'information suivante :</p>
 * <ul><li>Une liste de Controlleurs, suceptible d'�tre chang�e.</li></ul>
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Math�o Gallego
 */
public abstract class Subject {
	/**
	 * La liste des Controllers du Sujet.
	 * @see Subject#getAttached()
	 */
    protected List<Controller> attached;

    /**
     * Constructeur Subject.
     * <ul><li>Cr�� une nouvelle liste de Controllers, vide.</li></ul>
     */
    public Subject() {
        attached = new ArrayList<>();
    }
    
    /**
     * Ajoute � la Liste des Controlleurs du Sujet l'observeur pass� en param�tre.
     * @param obs
     * 				L'observeur que l'on souhaite ajouter � la liste des Controlleurs du Sujet.
     */
	public void attach(Observer obs) {
        if (! attached.contains( obs)) {
            attached.add((Controller) obs);
        }
    }
	
	/**
     * Retire � la Liste des Controlleurs du Sujet l'observeur pass� en param�tre.
     * @param obs
     * 				L'observeur que l'on souhaite retirer de la liste des Controlleurs du Sujet.
     */
    public void detach(Observer obs) {
        if (attached.contains( obs)) {
            attached.remove(obs);
        }
    }
    
    /**
     * Met � jour tous les Observers du Sujet.
     */
	public void notifyForUpdate() {
		for(Observer c : attached) {
			c.update();
		}
	}

    /**
     * Retourne la liste des Controllers du Sujet.
     * @return La liste des Controllers du Sujet, sous forme de List de Controllers.
     */
    public List<Controller> getAttached() {
		return attached;
	}
}
