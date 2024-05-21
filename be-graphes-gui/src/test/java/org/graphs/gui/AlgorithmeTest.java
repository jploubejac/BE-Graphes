package org.graphs.gui;

import java.io.IOException;
import java.util.Random;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.io.BinaryGraphReader;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;


import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AlgorithmeTest { 
    private static final int nbIteration=10;
    private Graph graphMap[]; 
    
    @Test
    public void testLengthAndTime() throws IOException{
        ArcInspector mode;
        String map[]=new String[3];
        graphMap=new Graph[3];
        map[0]=new String("C:/Users/zaifu/Desktop/INSA 3A/Semestre 2/Graphe/Resources/Maps/bordeaux.mapgr");
        map[1]=new String("C:/Users/zaifu/Desktop/INSA 3A/Semestre 2/Graphe/Resources/Maps/toulouse.mapgr");
        map[2]=new String("C:/Users/zaifu/Desktop/INSA 3A/Semestre 2/Graphe/Resources/Maps/guadeloupe.mapgr");
        for(int i=0; i<3; i++)graphMap[i]=new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(map[i])))).read();
        
        //ArcInspectorFactory.getAllFilters().get(0)->Tous les moyens de locomotion (en longueur)
        //ArcInspectorFactory.getAllFilters().get(1)->Voiture (en longueur)
        //ArcInspectorFactory.getAllFilters().get(2)->Voiture (en temps)
        //ArcInspectorFactory.getAllFilters().get(3)->Piéton (en temps)
        for(int i=0; i<4; i++){
            mode =ArcInspectorFactory.getAllFilters().get(i);
            test(mode);
        }

    }
    public void test(ArcInspector mode){
        Random rand = new Random();

        DijkstraAlgorithm bordeaux,toulouse,guadeloupe;
        

        BellmanFordAlgorithm bordeauxBF,toulouseBF,guadeloupeBF;

        ShortestPathData bordeauxData,toulouseData, guadeloupeData;

        ShortestPathSolution bordeauxOpti, toulouseOpti, guadeloupeOpti, bordeauxAlgoSo,toulouseAlgoSo, guadeloupeAlgoSo;

        for(int i=0; i<nbIteration; i++){
            bordeauxData= new ShortestPathData(graphMap[0], graphMap[0].get(rand.nextInt(graphMap[0].size())), graphMap[0].get(rand.nextInt(graphMap[0].size())),mode);
            toulouseData= new ShortestPathData(graphMap[1], graphMap[1].get(rand.nextInt(graphMap[1].size())), graphMap[1].get(rand.nextInt(graphMap[1].size())),mode);
            guadeloupeData= new ShortestPathData(graphMap[2], graphMap[2].get(rand.nextInt(graphMap[2].size())), graphMap[2].get(rand.nextInt(graphMap[2].size())),mode);

            bordeaux =BuildAlgorithm(bordeauxData);
            toulouse =BuildAlgorithm(toulouseData);
            guadeloupe =BuildAlgorithm(guadeloupeData);
            
            bordeauxBF=new BellmanFordAlgorithm(bordeauxData);
            toulouseBF=new BellmanFordAlgorithm(toulouseData);
            guadeloupeBF=new BellmanFordAlgorithm(guadeloupeData);

            bordeauxOpti=bordeauxBF.run();
            bordeauxAlgoSo=bordeaux.run();
            assertEquals(true,bordeauxOpti.getStatus()==bordeauxAlgoSo.getStatus());
            if(bordeauxOpti.getStatus()==Status.FEASIBLE)assertEquals(true,bordeauxOpti.getPath().equals(bordeauxAlgoSo.getPath()));

            toulouseOpti=toulouseBF.run();
            toulouseAlgoSo=toulouse.run();
            assertEquals(true,toulouseOpti.getStatus()==toulouseAlgoSo.getStatus());
            if(toulouseOpti.getStatus()==Status.FEASIBLE)assertEquals(true,toulouseOpti.getPath().equals(toulouseAlgoSo.getPath()));

            guadeloupeOpti=guadeloupeBF.run();
            guadeloupeAlgoSo=guadeloupe.run();
            assertEquals(true,guadeloupeOpti.getStatus()==guadeloupeAlgoSo.getStatus());
            if(guadeloupeOpti.getStatus()==Status.FEASIBLE)assertEquals(true,guadeloupeOpti.getPath().equals(guadeloupeAlgoSo.getPath()));
        }
        
    }
    
    public DijkstraAlgorithm BuildAlgorithm(ShortestPathData data){
        return new DijkstraAlgorithm(data);
    }
}
