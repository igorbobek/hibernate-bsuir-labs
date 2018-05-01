import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.HashSet;
import java.util.Set;

public class StudentDao implements DaoInterface<Student, Long> {


    private Session currentSession;
    private Transaction currentTransaction;


    public StudentDao() {
    }


    @SuppressWarnings("unchecked")
    @Override
    public Set<Student> findAll() {
        Set<Student> students = new HashSet<>(getCurrentSession().createQuery("FROM Student").list());
        return students;
    }

    @Override
    public void update(Student entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public void delete(Student entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public Student findById(Long id) {
        Student student = getCurrentSession().find(Student.class, id);
        return student;
    }

    @Override
    public void save(Student entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void deleteAll() {
        findAll().forEach(this::delete);
    }

    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }



    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }


    public void closeCurrentSession() {
        currentSession.close();
    }



    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    private static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );

            throw new ExceptionInInitializerError("SessionFactory failed: " + e);
        }

        return sessionFactory;

    }


    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }


    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }


}
