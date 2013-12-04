package ly.l33dr.assignment.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import ly.l33dr.assignment.entities.leaderboards.Leader;
import ly.l33dr.assignment.entities.leaderboards.Leaderboard;
import ly.l33dr.assignment.entities.statistics.StatName;
import ly.l33dr.assignment.entities.statistics.Statistic;
import ly.l33dr.assignment.entities.statistics.StatisticField;
import ly.l33dr.assignment.entities.users.User;
import ly.l33dr.assignment.errors.AppError;
import ly.l33dr.assignment.errors.AppErrorType;
import ly.l33dr.assignment.exceptions.BadRequestException;
import ly.l33dr.assignment.utils.SessionFactoryProvider;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 * Implements persistence and validation functionality for the statistic entity
 * 
 */
public class StatisticService {

    private SessionFactory sessionFactory;
    private static Validator validator;

    public StatisticService() {
        sessionFactory = SessionFactoryProvider.getSessionFactory();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Saves a new statistic if the id is null or updates it otherwise
     * 
     * @param statistic
     *            the entity to update/save
     * @return the id of the persisted entity
     */
    public Long persist(Statistic statistic) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        if (statistic.getId() == null) {
            session.save(statistic);
        } else {
            session.update(statistic);
        }
        session.getTransaction().commit();
        session.close();
        return statistic.getId();
    }

    /**
     * Validates the entity
     * 
     * @param stat
     *            the entity to validate
     * @throws BadRequestException
     *             if a validation violation occurs, this exception will contain
     *             a list of all violations wrapped in an AppError
     */
    public void validate(Statistic stat) throws BadRequestException {
        Set<ConstraintViolation<Statistic>> violations = validator.validate(stat);
        if (violations.size() > 0) {
            List<AppError> errors = new ArrayList<AppError>();
            for (ConstraintViolation<Statistic> violation : violations) {
                AppError error = new AppError(AppErrorType.ILLEGAL_VALUE.getErrorCode(),
                        String.format(AppErrorType.ILLEGAL_VALUE.getErrorMsg(),
                                violation.getMessage()));
                errors.add(error);
            }
            throw new BadRequestException(errors);
        }
    }

    /**
     * Gets entities restricted by a single field value (simplified function -
     * usually we want a List of restricting fields)
     * 
     * @param fieldName
     *            the field name that narrows the result set
     * @param value
     *            the value that narrows the result set
     * @return a List of matching entities
     */
    @SuppressWarnings("unchecked")
    public List<Statistic> getByField(StatisticField fieldName, Object value) {
        Session session = sessionFactory.openSession();
        List<Statistic> statistics;
        if (value instanceof User) {
            User user = (User) value;
            statistics = (List<Statistic>) session.createCriteria(Statistic.class)
                    .createCriteria("user").add(Restrictions.eq("id", user.getId())).list();

        } else {
            statistics = (List<Statistic>) session.createCriteria(Statistic.class)
                    .add(Restrictions.eq(fieldName.getJavaName(), value)).list();
        }
        return statistics;
    }

    /**
     * Gets a List of rows that build the leaderboard. The list is specific to a
     * stat, grouped by user name and sorted descending.
     * 
     * @param statName
     *            the stat to get the leaderboard for
     * @return the leaderboard
     */
    @SuppressWarnings("unchecked")
    public Leaderboard getLeaderboard(StatName statName) {
        Session session = sessionFactory.openSession();
        // @formatter:off
        String sql = "SELECT s.statName, s.value, s.user.userName "
                + "FROM Statistic s "
                + "WHERE s.statName = :statName "
                + "GROUP BY s.user.userName, s.statName "
                + "ORDER BY value DESC";
        // @formatter:on
        Iterator<Object> results = session.createQuery(sql).setParameter("statName", statName)
                .list().iterator();

        int rank = 1;
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setStatName(statName);
        List<Leader> leaders = new ArrayList<Leader>();
        while (results.hasNext()) {
            Object[] row = (Object[]) results.next();
            Leader leader = new Leader();
            leader.setValue(Integer.parseInt(row[1].toString()));
            leader.setUserName(row[2].toString());
            leader.setRank(rank++);
            leaders.add(leader);
        }
        leaderboard.setLeaders(leaders);
        return leaderboard;
    }

}
