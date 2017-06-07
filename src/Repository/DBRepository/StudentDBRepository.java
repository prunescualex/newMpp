package Repository.DBRepository;


import Domain.Student;
import Domain.Validators.Validator;
import Repository.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
//Initialize function that populates database

public class StudentDBRepository extends Repository<Long, Student> {
    private String url;
    private Connection connection;


    public StudentDBRepository(Validator<Student> validator,String url,String fileName){
        super(validator);
        //super(validator);
        this.url = url;
        System.out.println(url);

        //empty DB
        //ONLY CONNECTION
        try {
             connection = DriverManager.getConnection(this.url);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        //Start of populating DB
        //PreparedStatement queryI=

        Path path = Paths.get(fileName);
        System.out.println(fileName);
        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0));

                String serialNumber = items.get(1);
                String firstName = items.get((2));
                String lastName = items.get(3);
                int group = Integer.parseInt(items.get(4));

                Student student = new Student(firstName, lastName, serialNumber, group);
                student.setId(id);

                try {
                    List<Student> students=new ArrayList<Student>();
                    students=this.getAll();
                    students.forEach(stud->super.add(stud));
                   /*
                    if(students.contains(student)!=true)
                    {
                        this.add(student);
                    }*/

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //End of populating DB

        //Populate local Repositoruy
        //List<Student> studs=this.getAll();

    }



    @Override
    public Student get(Long aLong) {
        try(
                //, username, password

             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM student WHERE id = ?" )){
            stmt.setLong(1, aLong);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            Long id = rs.getLong("id");
            Student stud = new Student(rs.getString("firstname"), rs.getString("lastname"), rs.getString("nrmatricol"), rs.getInt("groupno"));
            stud.setId(id);

            stmt.close();
            connection.close();
            return stud;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> getAll() {

        try(
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM student" )){
            ResultSet rs = stmt.executeQuery();
            //System.out.println(rs.getString("firstname"));
            List<Student> stud = new ArrayList<>();

            while (rs.next()){
                Long id = rs.getLong("id");
                Student s = new Student(rs.getString("firstname"), rs.getString("lastname"), rs.getString("nrmatricol"), rs.getInt("groupno"));
                s.setId(id);
                //System.out.println(s.toString()); Display 2 x Students
                stud.add(s);
            }
            rs.close();
            stmt.close();
            //connection.close();
            return stud;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Long aLong) {
        try(
             PreparedStatement stmt = connection.prepareStatement("DELETE FROM student WHERE id = ?")){
            stmt.setLong(1, aLong);
            stmt.executeUpdate();
            stmt.close();
           // connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void add(Student entity) {
        /*
        String sql="INSERT INTO student (id, firstname, lastname, nrmatricol, groupno) "+
                "VALUES (1,'test','test','test',10)";*/
        //TEST INSERTION DATA
        //_____ IF EXISTS UPDATE ELSE INSERT
        String id=entity.getId().toString();
        String firstaneme=entity.getFirstName();
        String lastname=entity.getLastName();
        String nrmatricol=entity.getNrMatricol();
        String groupno=Integer.toString(entity.getGroup());

        //___ CHECK IF EXISTS

        List<Student> students=new ArrayList<Student>();
        students=this.getAll();

        //students.forEach(line->System.out.println(line.toString()));

        if(students.contains(entity)!=true)
        {
            try {
                PreparedStatement sql1= connection.prepareStatement("INSERT INTO student (id, firstname, lastname, nrmatricol, groupno) "+
                        "VALUES (?,?,?,?,?)");
                sql1.setString(1,id);
                sql1.setString(2,firstaneme);
                sql1.setString(3,lastname);
                sql1.setString(4,nrmatricol);
                sql1.setString(5,groupno);
                sql1.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        else
            this.update(entity.getId(),entity);
        /*
       */
       // this.update(entity.getId(),entity);

        //END OF TEST
        /*OLD FORM OF INSERTION
        try
        {
            connection.prepareStatement(sql).execute();
        }
        catch (SQLException e) {
        e.printStackTrace();
        }*///TO BE REPLACED


        /*
        try(connection = DriverManager.getConnection(url);//, username, password
             PreparedStatement stmt = connection.prepareStatement("INSERT INTO student (id, firstname, lastname, nrmatricol, groupno) VALUES (?, ?, ?, ?, ?)")){
            stmt.setLong(1, entity.getId());
            stmt.setString(2, entity.getFirstName());
            stmt.setString(3, entity.getLastName());
            stmt.setString(4, entity.getNrMatricol());
            stmt.setInt(5, entity.getGroup());
            stmt.executeUpdate();
            stmt.close();
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void update(Long aLong, Student entity) {
        try(
             PreparedStatement stmt = connection.prepareStatement("UPDATE student SET  firstname = ?, lastname = ?, nrmatricol = ?, groupno = ? WHERE id = ?" )){
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setString(3, entity.getNrMatricol());
            stmt.setInt(4, entity.getGroup());
            stmt.setLong(5, entity.getId());
            stmt.executeUpdate();
            stmt.close();
            //connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Set<Long> keys() {
        try(
             PreparedStatement stmt = connection.prepareStatement("SELECT id FROM student" )){
            ResultSet rs = stmt.executeQuery();
            Set<Long> keys = new HashSet<>();

            while (rs.next()){
                keys.add(rs.getLong("id"));
            }
            rs.close();
            stmt.close();
            //connection.close();
            return keys;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
