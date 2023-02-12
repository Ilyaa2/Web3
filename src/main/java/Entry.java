import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "entry")
public class Entry implements Serializable {
    private static final long serialVersionUID = 4L;

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "jpaSequence", sequenceName = "JPA_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpaSequence")
    public int id;

    private int x;

    private double y;

    private double r;

    private String verdict;

    public String getVerdict() {
        return verdict;
    }

    public void setVerdict(String verdict) {
        this.verdict = verdict;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }


    public String processVerdict(int x, double y, double r){
        if (x >= 0 && y >= 0) {
            if (x <= r && y <= r) {
                return "INCLUDED";
            }
            return "NOT_INCLUDED";
        }
        if (x >= 0 && y <= 0) {
            if (x * x + y * y <= r * r) {
                return "INCLUDED";
            }
            return "NOT_INCLUDED";
        }
        if (x<=0 && y<=0) {
            if (y >= -0.5 * x - r/2) {
                return "INCLUDED";
            }
        }
        return "NOT_INCLUDED";
    }


    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "verdict=" + verdict +
                ", x=" + x +
                ", y=" + y +
                ", r=" + r +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
