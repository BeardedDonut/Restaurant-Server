package com.readlearncode.dukesbookshop.restserver;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.ejb.Startup;
import javax.ejb.Stateless;

/**
 * Created by navid on 1/7/18.
 */
@Startup
@Stateless
public class DatabaseConfig {

    private static Session session;

    public DatabaseConfig() {
    }

    private static void createSessionFactory() {
        System.out.println("Creating Database Connection");
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        SessionFactory newSessionFactory = configuration
                .buildSessionFactory(builder.build());
        session = newSessionFactory.openSession();
    }

    public static Session getSession() {

        if (session == null) {
            createSessionFactory();
        }

        return session;
    }

    public static void closeSession() {

    }
}
