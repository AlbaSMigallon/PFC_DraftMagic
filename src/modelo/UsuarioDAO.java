package modelo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import persistencias.Usuario;

import org.hibernate.HibernateException;

/*
 * Clase Data Access Object de Usuario. Se utiliza para separar la logica de acceso a datos del resto de la aplicacion
 */
public class UsuarioDAO {
	private SessionFactory sessionFactory;

	public UsuarioDAO() {
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}
	}

	public Usuario obtenerUsuarioPorNombre(String nombreUsuario) {
		Session session = null;
		Usuario usuario = null;
		try {
			session = sessionFactory.openSession();
			usuario = (Usuario) session.createQuery("FROM Usuario WHERE nombre = :nombreUsuario")
					.setParameter("nombreUsuario", nombreUsuario).uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return usuario;
	}

	public void guardarUsuario(Usuario usuario) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			// poner num de draft
			usuario.setNumDraft(0);
			session.save(usuario);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public void updateUsuario(Usuario usuario) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();

			Usuario usuarioExistente = session.get(Usuario.class, usuario.getIdUsuario());
			if (usuarioExistente != null) {
				// Actualizar el numero de drafts
				usuarioExistente.setNumDraft(usuarioExistente.getNumDraft() + 1);
				session.update(usuarioExistente);
			}

			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
