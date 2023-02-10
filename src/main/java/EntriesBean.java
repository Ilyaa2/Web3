import org.primefaces.PrimeFaces;

import javax.enterprise.inject.Model;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Model
@ApplicationScoped
public class EntriesBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String persistenceUnit = "StudsPU";

    private Entry entry;
    private List<Entry> list;


    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction transaction;


    public EntriesBean(){
        entry = new Entry();
        list = new ArrayList<>();

        connection();
        loadEntries();
    }

    private void connection() {
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
    }


    private void loadEntries() {
        try {
            transaction.begin();
            //ошибка может быть в маленькой букве
            Query query = entityManager.createQuery("FROM Entry");
            list = query.getResultList();
            transaction.commit();
        } catch (RuntimeException exception) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        }

    }


    public void addEntry() {
        try {
            transaction.begin();
            entry.doVerdict();
            entityManager.persist(entry);
            list.add(entry);
            entry = new Entry();
            transaction.commit();
        } catch (RuntimeException exception) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        }
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }


    public List<Entry> getList() {
        return list;
    }

    public void setList(List<Entry> list) {
        this.list = list;
    }

    public void addEntryWithParameters(){
        if(entry==null) entry=new Entry();
        try {
            Map<String, String> paramMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            transaction.begin();
            entry.setX(Integer.parseInt(paramMap.get("x")));
            entry.setY(Double.parseDouble(paramMap.get("y")));
            entry.setR(Double.parseDouble(paramMap.get("r")));
            entry.doVerdict();
            entityManager.persist(entry);
            list.add(entry);
            PrimeFaces.current().ajax().addCallbackParam("verdict", entry.getVerdict());
            entry = new Entry();
            transaction.commit();
        } catch (RuntimeException exception) {
            System.out.println("error:" + exception.getMessage());
            if (transaction.isActive()){
                transaction.rollback();
            }
            throw exception;
        }
    }

    public void sendVerdict(){
        entry.doVerdict();
        PrimeFaces.current().ajax().addCallbackParam("verdict", entry.getVerdict());
    }


}
