package org.insa.graphs.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Class representing a path between nodes in a graph.
 * </p>
 * 
 * <p>
 * A path is represented as a list of {@link Arc} with an origin and not a list
 * of {@link Node} due to the multi-graph nature (multiple arcs between two
 * nodes) of the considered graphs.
 * </p>
 *
 */
public class Path {

    /**
     * Create a new path that goes through the given list of nodes (in order),
     * choosing the fastest route if multiple are available.
     * 
     * @param graph Graph containing the nodes in the list.
     * @param nodes List of nodes to build the path.
     * 
     * @return A path that goes through the given list of nodes.
     * 
     * @throws IllegalArgumentException If the list of nodes is not valid, i.e. two
     *         consecutive nodes in the list are not connected in the graph.
     * 
     *  Need to be implemented your gueul.
     */
    public static Path createFastestPathFromNodes(Graph graph, List<Node> nodes) throws IllegalArgumentException {
        List<Arc> arcs = new ArrayList<Arc>();
        for(int i=0;i<nodes.size()-1;i++){
            Node nodeA=nodes.get(i);
            Node nodeB= nodes.get(i+1);
            List<Arc> arcsNext= nodeA.getSuccessors();
            Arc bestArc=null;
            for(Arc curentArc: arcsNext){
                if(curentArc.getDestination()==nodeB){
                    if(bestArc==null){
                        bestArc=curentArc;
                    }
                    else{
                        if(bestArc.getMinimumTravelTime()>curentArc.getMinimumTravelTime()){
                            bestArc=curentArc;
                        }
                    }
                }
            } 
            if(bestArc==null){
                throw new IllegalArgumentException("Les nodes ne sont pas relié");
            }
            arcs.add(bestArc);
        }
        Path path;
        if(arcs.isEmpty()&&!nodes.isEmpty())path =new Path(graph, nodes.get(0)); 
        else if(arcs.isEmpty()) path =new Path(graph, arcs); 
        else path =new Path(graph, arcs);
        return path;
    }

    /**
     * Create a new path that goes through the given list of nodes (in order),
     * choosing the shortest route if multiple are available.
     * 
     * @param graph Graph containing the nodes in the list.
     * @param nodes List of nodes to build the path.
     * 
     * @return A path that goes through the given list of nodes.
     * 
     * @throws IllegalArgumentException If the list of nodes is not valid, i.e. two
     *         consecutive nodes in the list are not connected in the graph.
     * 
     *  Need to be implemented tour vous vous.
     */
    public static Path createShortestPathFromNodes(Graph graph, List<Node> nodes) throws IllegalArgumentException{
        List<Arc> arcs = new ArrayList<Arc>();
        for(int i=0;i<nodes.size()-1;i++){
            Node nodeA=nodes.get(i);
            Node nodeB= nodes.get(i+1);
            List<Arc> arcsNext= nodeA.getSuccessors();
            Arc bestArc=null;
            for(Arc curentArc: arcsNext){
                if(curentArc.getDestination()==nodeB||curentArc.getOrigin()==nodeB){
                    if(bestArc==null){
                        bestArc=curentArc;
                    }
                    else{
                        if(bestArc.getLength()>curentArc.getLength()){
                            bestArc=curentArc;
                        }
                    }
                }
            } 
            if(bestArc==null){
                throw new IllegalArgumentException("Les nodes ne sont pas relié");
            }
            arcs.add(bestArc);
        }
        Path path;
        if(arcs.isEmpty()&&!nodes.isEmpty())path =new Path(graph, nodes.get(0)); 
        else if(arcs.isEmpty()) path =new Path(graph, arcs); 
        else path =new Path(graph, arcs);
        return path;
    }

    /**
     * Concatenate the given paths.
     * 
     * @param paths Array of paths to concatenate.
     * 
     * @return Concatenated path.
     * 
     * @throws IllegalArgumentException if the paths cannot be concatenated (IDs of
     *         map do not match, or the end of a path is not the beginning of the
     *         next).
     */
    public static Path concatenate(Path... paths) throws IllegalArgumentException {
        if (paths.length == 0) {
            throw new IllegalArgumentException("Cannot concatenate an empty list of paths.");
        }
        final String mapId = paths[0].getGraph().getMapId();
        for (int i = 1; i < paths.length; ++i) {
            if (!paths[i].getGraph().getMapId().equals(mapId)) {
                throw new IllegalArgumentException(
                        "Cannot concatenate paths from different graphs.");
            }
        }
        ArrayList<Arc> arcs = new ArrayList<>();
        for (Path path: paths) {
            arcs.addAll(path.getArcs());
        }
        Path path = new Path(paths[0].getGraph(), arcs);
        if (!path.isValid()) {
            throw new IllegalArgumentException(
                    "Cannot concatenate paths that do not form a single path.");
        }
        return path;
    }

    // Graph containing this path.
    private final Graph graph;

