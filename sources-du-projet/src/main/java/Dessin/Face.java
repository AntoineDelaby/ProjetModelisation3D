package Dessin;

import java.util.ArrayList;

public class Face {
	private ArrayList<Integer>sommets;
	
	public Face() {
		this.sommets = new ArrayList<Integer>();
	}
	
	public void addSommet(int s) {
		this.sommets.add(s);
	}

	public ArrayList<Integer> getSommets() {
		return sommets;
	}
	
	public String toString() {
		String res = "Face : [" + this.sommets.get(0);
		for(int i = 1; i < this.sommets.size(); i++) {
			res += "," + this.sommets.get(i);
		}
		res += "]";
		return res;
	}
}
