package modelo;



import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.context.internal.ManagedSessionContext;
import org.hibernate.query.Query;

import persistencias.Usuario;
import persistencias.Carta;
import persistencias.Mazo;

public class GestionBBDD {
	private static SessionFactory sessionFactory;
	// Seccion critica
	private static Object object = new Object();

	// Patron Singleton
	private static GestionBBDD instance;

	// trabajamos con el mismo session factory de la calse

	public GestionBBDD() {
		super();
		this.sessionFactory = null;
		this.sessionFactory = inicializarPoolConexiones();
	}

	private synchronized static void createInstance() {
		if (null == instance) {
			instance = new GestionBBDD();
		}
	}

	public static GestionBBDD getInstance() {
		if (null == instance) {
			createInstance();
		}
		return instance;
	}

	public static void insertarCartasEnBDD(ArrayList<Carta> cartas) {
		Session session = null;
		try {
			for (int i = 0; i < cartas.size(); i++) {
				session = sessionFactory.getCurrentSession();
				session.beginTransaction();
				Carta carta = cartas.get(i);
				carta.setIdCarta(i);

				if (carta.getColor() == null) {
					carta.setColor("");
				}
				session.save(carta);
				session.getTransaction().commit();
			}

		} catch (HibernateException e) {
			e.printStackTrace();
			if (session != null) {
				session.getTransaction().rollback();
			}
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public void cerrarPoolConexiones() {
		try {
			if (sessionFactory != null) {
				sessionFactory.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SessionFactory inicializarPoolConexiones() {
		try {
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			this.sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return sessionFactory;
	}

}
