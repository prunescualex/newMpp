package Repository.DBRepository;

import Domain.Assignments;
import Domain.Validators.Validator;
import Repository.Repository;
import jdk.nashorn.internal.ir.Assignment;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by prunescu on 05/04/2017.
 */
public class AssignmentDBRepository extends Repository<Long, Assignments> {

    private String url;
    private Connection connection;

    public AssignmentDBRepository(Validator<Assignments> validator, String url, String fileName) {
        super(validator);
        this.url = url;

        try {
            connection = DriverManager.getConnection(url);//, username, password
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Start of populating DB
        Path path = Paths.get(fileName);
        //System.out.println(fileName);

        try {
            Files.lines(path).forEach(line -> {
                        List<String> items = Arrays.asList(line.split(","));


                        Long idStudent = Long.valueOf(items.get(0));
                        Long idProblem = Long.valueOf(items.get(1));
                        int grade = Integer.parseInt(items.get(2));

                        Assignments assign = new Assignments(idStudent, idProblem, grade);
                    }
            );

            try {
                List<Assignments> assignments = new ArrayList<Assignments>();
                assignments = this.getAll();
                assignments.forEach(assign -> super.add(assign));//add to in-memory repo

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Assignments get(Long idStudent) {
        try {
            PreparedStatement sql = connection.prepareStatement("Select * from Assignment WHERE studentID=?");
            sql.setLong(1, idStudent);
            ResultSet rs = sql.executeQuery();
            rs.next();

            Long id = rs.getLong("studentID");
            Long idProb = rs.getLong("problemID");
            int grade = rs.getInt("grade");
            Assignments assign = new Assignments(id, idProb, grade);

            sql.close();
            return assign;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public List<Assignments> getAll() {
        try {
            PreparedStatement sql = connection.prepareStatement("Select * from Assignment");
            ResultSet rs = sql.executeQuery();

            List<Assignments> assigns = new ArrayList<Assignments>();
            while (rs.next()) {
                Long id = rs.getLong("studentID");
                Long idProb = rs.getLong("problemID");
                int grade = rs.getInt("grade");

                Assignments a = new Assignments(id, idProb, grade);
                assigns.add(a);
            }
            rs.close();
            sql.close();
            return assigns;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
    @Override
    public void add(Assignment entity) {

        List<Assignments> assigns=new ArrayList<Assignments>();
        assigns=this.getAll();

        if(assigns.contains(entity)!=true)
        {
            try
            {
                PreparedStatement sql=connection.prepareStatement("Insert into Assignment(studentID,problemID,grade)");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }
    }*/
}
