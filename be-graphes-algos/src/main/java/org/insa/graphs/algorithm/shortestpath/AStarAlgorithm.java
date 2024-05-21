package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;

import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    ArrayList<Label> BuildLabel(int nbNodes, Graph graph, Node destination,Node origin, Mode mode, int maxSpeed){
        ArrayList<Label> labels=new ArrayList<Label>();
        double birdTravel=0;
        for(int i=0; i<nbNodes;i++){
            birdTravel=destination.getPoint().distanceTo(graph.get(i).getPoint());
            if(mode==Mode.TIME)birdTravel/=maxSpeed;
            if(graph.get(i)!=origin)labels.add(new LabelStar(graph.get(i),false,Double.POSITIVE_INFINITY,null,null,birdTravel));
            else labels.add(new LabelStar(graph.get(i),false,0,null,null,birdTravel));
        }
        return labels;
    }
}
