package hibernate;

public class util {
	package com.hibernate.util;

	import java.util.Properties;

	import org.hibernate.SessionFactory;
	import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
	import org.hibernate.cfg.Configuration;
	import org.hibernate.cfg.Environment;
	import org.hibernate.service.ServiceRegistry;

	import com.hibernate.model.Medicamento;
	import com.hibernate.model.SinStock;


	public class HibernateUtil {
		private static SessionFactory sessionFactory;
		public static SessionFactory getSessionFactory() {
			if (sessionFactory == null) {
				try {
					Configuration configuration = new Configuration();
					
					Properties settings = new Properties();
					settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
					settings.put(Environment.URL, "jdbc:mysql://192.168.1.212:3306/farmacia2?useSSL=false");
					settings.put(Environment.USER, "alumno");
					settings.put(Environment.PASS, "alumno");
					settings.put(Environment.SHOW_SQL, "true");
					settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
					settings.put(Environment.HBM2DDL_AUTO, "update");
					
					configuration.setProperties(settings);
					
					configuration.addAnnotatedClass(Medicamento.class);
					configuration.addAnnotatedClass(SinStock.class);
					
					ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
							.applySettings(configuration.getProperties()).build();
					
					sessionFactory = configuration.buildSessionFactory(serviceRegistry);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return sessionFactory;
		}

	}
}
