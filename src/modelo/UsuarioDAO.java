package modelo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import persistencias.Usuario;

import org.hibernate.HibernateException;

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
                    .setParameter("nombreUsuario", nombreUsuario)
                    .uniqueResult();
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
            //poner num de draft a =
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
}
