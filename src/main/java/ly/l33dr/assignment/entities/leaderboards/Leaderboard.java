package ly.l33dr.assignment.entities.leaderboards;

import java.util.List;

import ly.l33dr.assignment.entities.statistics.StatName;

/**
 * The leaderboard
 *
 */
public class Leaderboard {

    private StatName statName;
    
    private List<Leader> leaders;
    
    public StatName getStatName() {
        return statName;
    }

    public void setStatName(StatName statName) {
        this.statName = statName;
    }

    public List<Leader> getLeaders() {
        return leaders;
    }

    public void setLeaders(List<Leader> leaders) {
        this.leaders = leaders;
    }
    
}
