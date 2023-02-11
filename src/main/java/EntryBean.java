
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.annotation.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.io.Serializable;

@javax.faces.bean.ManagedBean(name = "entryBean")
@SessionScoped
public class EntryBean implements Serializable {
    private static final long serialVersionUID = 2L;
    //у меня ничего не поменялось, так как у каждого пользователя есть entrybean,
    // но они обращаются к одному и тому же entriesBean, у которого один и тот же entry
    //у меня в entryBean должна быть своя Entry, для каждого пользователя отедельная.
    //а соответственно лист будет общий для всех пользователей,
    // который будет отображать все одни и те же данные

    @ManagedProperty("#{entriesBean}")
    private EntriesBean entriesBean= new EntriesBean();

    private Entry entry;



    public EntriesBean getEntriesBean() {
        return entriesBean;
    }

    public void setEntriesBean(EntriesBean entriesBean) {
        this.entriesBean = entriesBean;
    }
}
