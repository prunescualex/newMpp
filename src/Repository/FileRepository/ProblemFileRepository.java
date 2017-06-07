package Repository.FileRepository;

import Domain.Problem;
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

/**
 * Created by prunescu on 28/04/2017.
 */
public class ProblemFileRepository extends Repository<Long, Problem> {
    private String fileName;

    public ProblemFileRepository(Validator<Problem> validator, String fileName) {
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
                Integer number = Integer.valueOf(items.get(1));
                String statement = items.get((2));

                Problem student = new Problem(number, statement);
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
    public void add(Problem entity) {
        super.add(entity);
        saveToFile(entity);
    }

    private void saveToFile(Problem entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getNumber() + "," + entity.getAssignment());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
