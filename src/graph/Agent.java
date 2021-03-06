package graph;

import enums.StanE;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Agent {
    private long id;
    private StanE stan = StanE.S;
    private List<Agent> neighbours = new ArrayList<>();
    boolean czyZmianaWStiflera = false;
    boolean czyZmianaWSpreadera = false;
    boolean czyZmianaWIgnoranta = false;

    public Agent(){

    }
    public boolean isNeighbour(long neighbourId){
        return neighbours.stream().anyMatch(e -> neighbourId == e.getId());
    }
    public void removeNeighbour(long neighbourId){
        neighbours.removeIf(e -> neighbourId == e.getId());
    }
    public void addNeighbour(Agent neighbour){
        neighbours.add(neighbour);
    }
    public int getNodeDegree(){
        return neighbours.size();
    }
}