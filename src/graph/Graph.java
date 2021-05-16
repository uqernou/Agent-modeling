package graph;

import graphUtils.GraphUtils;
import lombok.Data;

import java.awt.*;
import java.util.*;
import java.util.List;

@Data
public class Graph {

    private Map<Agent, List<Agent>> graph;

    public Graph(int nodes, double probablilty){
        graph = GraphUtils.generateErdosRenyi(nodes, probablilty);
    }


    public static void main(String[] args) {
        Graph graph = new Graph(256, 1);
        System.out.println("Done");
    }

}