    // Origin of the path
    private final Node origin;

    // List of arcs in this path.
    private final List<Arc> arcs;

    /**
     * Create an empty path corresponding to the given graph.
     * List of nodes to build the path
     * @param graph Graph containing the path.
     */
    public Path(Graph graph) {
        this.graph = graph;
        this.origin = null;
        this.arcs = new ArrayList<>();
    }

    /**
     * Create a new path containing a single node.
     * 
     * @param graph Graph containing the path.
     * @param node Single node of the path.
     */
    public Path(Graph graph, Node node) {
        this.graph = graph;
        this.origin = node;
        this.arcs = new ArrayList<>();
    }

    /**
     * Create a new path with the given list of arcs.
     * 
     * @param graph Graph containing the path.
     * @param arcs Arcs to construct the path.
     */
    public Path(Graph graph, List<Arc> arcs) {
        this.graph = graph;
        this.arcs = arcs;
        this.origin = arcs.size() > 0 ? arcs.get(0).getOrigin() : null;
    }

    /**
     * @return Graph containing the path.
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * @return First node of the path.
     */
    public Node getOrigin() {
        return origin;
    }

    /**
     * @return Last node of the path.
     */
    public Node getDestination() {
        return arcs.get(arcs.size() - 1).getDestination();
    }

    /**
     * @return List of arcs in the path.
     */
    public List<Arc> getArcs() {
        return Collections.unmodifiableList(arcs);
    }

    /**
     * Check if this path is empty (it does not contain any node).
     * 
     * @return true if this path is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.origin == null;
    }

    /**
     * Get the number of <b>nodes</b> in this path.
     * 
     * @return Number of nodes in this path.
     */
    public int size() {
        return isEmpty() ? 0 : 1 + this.arcs.size();
    }

    /**
     * Check if this path is valid.
     * 
     * A path is valid if any of the following is true:
     * <ul>
     * <li>it is empty;</li>
     * <li>it contains a single node (without arcs);</li>
     * <li>the first arc has for origin the origin of the path and, for two
     * consecutive arcs, the destination of the first one is the origin of the
     * second one.</li>
     * </ul>
     * 
     * @return true if the path is valid, false otherwise.
     * 
     * Need to be implemented.
     */
    public boolean isValid() {
        List<Arc> list_arcs = this.getArcs();

        // empty
        boolean empty = this.isEmpty();
        
        // single_node
        boolean single_node = (this.size() == 1);
        
        //first_arc_origin_ok
        boolean first_arc_origin_ok = false;
        if (!this.isEmpty() && !list_arcs.isEmpty())
        {
            first_arc_origin_ok = (this.getOrigin() == list_arcs.get(0).getOrigin());
        }

        
        //consecutif_ok
        boolean consecutif_ok = true;
        if (this.isEmpty() == false && list_arcs.size()>2){
            for (int i = 0; i<list_arcs.size()-1; i++){
                Arc premier_arc = list_arcs.get(i);
                Arc deuxieme_arc = list_arcs.get(i+1);
                if (premier_arc.getDestination() != deuxieme_arc.getOrigin()){
                    consecutif_ok = false;
                }
            }
        }
        
        return (empty || single_node || (first_arc_origin_ok && consecutif_ok));
    }

    /**
     * Compute the length of this path (in meters).
     * 
     * @return Total length of the path (in meters).
     * 
     * 
     */
    public float getLength() {
        float length=0;
        for(Arc currentArc:this.arcs){
            length+=currentArc.getLength();
        }
        return length;
    }

    /**
     * Compute the time required to travel this path if moving at the given speed.
     * 
     * @param speed Speed to compute the travel time.
     * 
     * @return Time (in seconds) required to travel this path at the given speed (in
     *         kilometers-per-hour).
     * 
     * Need to be implemented.
     */
    public double getTravelTime(double speed) {
        double travelTime=0;
        for(Arc currentArc:this.arcs){
            travelTime+=currentArc.getTravelTime(speed);
        }
        return travelTime;
    }

    /**
     * Compute the time to travel this path if moving at the maximum allowed speed
     * on every arc.
     * 
     * @return Minimum travel time to travel this path (in seconds).
     * 
     * Need to be implemented.
     */
    public double getMinimumTravelTime() {
        double travelTime=0;
        for(Arc currentArc:this.arcs){
            travelTime+=currentArc.getMinimumTravelTime();
        }
        return travelTime;
    }

    public boolean equals(Path path){ //ici, on compare les adresse, mais ce n'est pas génant, car on utilise souvent le même graphique d'origine (donc même adresse)
        if(!this.graph.equals(path.graph))return false; 
        if(!this.origin.equals(path.origin))return false;
        for(int i =0; i<this.arcs.size();i+=1)if(!this.arcs.get(i).equals(path.arcs.get(i)))return false;
        return true;
    } 

}
