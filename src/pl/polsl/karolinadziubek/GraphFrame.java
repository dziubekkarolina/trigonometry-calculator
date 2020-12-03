package pl.polsl.karolinadziubek;

import javax.swing.*;

/**
 * Represents frame that contains panel in which graph is displayed
 */
public class GraphFrame extends JFrame {
    /**
     * Panel element in which graph is displayed
     */
    public Graph graph;

    /**
     * Default parameterless constructor
     */
    public GraphFrame(){
        super("Graph");
        graph = new Graph();
        add(graph);
        setResizable(false);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
