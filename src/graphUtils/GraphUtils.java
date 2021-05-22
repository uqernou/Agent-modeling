package graphUtils;

import enums.StanE;
import graph.Agent;
import graph.Graph;
import lombok.Data;

import java.util.*;

import static enums.StanE.S;
import static enums.StanE.I;
import static enums.StanE.R;

@Data
public class GraphUtils {

    public static List<Agent> generateErdosRenyi(int nrOfNodes, double probablityOfLink){

        List<Agent> agents = GraphUtils.emptyAgents(nrOfNodes);

        for (int i = 0; i < nrOfNodes; i++) {
            Agent current = new Agent();
            long currentId = agents.get(i).getId();
            /* Filtrowanie po agentach, którzy nie zostali wcześniej dodani do znajomych
             * Sprawdzanie prawdopodobienstwa polaczenia
             * Doddanie sasiada do wylosowanego agenta, oraz tymczasowego
             */
            agents.stream()
                    .filter(f -> !f.getNeighbours().contains(agents.get((int) currentId)))
                    .forEach(e -> {
                        if (Math.random() < probablityOfLink && e.getId() != currentId) {
                            current.addNeighbour(e);
                            e.addNeighbour(agents.get((int) currentId));
                        }
            });

            // Dodanie listy sasiadow dla danego kroku
            agents.get(i).setNeighbours(current.getNeighbours());
        }

        return agents;
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

    public static void zmienStan(List<Agent> agents){
        agents.forEach(e -> {
            if (e.isCzyZmianaWIgnoranta()){
                e.setStan(S);
                e.setCzyZmianaWIgnoranta(false);
            } else if (e.isCzyZmianaWSpreadera()){
                e.setStan(I);
                e.setCzyZmianaWSpreadera(false);
            } else if (e.isCzyZmianaWStiflera()){
                e.setStan(R);
                e.setCzyZmianaWStiflera(false);
            }
        });
    }

    public static boolean czyPodatnyNaZmianeWKroku(Agent agent){
        return !(agent.isCzyZmianaWStiflera() || agent.isCzyZmianaWIgnoranta() || agent.isCzyZmianaWSpreadera());
    }

    public static int getIgnorantsCount(List<Agent> agents){
        return (int) agents.stream()
                .filter(e -> S.equals(e.getStan()))
                .count();
    }
    public static int getSpreadersCount(List<Agent> agents){
        return (int) agents.stream()
                .filter(e -> I.equals(e.getStan()))
                .count();
    }
    public static int getStiflersCount(List<Agent> agents){
        return (int) agents.stream()
                .filter(e -> R.equals(e.getStan()))
                .count();
    }
}
