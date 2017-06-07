package Repository.DBRepository;
import Domain.*;
import Domain.Validators.Validator;
import Repository.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;


/**
 * Created by prunescu on 27/03/2017.
 */
public class ProblemDBRepository extends Repository<Long, Problem> {

    private String url;
    private Connection connection;

    public ProblemDBRepository(Validator<Problem> validator, String url, String fileName) {
        super(validator);
        this.url = url;
        //System.out.println(url);


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


                Long id = Long.valueOf(items.get(0));
                int number = Integer.parseInt(items.get(1));
                String assignment = items.get(2);


                Problem problem = new Problem(number, assignment);
                problem.setId(id);//
                /*
                try {
                    this.add(problem);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                //new version without conflicts
                try {
                    List<Problem> problems = new ArrayList<Problem>();
                    problems = this.getAll();
                    problems.forEach(prob -> super.add(prob));//add problem to memory repository;

                    /*if (problems.contains(problem) != true) {
                        this.add(problem);//if not in list insert in DB
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                }


            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //End of populating DB


    }

    //Get wanted Problem
    @Override
    public Problem get(Long aLong) {
        try (

                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM problem WHERE id = ?")) {
            stmt.setLong(1, aLong);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            Long id = rs.getLong("id");


            Problem prob = new Problem(Integer.parseInt(rs.getString("number")), rs.getString("assignment"));

            /*
            Student stud = new Student(rs.getString("firstname"), rs.getString("lastname"), rs.getString("nrmatricol"), rs.getInt("groupno"));
            stud.setId(id);*/

            stmt.close();
            //connection.close();
            return prob;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Problem> getAll() {

        try (
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM problem")) {
            ResultSet rs = stmt.executeQuery();
            //System.out.println(rs.getString("firstname")); USED FOR TESTING
            List<Problem> problems = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("number");

                Problem p = new Problem(id, rs.getString("assignments"));
                problems.add(p);
            }
            rs.close();
            stmt.close();
            //connection.close();
            return problems;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Long aLong) {
        try (
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM problem WHERE id = ?")) {
            stmt.setLong(1, aLong);
            stmt.executeUpdate();
            stmt.close();
            //connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Problem entity) {

        String number = entity.getNumber().toString();//Change to id,number,ass
        String id = entity.getId().toString();
        String assignment = entity.getAssignment();
            /*

            System.out.println(id);
            System.out.println(assignment);

          */

        List<Problem> problems = new ArrayList<Problem>();
        problems = this.getAll();
        if (problems.contains(entity) != true) {
            try {
                PreparedStatement sql1 = connection.prepareStatement("INSERT INTO problem (id,number,assignments) " +
                        "VALUES (?,?,?)");
                sql1.setString(1, id);
                sql1.setString(2, number);
                sql1.setString(3, assignment);
                sql1.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            this.update(entity.getId(), entity);

    }

    @Override
    public void update(Long id,Problem entity)
    {
        try(PreparedStatement stmt = connection.prepareStatement("UPDATE problem SET  number = ?, assignments = ? WHERE id = ?" )){
            stmt.setInt(1,entity.getNumber());
            stmt.setString(2,entity.getAssignment());
            stmt.setLong(3,entity.getId());
            stmt.executeUpdate();
            stmt.close();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Set<Long> keys() {
        try(
             PreparedStatement stmt = connection.prepareStatement("SELECT id FROM problem" )){
            ResultSet rs = stmt.executeQuery();
            Set<Long> keys = new HashSet<>();

            while (rs.next()){
                keys.add(rs.getLong("id"));
            }
            rs.close();
            stmt.close();
            connection.close();
            return keys;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
