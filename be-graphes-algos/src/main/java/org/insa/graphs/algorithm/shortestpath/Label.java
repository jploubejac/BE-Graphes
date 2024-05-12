package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {
	private Node sommet_courant;
	private boolean marque;
	private float cout_realise;
	private Node sommet_pere;
	private Arc arc_pere;
	
	/* Constructeur de la classe */
	
	public Label(Node sommet_courant, boolean marque, float cout_realise, Node sommet_pere, Arc arc_pere) {
		this.sommet_courant = sommet_courant;
		this.marque = marque;
		this.cout_realise = cout_realise;
		this.sommet_pere = sommet_pere;
		this.arc_pere = arc_pere;
	}
	
	/* Getters */
	
	public Node getSommet_courant() {
		return this.sommet_courant;
	}

	public boolean getMarque() {
		return this.marque;
	}

	public float getCout_realise() {
		return this.cout_realise;
	}
	
	public Node getSommet_pere() {
		return this.sommet_pere;
	}
	
	public Arc getArc_pere() {
		return this.arc_pere;
	}
	
	/* Methodes */
	public double getTotalCost(){
        return this.getCout_realise();
    }
	public void setSommet_courant(Node sommet_courant){this.sommet_courant=sommet_courant;}
	public void setMarque(boolean marque){this.marque=marque;}
	public void setCout_realise(float cout_realise){this.cout_realise=cout_realise;}
	public void setSommet_pere(Node sommet_pere){this.sommet_pere=sommet_pere;}
	public void setArc_pere(Arc arc_pere){this.arc_pere=arc_pere;}

	public int compareTo(Label label2){
		if(this.getTotalCost()==label2.getTotalCost())return 0;
		else if(this.getTotalCost()>label2.getTotalCost())return 1;
		else return -1;
	}
	
	
}
