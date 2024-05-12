package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    ArrayList<Label> BuildLabel(int nbNodes, Graph graph, Node origin){
        ArrayList<Label> labels=new ArrayList<Label>();
        for(int i=0; i<nbNodes;i++){
            if(graph.get(i)!=origin)labels.add(new LabelStar(graph.get(i),false,Float.POSITIVE_INFINITY,null,null,origin.getPoint().distanceTo(graph.get(i).getPoint())));
            else labels.add(new LabelStar(graph.get(i),false,0,null,null,origin.getPoint().distanceTo(graph.get(i).getPoint())));
        }
        return labels;
    }
}
