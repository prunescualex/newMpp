package Repository.FileRepository;

import Domain.Assignments;
import Domain.Validators.Validator;
import Repository.Repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

/**
 * Created by prunescu on 28/04/2017.
 */
public class AssignmentFileRepository extends Repository<Long, Assignments> {
    private String fileName;

    public AssignmentFileRepository(Validator<Assignments> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0));
                Long studentId = Long.valueOf(items.get(1));
                Long problemId = Long.valueOf(items.get(2));
                Integer grade = Integer.valueOf(items.get(3));

                Assignments assignment = new Assignments(studentId, problemId, grade);
                assignment.setId(id);

                try {
                    super.add(assignment);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void add(Assignments entity) {
        super.add(entity);
        saveToFile(entity);
    }

    @Override
    public void delete(Long aLong) {
        super.delete(aLong);
        saveAllToFile();
    }

    @Override
    public void update(Long aLong, Assignments entity) {
        super.update(aLong, entity);
        saveAllToFile();
    }

    private void saveToFile(Assignments entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getStudentId() + "," + entity.getProblemId() + "," + entity.getGrade());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveAllToFile() {
//        Path path = Paths.get(fileName);
        File file = new File(fileName);
        List<Assignments> entities = this.getAll();


        try {
            FileWriter fileWriter = new FileWriter(file, false);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            entities.forEach(entity -> {
                try {
                    bufferedWriter.newLine();
                    bufferedWriter.write(entity.getId() + "," + entity.getStudentId() + "," + entity.getProblemId() + "," + entity.getGrade());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

