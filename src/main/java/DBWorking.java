import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class DBWorking {
    private static final SessionFactory sessionFactory;

    // ЭТОТ КЛАСС НЕ НУЖЕН. ЗДЕСЬ МЕТОДЫ ДЛЯ HIBERNATE. Я ИСПОЛЬЗУЮ JPA
    static {
        //sessionFactory = new Configuration().configure("backup.xml").buildSessionFactory();
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public static List<Entry> findAll(){
        try(Session session = sessionFactory.openSession()){
            Query<Entry> query = session.createQuery("from Entry",Entry.class);

            return query.list();
        }
    }

    public static void save(Entry entry){
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.save(entry);
            transaction.commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
