package org.mehaexample.asdDemo.dao.alignprivate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.model.alignprivate.Projects;

import java.util.List;

public class ProjectsDao {
    private static SessionFactory factory;
    private static Session session;

    /**
     * Default constructor.
     * it will check the Hibernate.cfg.xml file and load it
     * next it goes to all table files in the hibernate file and loads them.
     */
    public ProjectsDao() {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Find a Project by the Project Id.
     * This method searches the project from the private database.
     *
     * @param projectId project Id in private database.
     * @return Project if found.
     */
    public Projects getProjectById(int projectId) {
        session = factory.openSession();
        org.hibernate.query.Query query = session.createQuery(
                "FROM Projects WHERE projectId = :projectId");
        query.setParameter("projectId", projectId);
        List<Projects> listOfProjects = query.list();
        if (listOfProjects.size() == 0)
            return null;
        Projects project = listOfProjects.get(0);
        session.close();
        return project;
    }

    /**
     * Find project records of a student in private DB.
     *
     * @param neuId the neu Id of a student; not null.
     * @return List of Projects.
     */
    public List<Projects> getProjectsByNeuId(String neuId) {
        session = factory.openSession();
        org.hibernate.query.Query query = session.createQuery(
                "FROM Projects WHERE neuId = :neuId");
        query.setParameter("neuId", neuId);
        List<Projects> listOfProjects = query.list();
        session.close();
        return listOfProjects;
    }

    /**
     * Create a project in the private database.
     *
     * @param project the project object to be created; not null.
     * @return newly created project if success. Otherwise, return null;
     */
    public Projects createProject(Projects project) {
        session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            System.out.println("saving project in Projects table");
            session.save(project);
            tx.commit();
        } catch (HibernateException e) {
            System.out.println("HibernateException: " + e);
            if (tx != null) tx.rollback();
            project = null;
        } finally {
            session.close();
        }

        return project;
    }

    /**
     * Delete a project in the private database.
     *
     * @param projectId the project Id to be deleted.
     * @return true if project is deleted, false otherwise.
     */
    public boolean deleteProjectById(int projectId) {
        boolean deleted = false;
        Projects project = getProjectById(projectId);
        if (project != null) {
            session = factory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                System.out.println("Deleting project with id = " + project.getProjectId());
                session.delete(project);
                tx.commit();
                deleted = true;
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }

        return deleted;
    }

    public boolean deleteProjectsByNeuId(String neuId) {
        Transaction tx = null;
        boolean deleted = false;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            org.hibernate.query.Query query = session.createQuery("DELETE FROM Projects " +
                    "WHERE neuId = :neuId ");
            query.setParameter("neuId", neuId);
            query.executeUpdate();
            tx.commit();
            deleted = true;
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return deleted;
    }

    /**
     * Update a project in the private DB.
     *
     * @param project project object; not null.
     * @return true if the project is updated, false otherwise.
     */
    public boolean updateProject(Projects project) {
        boolean updated = false;

        if (getProjectById(project.getProjectId()) != null) {
            session = factory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                System.out.println("updating project in Projects table...");
                session.saveOrUpdate(project);
                tx.commit();
                updated = true;
            } catch (HibernateException e) {
                System.out.println("HibernateException: " + e);
                if (tx != null) tx.rollback();
            } finally {
                session.close();
            }
        }

        return updated;
    }
}
