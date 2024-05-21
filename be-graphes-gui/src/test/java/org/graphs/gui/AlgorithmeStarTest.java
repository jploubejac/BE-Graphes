package org.graphs.gui;

import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;

public class AlgorithmeStarTest extends AlgorithmeTest {
    public DijkstraAlgorithm BuildAlgorithm(ShortestPathData data){
        return new AStarAlgorithm(data);
    }
    
}
