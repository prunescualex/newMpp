package Controller;

import Domain.Assignments;
import Domain.Problem;
import Domain.Student;
import Repository.Repository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Prunescu on 10/03/2017.
 */
public class StudentController {
    private Repository<Long, Student> repoStudent;
    private Repository<Long, Problem> repoProblem;
    private Repository<Long, Assignments> repoAssign;

    public StudentController(Repository<Long, Student> repository, Repository<Long, Problem> repo2, Repository<Long, Assignments> repo3){
        this.repoStudent = repository;
        this.repoProblem = repo2;
        this.repoAssign = repo3;
    }

    public void addStudent(Long id, Student s){
        if(repoStudent.keys().contains(id)){throw new IllegalArgumentException();}
        repoStudent.add(s);
    }

    public List<Student> getAllStudents(){
        List<Student> stud = repoStudent.getAll();
        return StreamSupport.stream(stud.spliterator(), false).collect(Collectors.toList());
    }

    public void assignProblem(Long sId, Long pId) throws ControllerException{
        try{
            Assignments assign = new Assignments(sId, pId, 0);
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy");
                Date dateFrom = dateFormat.parse("2012");
                long timestampFrom = dateFrom.getTime();
                Date dateTo = dateFormat.parse("2013");
                long timestampTo = dateTo.getTime();
                Random random = new Random();
                long timeRange = timestampTo - timestampFrom;
                long randomTimestamp = (timestampFrom + (long) (random.nextDouble() * timeRange)) % 1000000;
                assign.setId(randomTimestamp);
                this.repoAssign.add(assign);
            }catch (ParseException e){
                e.printStackTrace();
            }

        } catch (Exception ex)
        {
            throw new ControllerException();
        }
    }

    public void assignGrade(Long sId, Long pId, Integer grade) throws ControllerException{
        try{
            Iterator<Assignments> assignmentsIterator = this.repoAssign.getAll().stream().filter(p -> p.getStudentId() == sId && p.getProblemId() == pId).iterator();
            if(!assignmentsIterator.hasNext()){
                throw new NoSuchElementException("Student hasn't been assigned to this problem!");
            }
            else{
                Assignments a = assignmentsIterator.next();
                if(assignmentsIterator.hasNext()) {
                    throw new IllegalArgumentException("This problem has been assigned multiple times");
                }
                this.repoAssign.update(a.getId(), new Assignments(sId, pId, grade));
            }
        } catch (Exception ex){
            throw new ControllerException();
        }
    }


    public List<Assignments> getAssignments(Long sId){
        List<Assignments> studAssign = this.repoAssign.getAll().stream().filter(p -> p.getStudentId() == sId).collect(Collectors.toList());
        return studAssign;
    }

    public void removeStudent(Long id){
        this.repoStudent.delete(id);
        this.repoAssign.getAll().stream().filter(p -> p.getStudentId() == id).map(p -> p.getId()).forEach(e -> repoAssign.delete(e));
    }

    public void updateStudent(Long id, Student s){
        this.repoStudent.update(id, s);
    }

    public void updateAssignment(Long sId, Long pId, Assignments a){

    }

    public void updateProblem(Long id, Problem problem){
        this.repoProblem.update(id, problem);

    }

    public void removeProblem(Long pId){
        this.repoProblem.delete(pId);
        this.repoAssign.getAll().stream().filter(p -> p.getProblemId() == pId).map(p -> p.getId()).forEach(p -> repoAssign.delete(p));
    }

    public List<Student> filterByGroup(int group){
        List<Student> stud = repoStudent.getAll();
        List<Student> filteredStud = stud.stream()
                .filter(s -> s.getGroup() == group)
                .collect(Collectors.toList());
        return filteredStud;
    }

    public List<Student> filterByProblem(Long pId){
        List<Student> filtered = repoAssign.getAll().stream()
                .filter(a -> a.getProblemId() == pId)
                .map(s -> s.getStudentId())
                .map(s -> repoStudent.get(s))
                .collect(Collectors.toList());

        return filtered;
    }

    public Problem assignedMost(){
        List<Student> stud = repoStudent.getAll();
        List<Long> probList = repoAssign.getAll().stream().map(a -> a.getProblemId()).collect(Collectors.toList());
        Collections.sort(probList);
        Integer max = 0 , c = 1;
        Long a = probList.get(0), nr = Long.valueOf(0);

        for(int i = 1; i< probList.size(); i++){
            if(probList.get(i) == a){
                c++;
            }else{
                if (c > max){
                    max = c;
                    nr = a;
                    a = probList.get(i);
                    c = 1;
                }else{
                    a = probList.get(i);
                    c = 1;
                }
            }
            if (c > max){
                max = c;
                nr = a;
                a = probList.get(i);
                c = 1;
            }

        }
        return repoProblem.get(nr);
    }


    public void removeAssignment(Long id) {
        this.repoAssign.delete(id);
    }

    public List<Problem> getAllProblems() {
        List<Problem> problem = repoProblem.getAll();
        return StreamSupport.stream(problem.spliterator(), false).collect(Collectors.toList());

    }

    public void addProblem(Problem p) {
        //System.out.println(repoProblem.keys());
        if (repoProblem.sizeOfRepo() >1) {
            if (repoProblem.keys().contains(p.getId()) != true) {
                throw new IllegalArgumentException();
            }
        }
        repoProblem.add(p);

    }
}
