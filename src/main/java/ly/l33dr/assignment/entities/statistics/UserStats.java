package ly.l33dr.assignment.entities.statistics;

import java.util.Set;

public class UserStats {
    private String userName;

    private Set<Statistic> stats;

    public UserStats(String userName, Set<Statistic> stats) {
        this.userName = userName;
        this.stats = stats;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<Statistic> getStats() {
        return stats;
    }

    public void setStats(Set<Statistic> stats) {
        this.stats = stats;
    }

}
