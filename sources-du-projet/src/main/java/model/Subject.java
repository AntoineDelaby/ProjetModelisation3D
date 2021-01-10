package model;

import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import vues.Observer;

/**
 * Subject est la classe abstraite de gestion des Sujets (les Observés).
 * <p> Un Subject est caractérisé par l'information suivante :</p>
 * <ul><li>Une liste de Controlleurs, suceptible d'être changée.</li></ul>
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Mathéo Gallego
 */
public abstract class Subject {
	/**
	 * La liste des Controllers du Sujet.
	 * @see Subject#getAttached()
	 */
    protected List<Controller> attached;

    /**
     * Constructeur Subject.
     * <ul><li>Créé une nouvelle liste de Controllers, vide.</li></ul>
     */
    public Subject() {
        attached = new ArrayList<>();
    }
    
    /**
     * Ajoute à la Liste des Controlleurs du Sujet l'observeur passé en paramètre.
     * @param obs
     * 				L'observeur que l'on souhaite ajouter à la liste des Controlleurs du Sujet.
     */
	public void attach(Observer obs) {
        if (! attached.contains( obs)) {
            attached.add((Controller) obs);
        }
    }
	
	/**
     * Retire à la Liste des Controlleurs du Sujet l'observeur passé en paramètre.
     * @param obs
     * 				L'observeur que l'on souhaite retirer de la liste des Controlleurs du Sujet.
     */
    public void detach(Observer obs) {
        if (attached.contains( obs)) {
            attached.remove(obs);
        }
    }
    
    /**
     * Met à jour tous les Observers du Sujet.
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
