package org.insa.graphs.gui.simple;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.graphs.gui.drawing.Drawing;
import org.insa.graphs.gui.drawing.components.BasicDrawing;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;


public class Launch {

    /**
     * Create a new Drawing inside a JFrame an return it.
     * 
     * @return The created drawing.
     * 
     * @throws Exception if something wrong happens when creating the graph.
     */
    public static Drawing createDrawing() throws Exception {
        BasicDrawing basicDrawing = new BasicDrawing();
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("BE Graphes - Launch");
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setSize(new Dimension(800, 600));
                frame.setContentPane(basicDrawing);
                frame.validate();
            }
        });
        return basicDrawing;
    }

    public static void main(String[] args) throws Exception {

        // Visit these directory to see the list of available files on Commetud.
        //final String mapName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
        //final String pathName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Paths/path_fr31insa_rangueil_r2.path";
    	

    	// En local chez Hilal - decommenter
    	final String mapName = "/Users/hilal/Documents/INSA/3A/S2/BE-Graphes/Ressources/insa.mapgr";
    	final String pathName = "/Users/hilal/Documents/INSA/3A/S2/BE-Graphes/Ressources/path_fr31insa_rangueil_r2.path";


        //En local chez JP - decommenter
        //final String mapName = "C:/Users/zaifu/Desktop/INSA 3A/Semestre 2/Graphe/Ressources/Maps/insa.mapgr";
        //final String pathName = "C:/Users/zaifu/Desktop/INSA 3A/Semestre 2/Graphe/Ressources/Paths/path_fr31insa_rangueil_r2.path";
        // Create a graph reader.
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

        final Graph graph = reader.read();

        // Create the drawing:
        final Drawing drawing = createDrawing();

        drawing.drawGraph(graph);

        // Create a PathReader.
        final PathReader pathReader = new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))));
        //final PathReader pathReader2 = new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream(pathName2))));

        final Path path = pathReader.readPath(graph);
        //l Path path2 = pathReader2.readPath(graph);
        System.out.println(path.equals(path));

        // Draw the path.
        drawing.drawPath(path);
        reader.close();
        pathReader.close();
        //drawing.drawPath(path2);
        
        /*
        
        										// TESTS DIJKSTRA
        
        // CAS 1 : Test avec un graphe simple
        // solution disponible sur https://www.maths-cours.fr/methode/algorithme-de-dijkstra-etape-par-etape
		
        System.out.println("TESTS DIJKSTRA ==== CAS 1 : Test avec un graphe simple");
        
        final double EPSILON = 1e-3;
        
        // I- Création du graphe
        
        Graph monGraph; // graphe
        Node[] mesNoeuds; // noeuds
        Arc a01, a02, a03, a04, a12, a23, a25, a35, a34, a45;
        
        RoadInformation RoadInfo = new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null);

        mesNoeuds = new Node[6];
		for (int i = 0; i < mesNoeuds.length; i++) {
			mesNoeuds[i] = new Node(i, null);
		}

		a01 = Node.linkNodes(mesNoeuds[0], mesNoeuds[1], 8, RoadInfo, null);
		a02 = Node.linkNodes(mesNoeuds[0], mesNoeuds[2], 10, RoadInfo, null);
		a03 = Node.linkNodes(mesNoeuds[0], mesNoeuds[3], 5, RoadInfo, null);
		a04 = Node.linkNodes(mesNoeuds[0], mesNoeuds[4], 8, RoadInfo, null);
		a12 = Node.linkNodes(mesNoeuds[1], mesNoeuds[2], 4, RoadInfo, null);
		a23 = Node.linkNodes(mesNoeuds[2], mesNoeuds[3], 8, RoadInfo, null);
		a25 = Node.linkNodes(mesNoeuds[2], mesNoeuds[5], 10, RoadInfo, null);
		a35 = Node.linkNodes(mesNoeuds[3], mesNoeuds[5], 7, RoadInfo, null);
		a34 = Node.linkNodes(mesNoeuds[3], mesNoeuds[4], 2, RoadInfo, null);
		a45 = Node.linkNodes(mesNoeuds[4], mesNoeuds[5], 4, RoadInfo, null);

		System.out.println(" ");

		monGraph = new Graph("ID", "", Arrays.asList(mesNoeuds), null);
		
		for (int i = 0; i<mesNoeuds.length; i++)
		{
			System.out.println("Point de départ : le noeud " + i);
			
			for (int j =0; j<mesNoeuds.length; j++)
			{
				System.out.print("De " + i + " à " + j + " : ");
				if (i==j)
				{
					System.out.print("chemin de longueur nulle : ");
				}
				
				ArcInspector arcInspectorDijkstra = new ArcInspectorFactory().getAllFilters().get(0);
				ShortestPathData data = new ShortestPathData(monGraph, mesNoeuds[i],mesNoeuds[j], arcInspectorDijkstra);

				BellmanFordAlgorithm Bellman = new BellmanFordAlgorithm(data);
				DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
				
				ShortestPathSolution AvecDijkstra = Dijkstra.run();
				ShortestPathSolution AvecBellman = Bellman.run();
				
				//Pas de solution
				
				if (AvecDijkstra.getPath() == null && AvecBellman.getPath() == null) {
					System.out.println("Pas de solution :(");
				}
				else
				{
					float CoutAvecBellman = AvecBellman.getPath().getLength();
					float CoutAvecDijkstra = 3; //AvecDijkstra.getPath().getLength();
					
					if (Math.abs(CoutAvecDijkstra - CoutAvecBellman) < EPSILON)
					{
						System.out.println("les solutions Dijikstra et Bellman sont identiques :) ====  " + CoutAvecDijkstra);
					}
					else
					{
						System.out.println("les solutions Dijikstra et Bellman ne sont pas identiques :( ====  Dijiksta : " + CoutAvecDijkstra + 
								" Bellman : " + CoutAvecBellman);
					}
				}
			}
			
			System.out.println(" ");
		}
		
*/  
        
        
    }

}
