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
    	//final String mapName = "/Users/hilal/Documents/INSA/3A/S2/BE-Graphes-local/Ressources/insa.mapgr";
    	//final String pathName = "/Users/hilal/Documents/INSA/3A/S2/BE-Graphes-local/Ressources/path_fr31insa_rangueil_r2.path";


        //En local chez JP - decommenter
        final String mapName = "C:/Users/zaifu/Desktop/INSA 3A/Semestre 2/Graphe/Resources/Maps/insa.mapgr";
        final String pathName = "C:/Users/zaifu/Desktop/INSA 3A/Semestre 2/Graphe/Resources/Paths/path_fr31_insa_bikini_canal.path";
        final String pathName2 = "C:/Users/zaifu/Desktop/INSA 3A/Semestre 2/Graphe/Resources/Paths/path_fr31_insa_bikini_motorcar.path";
        //final String mapName = "C:/Users/zaifu/Desktop/INSA 3A/Semestre 2/Graphe/Resources/Maps/belgium.mapgr";
        //final String pathName = "C:/Users/zaifu/Desktop/INSA 3A/Semestre 2/Graphe/Resources/Paths/path_be_173101_302442.path";
        // Create a graph reader.
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

        final Graph graph = reader.read();

        // Create the drawing:
        final Drawing drawing = createDrawing();

        drawing.drawGraph(graph);

        // Create a PathReader.
        final PathReader pathReader = new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))));
        final PathReader pathReader2 = new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream(pathName2))));

        final Path path = pathReader.readPath(graph);
        final Path path2 = pathReader2.readPath(graph);
        System.out.println(path.equals(path));

        // Draw the path.
        drawing.drawPath(path);
        drawing.drawPath(path2);
        
    }

}
