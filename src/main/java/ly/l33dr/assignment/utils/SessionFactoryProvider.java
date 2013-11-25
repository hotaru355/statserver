package ly.l33dr.assignment.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Provides the Hibernate session factory
 *
 */
public class SessionFactoryProvider {
    private static SessionFactory sessionFactory;
    
    private SessionFactoryProvider() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
