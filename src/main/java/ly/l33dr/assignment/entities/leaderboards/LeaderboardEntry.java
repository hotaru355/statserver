package ly.l33dr.assignment.entities.leaderboards;

import ly.l33dr.assignment.entities.statistics.StatName;

/**
 * A row in the leaderboard
 *
 */
public class LeaderboardEntry {
    private String userName;

    private StatName statName;

    private int value;

    private int rank;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public StatName getStatName() {
        return statName;
    }

    public void setStatName(StatName statName) {
        this.statName = statName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

}
