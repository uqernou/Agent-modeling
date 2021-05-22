package graph;

import enums.StanE;
import graphUtils.GraphUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.List;

import static enums.StanE.S;
import static enums.StanE.I;
import static enums.StanE.R;

@Data
@AllArgsConstructor
public class Graph {

    private List<Agent> agents = new ArrayList<>();
    private List<Integer> ignoratns = new ArrayList<>();
    private List<Integer> spreaders = new ArrayList<>();
    private List<Integer> strfilers = new ArrayList<>();
    private double spreaderProb, stiflerProb, ignorantProb;
    private Random rand = new Random();


    public Graph(){
    }
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        Graph graph = ErdosRenyi(2000, 0.4);
        graph.setSpreaderProb(0.05123);
        graph.setIgnorantProb(0.2123);
        graph.setStiflerProb(0.025123);
        graph.getAgents().get(graph.rand.nextInt(graph.agents.size())).setStan(I);
        PrintWriter writer = new PrintWriter("data.txt", "UTF-8");
        for (int i = 0 ; i < 10000; i++){
            graph.SIR();
            writer.println(graph.ignoratns.get(i)+" "+ graph.spreaders.get(i)+ " " + graph.strfilers.get(i));
            System.out.println("Step: "+i+"  Ignorants: " + graph.ignoratns.get(i)+"  Spreader: "+ graph.spreaders.get(i)+ "  Stifler: " + graph.strfilers.get(i));
            //System.out.println(graph.ignoratns.get(i)+" "+ graph.spreaders.get(i)+ " " + graph.strfilers.get(i));

        }
        writer.close();
        System.out.println("Done");
    }

    public static Graph ErdosRenyi(int nrOfNodes, double probablityOfLink){
        Graph graph = new Graph();
        graph.setAgents(GraphUtils.generateErdosRenyi(nrOfNodes, probablityOfLink));
        return graph;
    }

    public void SIR(){
        int ignorants = GraphUtils.getIgnorantsCount(agents);
        int spreaders = GraphUtils.getSpreadersCount(agents);
        int striflers = GraphUtils.getStiflersCount(agents);

        agents.forEach(e -> {
            int randomNeigbourId = rand.nextInt(e.getNeighbours().size());
            StanE curretAgentStan = e.getStan();
            StanE neigbourStan = e.getNeighbours().get(randomNeigbourId).getStan();
            switch (curretAgentStan) {
                //Wylosowany agent Ignorant
                case S:
                     if (I.equals(neigbourStan)){
                        if(Math.random() < spreaderProb && GraphUtils.czyPodatnyNaZmianeWKroku(e)){
                            //Jesli w prawdopodobienstwie i aktualny agent podatny to zmiana  S + I -> I + I
                            e.setCzyZmianaWSpreadera(true);
                            add(spreaders);
                            remove(ignorants);
                        }
                    }
                    break;
                //Wylosowany agent Spreader
                case I:
                    if(S.equals(neigbourStan)){
                        if(Math.random() < spreaderProb && GraphUtils.czyPodatnyNaZmianeWKroku(e.getNeighbours().get(randomNeigbourId))){
                            //Jesli w prawdopodobienstwie i sasiad agenta podatny to zmiana   I + S -> I + I
                            e.getNeighbours().get(randomNeigbourId).setCzyZmianaWSpreadera(true);
                            add(spreaders);
                            remove(ignorants);
                        }
                    } else if (I.equals(neigbourStan)){
                        if(Math.random() < stiflerProb && GraphUtils.czyPodatnyNaZmianeWKroku(e)){
                            //Jesli w prawdopodobienstwie i agenta podatny to zmiana          I + I -> R + I
                            e.setCzyZmianaWStiflera(true);
                            add(striflers);
                            remove(spreaders);
                        }
                    } else if (R.equals(neigbourStan)){
                        if(Math.random() < stiflerProb && GraphUtils.czyPodatnyNaZmianeWKroku(e)){
                            //Jesli w prawdopodobienstwie i agenta podatny to zmiana          I + R -> R + R
                            e.setCzyZmianaWStiflera(true);
                            add(striflers);
                            remove(spreaders);
                        }
                    }
                    break;
                //Wylosowany agent Stifler
                case R:
                     if (I.equals(neigbourStan)){
                        if(Math.random() < stiflerProb && GraphUtils.czyPodatnyNaZmianeWKroku(e.getNeighbours().get(randomNeigbourId))){
                            //Jesli w prawdopodobienstwie i agenta podatny to zmiana          R + I -> R + R
                            e.getNeighbours().get(randomNeigbourId).setCzyZmianaWStiflera(true);
                            add(striflers);
                            remove(spreaders);
                        }
                    }
                    break;
            }
        });
        this.ignoratns.add(ignorants);
        this.spreaders.add(spreaders);
        this.strfilers.add(striflers);

        GraphUtils.zmienStan(agents);
    }
    public void add(int a){
        a++;
    }
    public void remove(int a){
        a--;
    }
}
