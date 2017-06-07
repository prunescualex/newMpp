/*import Domain.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import java.sql.DriverManager;


        public class Main {
            public static void main(String args[]) {
                Connection c = null;
                try {
                   // Class.forName("org.sqlite.JDBC");
                    Class.forName("org.sqlite.JDBC");
                    c = DriverManager
                            .getConnection("jdbc:sqlite:E:/MPP2017/database/database");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println(e.getClass().getName()+": "+e.getMessage());
                    System.exit(0);
                }
                System.out.println("Opened database successfully");

                String s="SELECT * FROM table_name";


                String sql = "INSERT INTO table_name " +
                        "VALUES ('test')";

                try {
                    c.prepareStatement(sql).execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                try {
                    System.out.print(c.prepareStatement(s).execute());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
*/



import Controller.StudentController;
import Domain.Assignments;
import Domain.Problem;
import Domain.Student;
import Domain.Validators.AssignmentValidator;
import Domain.Validators.ProblemValidator;
import Domain.Validators.StudentValidator;
import Domain.Validators.Validator;
import Repository.*;
import Repository.FileRepository.*;
import Repository.DBRepository.*;
//import Repository.DBRepositories.StudentDBRepository;
//import Repository.FileRepositories.AssignmentFileRepository;
//import Repository.FileRepositories.ProblemFileRepository;
//import Repository.FileRepositories.StudentFileRepository;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        String dbURL = "jdbc:sqlite:E:/MPP2017/database/database";
        //String user = "dutzi";
        //String pass = "dutzana";

        //Testing Databaase connection
        Connection c = null;
        try {
            // Class.forName("org.sqlite.JDBC");
            Class.forName("org.sqlite.JDBC");
            c = DriverManager
                    .getConnection("jdbc:sqlite:E:/MPP2017/database/database");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        //if passed Message is printed ..... OK

        Validator<Student> studentValidator = new StudentValidator();
        Validator<Problem> problemValidator = new ProblemValidator();
        Validator<Assignments> assignValidator = new AssignmentValidator();
// DB Repositories
        Repository<Long, Student> studRepo = new StudentDBRepository(studentValidator,dbURL,"E:\\MPP2017\\Lab3\\src\\students.txt");
        Repository<Long, Problem> probRepo = new ProblemDBRepository(problemValidator,dbURL,"E:\\MPP2017\\Lab3\\src\\problems.txt");
        Repository<Long, Assignments> assignRepo=new AssignmentDBRepository(assignValidator,dbURL,"E:\\MPP2017\\Lab3\\src\\assignments.txt");
// File Repositories
        //Repository<Long, Student> studRepo = new StudentFileRepository(studentValidator, "E:\\MPP2017\\Lab3\\src\\students.txt");
       // Repository<Long, Problem> probRepo = new ProblemFileRepository(problemValidator,"E:\\MPP2017\\Lab3\\src\\problems.txt");
       // Repository<Long, Assignments> assignRepo = new AssignmentFileRepository(assignValidator, "E:\\MPP2017\\Lab3\\src\\assignments.txt");

        StudentController ctrl = new StudentController(studRepo, probRepo, assignRepo);
        UI ui = new UI(ctrl);
        //TO DO -> DATABASE PROBLEMS + Assignments

        //______
        /*
        Repository<Long, Problem> probRepo= new ProblemDBRepository(..);
        Repository<Long, Assignment> assignRepo=new AssignDBRepository(..);
        StudentController ctrl=new StudentController(studRepo,probRepo,assignRepo);
        UI ui - new UI(ctrl);
        */
        //______


        ui.run();
    }
}

