package modelo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import persistencias.Carta;
import persistencias.Coleccion;

public class ProcesarColeccionDAO {
	private SessionFactory sessionFactory;

	public ProcesarColeccionDAO() {
		this.sessionFactory = inicializarPoolConexiones();
	}

	public void insertarCartasEnBDD(ArrayList<Carta> cartas) {
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

			cerrarPoolConexiones();
		}
	}
	
	

	public List<Coleccion> selectColeccion(String nombreColeccion) {
		Session session = null;
		List<Coleccion> colecciones= new ArrayList<Coleccion>();

		try {
			session = sessionFactory.openSession();
			Query<Coleccion> query = session.createQuery("FROM Coleccion WHERE nombre = :coleccion", Coleccion.class)
					.setParameter("coleccion", nombreColeccion);
			colecciones = query.list();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return colecciones;
	}
	
	public void insertarColeccion(Coleccion coleccion) {
        Session session = null;
        
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(coleccion);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
            	session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
	
	public void borrarColeccionesPorNombre(String nombreColeccion) {
	    Session session = null;

	    try {
	        session = sessionFactory.getCurrentSession();
	        session.beginTransaction();
	        Query query = session.createQuery("DELETE FROM Coleccion WHERE nombre = :nombre")
	                             .setParameter("nombre", nombreColeccion);
	        query.executeUpdate();
	        session.getTransaction().commit();
	    } catch (Exception e) {
	         if (session.getTransaction() != null) {
	             session.getTransaction().rollback();
	         }
	        e.printStackTrace();
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }
	}


	public ArrayList<Carta> obtenerCartasColeccion(String nombreColeccion) {
		Session session = null;
		ArrayList<Carta> cartasColeccion = new ArrayList<Carta>();

		try {
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();

			Query query = session.createQuery("FROM Carta WHERE coleccion.nombre=:nombreColeccion").setParameter("nombreColeccion", nombreColeccion);
			cartasColeccion = (ArrayList<Carta>) query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				session.getTransaction().rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return cartasColeccion;

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

	private SessionFactory inicializarPoolConexiones() {
		try {
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			return configuration.buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
