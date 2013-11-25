package ly.l33dr.assignment.entities.users;

/**
 * Field names of the user table and object
 *
 */
public enum UserField {
    id("id", "id"), firstName("firstName", "first_name"), lastName("lastName", "last_name"), email(
            "email", "email"), userName("userName", "user_name");

    private String javaName;
    private String sqlName;

    UserField(String javaName, String sqlName) {
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
