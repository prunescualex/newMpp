package Domain;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


/**
 * Created by Prunescu on 10/03/2017.
 */



public class Assignments extends BaseEntity<Long> {
    private Long studentId;
    private Long problemId;
    private Integer grade;

    public Assignments(Long sId, Long pId, Integer grade){
        this.studentId = sId;
        this.problemId = pId;
        this.grade = grade;
    }


    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String toString(){
        return "Id: " + this.getId() + " Student ID: " + studentId + " Problem Assigned: " + problemId + " Grade: " + grade;
    }


}
