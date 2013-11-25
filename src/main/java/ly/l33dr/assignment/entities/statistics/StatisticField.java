package ly.l33dr.assignment.entities.statistics;

/**
 * Field names of the statistic table and object
 *
 */
public enum StatisticField {
    id("id", "id"), user("user", "user_id"), statName("statName", "name"), value("value", "value"), created_at(
            "createdAt", "created_at");

    private String javaName;
    private String sqlName;

    StatisticField(String javaName, String sqlName) {
        this.javaName = javaName;
        this.sqlName = sqlName;
    };

    public String getJavaName() {
        return javaName;
    }

    public String getSqlName() {
        return sqlName;
    }
}
