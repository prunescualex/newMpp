package Domain;


/**
 * Created by Prunescu on 10/03/2017.
 */
/*
    Clas that keeps reference of Students,Problems and assignments
 */
public class BaseEntity<ID> {
    private ID id;

    public ID getId(){
        return this.id;
    }

    public void setId(ID id){
        this.id=id;
    }

    @Override
    public String toString(){
        return "BaseEntity{" + "id= " + id + "} \n" ;
    }
}
