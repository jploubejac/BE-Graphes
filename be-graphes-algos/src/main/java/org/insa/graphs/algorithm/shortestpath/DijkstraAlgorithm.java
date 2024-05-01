package org.insa.graphs.algorithm.shortestpath;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Graph graph = data.getGraph();

        final int nbNodes = graph.size();
        
        Label[] labels = new Label[nbNodes];

        // HILAL
        BinaryHeap <Label> tas = new BinaryHeap<Label>();
        Node origin= data.getOrigin();
        for(int i=0; i<nbNodes;i++){
            labels[i].setArc_pere(null);
            labels[i].setCout_realise(Float.POSITIVE_INFINITY);
            labels[i].setMarque(false);
            labels[i].setSommet_courant(graph.get(i));
            if(labels[i].getSommet_courant()==origin){
                labels[i].setCout_realise(0);
                tas.insert(labels[i]);
            }
        }
        notifyOriginProcessed(data.getOrigin());
        

        for(int i=0; i<nbNodes;i++){
            Label currentLabel=tas.deleteMin();
            currentLabel.setMarque(true);
            for(Arc arc: currentLabel.getSommet_courant().getSuccessors()){
                Node destination= arc.getDestination();
                int indiceDest;
                for(indiceDest=0;destination!=labels[indiceDest].getSommet_courant() || indiceDest<nbNodes; indiceDest++ ){}
                Label destLabel=labels[indiceDest];
                if(destLabel.getCout_realise()==float.POSITIVE_INFINITY){
                    
                }
                if(destLabel==)
            }

        }

        // Destination has no predecessor, the solution is infeasible...
        if (predecessorArcs[data.getDestination().getId()] == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {

            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());

            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = predecessorArcs[data.getDestination().getId()];
            while (arc != null) {
                arcs.add(arc);
                arc = predecessorArcs[arc.getOrigin().getId()];
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }

        return solution;
    }

}
