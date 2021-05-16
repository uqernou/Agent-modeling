package graphUtils;

import graph.Agent;

import java.util.*;

public class GraphUtils {

    public static Map<Agent, List<Agent>> generateErdosRenyi(int nrOfNodes, double probablityOfLink){
        Map<Agent, List<Agent>> graph = new HashMap<>();
        List<Agent> agents = GraphUtils.emptyAgents(nrOfNodes);

        for (int i = 0; i < nrOfNodes; i++) {
            Agent current = new Agent();
            long currentId = agents.get(i).getId();

            agents.forEach(e -> {
                if (Math.random() < probablityOfLink && e.getId() != currentId)
                    current.addNeighbour(e);
            });
            graph.put(agents.get(i), current.getNeighbours());
        }

        return graph;
    }

    public static List<Agent> emptyAgents(int n){
       List<Agent> agents = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            Agent agent = new Agent();
            agent.setId(i);
            agents.add(agent);
        }
        return agents;
    }

}
