package Domain;

/**
 * Created by Prunescu on 10/03/2017.
 */
public class Problem extends BaseEntity<Long>{
    private Integer number;
    private String assignment;

    public Problem(Integer number, String assignment){
        this.number = number;
        this.assignment = assignment;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public String toString(){
        return "Problem: " + number + " " + assignment;
    }
}
