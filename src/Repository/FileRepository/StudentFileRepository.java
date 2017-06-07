package Repository.FileRepository;

import javafx.util.Pair;
import Domain.Student;
import Domain.Validators.Validator;
import Repository.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;


public class StudentFileRepository extends Repository<Long, Student> {
    private String fileName;

    public StudentFileRepository(Validator<Student> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        loadData();
    }

    private void loadData() {
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
                    super.add(student);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void add(Student entity) {
        super.add(entity);
        saveToFile(entity);
    }

    @Override
    public void delete(Long aLong) {
        super.delete(aLong);
        saveAllToFile();
    }

    @Override
    public void update(Long aLong, Student entity) {
        super.update(aLong, entity);
    }

    private void saveToFile(Student entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getNrMatricol() + "," + entity.getFirstName() + ","  + entity.getLastName() + ","  + entity.getGroup());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveAllToFile(){
        List<Student> stud = this.getAll();
        stud.stream().forEach(e -> saveToFile(e));
    }
}
