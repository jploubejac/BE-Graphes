package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class LabelStar extends Label {
    double costToDest;
    public LabelStar(Node sommet_courant, boolean marque, float cout_realise, Node sommet_pere, Arc arc_pere, double costToDest){
        super(sommet_courant, marque, cout_realise, sommet_pere, arc_pere);
        this.costToDest=costToDest;
    }
    public void setCostToDest(double costToDest){
        this.costToDest=costToDest;
    }
    public double getCostToDest(){
        return this.costToDest;
    }
    public double getTotalCost(){
        return this.getCout_realise()+ this.costToDest;
    }
    public int compareTo(Label label2){
		if(this.getTotalCost()==label2.getTotalCost())return 0;
		else if(this.getTotalCost()>label2.getTotalCost())return 1;
		else return -1;
	}
}
