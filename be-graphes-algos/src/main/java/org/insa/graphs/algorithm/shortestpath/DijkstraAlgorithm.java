package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.AbstractInputData.Mode;
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
        boolean destIsMarked=false;
        ArrayList<Label> labels;
        BinaryHeap <Label> tas = new BinaryHeap<Label>();
        
        Node origin= data.getOrigin();
        Node destination= data.getDestination();
        labels=BuildLabel(nbNodes, graph, destination, origin, data.getMode(), 36);
        tas.insert(labels.get(origin.getId()));
        notifyNodeReached(origin);
        notifyOriginProcessed(data.getOrigin());
        
        while(!tas.isEmpty() && !destIsMarked){
            Label currentLabel= tas.deleteMin();
            currentLabel=labels.get(labels.indexOf(currentLabel));
            currentLabel.setMarque(true);
            notifyNodeMarked(currentLabel.getSommet_courant());
            if(currentLabel.getSommet_courant()==destination)destIsMarked=true;
            else 
            for(Arc arc: currentLabel.getSommet_courant().getSuccessors()){
                Label successorsLabel= labels.get(arc.getDestination().getId());
                if(successorsLabel.getCout_realise()>(currentLabel.getCout_realise()+arc.getLength()) && successorsLabel.getMarque()==false){
                    if(successorsLabel.getCout_realise()!=Float.POSITIVE_INFINITY)tas.remove(successorsLabel);
                    else notifyNodeReached(arc.getDestination());
                    successorsLabel.setCout_realise(currentLabel.getCout_realise()+arc.getLength());
                    successorsLabel.setArc_pere(arc);
                    successorsLabel.setSommet_pere(currentLabel.getSommet_courant());
                    tas.insert(successorsLabel);
                }
            }
        }

        // Destination has no predecessor, the solution is infeasible...
        if (!destIsMarked) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {

            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());

            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = labels.get(data.getDestination().getId()).getArc_pere();
            while (arc != null) {
                arcs.add(arc);
                arc = labels.get(arc.getOrigin().getId()).getArc_pere();
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }

        return solution;
    }
    ArrayList<Label> BuildLabel(int nbNodes, Graph graph,Node Destination, Node origin,Mode mode, int maxSpeed){
        ArrayList<Label> labels=new ArrayList<Label>();
        for(int i=0; i<nbNodes;i++){
            if(graph.get(i)!=origin)labels.add(new Label(graph.get(i),false,Float.POSITIVE_INFINITY,null,null));
            else labels.add(new Label(graph.get(i),false,0,null,null));
        }
        return labels;
    }

}
