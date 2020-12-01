package pl.polsl.karolinadziubek;

import javax.swing.*;

public class GraphWindow extends JFrame {
    public Graph graph;
    public GraphWindow(){
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
