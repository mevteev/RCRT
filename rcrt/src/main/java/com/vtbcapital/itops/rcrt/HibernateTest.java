package com.vtbcapital.itops.rcrt;

import java.util.Date;
import java.util.List;

import org.hibernate.*;

public class HibernateTest {

	public static void main(String[] args) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		  
        session.beginTransaction();
 
        Applications app = new Applications();
        
        app.setDate(new Date(2016,2,1));
		app.setInterval(6);
		app.setName("REC7");
		
        session.save(app);
 
        session.getTransaction().commit();
 
        Query q = session.createQuery("From Applications ");
                 
        List<Applications> resultList = q.list();
        System.out.println("num of applications:" + resultList.size());
        for (Applications next : resultList) {
            System.out.println("next aplpications: " + next.getName());
        }


	}
	

}
