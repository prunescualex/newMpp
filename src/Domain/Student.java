package Domain;

/**
 * Created by prunescu on 21/03/2017.
 */
public class Student extends BaseEntity<Long> {
    private String firstName;
    private String lastName;
    private String nrMatricol;
    private int group;


    public Student(String firstName, String lastName, String nrMatricol, int group){
        this.firstName = firstName;
        this.lastName = lastName;
        this.nrMatricol = nrMatricol;
        this.group = group;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNrMatricol() {
        return nrMatricol;
    }

    public void setNrMatricol(String nrMatricol) {
        this.nrMatricol = nrMatricol;
    }

    public String toString(){
        return "NrMatricol " + nrMatricol + " :: " + firstName + " " + lastName + " :: Group " + group;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
}