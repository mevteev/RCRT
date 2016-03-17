package com.vtbcapital.itops.rcrt;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class HibernateUtil {
	
	private static final SessionFactory sessionFactory = buildSessionFactory();
	  
    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
        	
    		Configuration conf = new Configuration();
    		
    		conf.configure();
    		
    		ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).
    				build();
    		
    		return conf.buildSessionFactory(sr);        	
        	
            //return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
  
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
  
    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
    
    @Transactional
    public static void initialize(Object obj) {
    	Session session = getSessionFactory().openSession();
    	Hibernate.initialize(obj);
    	session.close();
    }
    
    @SuppressWarnings("unchecked")
	public static List<Object[]> select(String sql) {
    	List<Object[]> res;
		Session session = getSessionFactory().openSession();
		res = session.createQuery(sql).list();
    	session.close();
    	
    	return res;
    }
    
    public static boolean saveElement(Object object) {
		Session session = getSessionFactory().openSession();
		  
        session.beginTransaction();
 
        session.saveOrUpdate(object);
 
        session.getTransaction().commit(); 
        
        session.close();
        
        return true;
    }
    
    public static boolean saveElement(String className, Object object) {
		Session session = getSessionFactory().openSession();
		  
        session.beginTransaction();
 
        session.saveOrUpdate(className, object);
 
        session.getTransaction().commit(); 
        
        session.close();
        
        return true;
    }

}
