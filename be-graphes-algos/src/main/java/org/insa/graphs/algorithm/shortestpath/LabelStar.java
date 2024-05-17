package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class LabelStar extends Label {
    double birdTravel;
    public LabelStar(Node sommet_courant, boolean marque, double cout_realise, Node sommet_pere, Arc arc_pere, double birdTravel){
        super(sommet_courant, marque, cout_realise, sommet_pere, arc_pere);
        this.birdTravel=birdTravel;
    }

    public double getbirdTravel(){
        return this.birdTravel;
    }
    @Override
    public double getTotalCost(){
        return this.getCout_realise()+this.birdTravel;
    }
    /*public int compareTo(Label label2){
		if(this.getTotalCost()==label2.getTotalCost())return 0;
		else if(this.getTotalCost()>label2.getTotalCost())return 1;
		else return -1;
	}*/
}
