package ly.l33dr.assignment.services;

import ly.l33dr.assignment.entities.users.UserField;
import ly.l33dr.assignment.entities.users.User;
import ly.l33dr.assignment.utils.SessionFactoryProvider;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 * Implements persistence functionality for the user entity
 * 
 */
public class UserService {

    private SessionFactory sessionFactory;

    public UserService() {
        sessionFactory = SessionFactoryProvider.getSessionFactory();
    }

    public User getByField(UserField fieldName, Object value) {
        Session session = sessionFactory.openSession();
        User user = (User) session.createCriteria(User.class)
                .add(Restrictions.eq(fieldName.getJavaName(), value)).uniqueResult();
        return user;
    }
}
