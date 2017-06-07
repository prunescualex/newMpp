

import Controller.StudentController;
import Domain.BaseEntity;
import Domain.Problem;
import Domain.Student;
import java.lang.Exception;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Features:
 * F1-4: CRUD for Student entity: Options 1 -> 4
 * F5-8: CRUD for Problem entity: Options 5 -> 8
 * F9:   Assign grades
 * F10:  Filter students by their group
 * F11:  Filter students based on a assigned problem
 * F12:  Report: The problem that was assigned the most
 */
public class UI {
    private StudentController ctrl;

    public UI(StudentController ctrl){
        this.ctrl = ctrl;
    }

    private String mainMenu = "\nMAIN MENU\n" +
            "CRUD students\n" +
            "1.  Add student\n" +
            "2.  List all students\n" +
            "3.  Update student\n" +
            "4.  Delete student\n" +
            "CRUD assignments\n" +
            "5.  Assign a problem to a student\n" +
            "6.  List assignments for a student\n" +
            "7.  Assign a grade\n" +
            "8.  Remove an assignment\n" +
            "CRUD Problems\n" +
            "9.  Delete a problem\n" +
            "10. Update a problem\n" +
            "11. Add a problem\n" +
            "12. List all problems\n" +
            "Additional features\n" +
            "13. Filter stud. by their group\n" +
            "14. Filter stud. by an assigned problem\n" +
            "15. Which problem was assigned the most?\n" +
            "0. Exit";

    public void mainMenu() {
        int option;
        Scanner in = new Scanner(System.in);
        System.out.println(mainMenu);

        try {
            option = in.nextInt();
            switch (option) {
                case 1: {
                    try {
                        addStudent();
                        mainMenu();
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case 2: {
                    printAllStudents();
                    mainMenu();
                    break;
                }
                case 3: {
                    updateStudent();
                    mainMenu();
                    break;
                }
                case 4: {
                    removeStudent();
                    mainMenu();
                    break;
                }
                case 5: {
                    assignProblem();
                    mainMenu();
                    break;
                }
                case 6: {
                    printAssignments();
                    mainMenu();
                    break;
                }
                case 7: {
                    assignGrade();
                    mainMenu();
                    break;
                }
                case 8: {
                    removeAssignment();
                    mainMenu();
                    break;
                }
                case 9: {
                    removeProblem();
                    mainMenu();
                    break;
                }
                case 10: {
                    updateProblem();
                    mainMenu();
                    break;
                }
                case 11: {
                    addProblem();
                    mainMenu();
                    break;
                }
                case 12: {
                    listProblems();
                    mainMenu();
                    break;
                }
                case 13: {
                    filterByGroup();
                    mainMenu();
                    break;
                }
                case 14: {
                    filterByProblem();
                    mainMenu();
                    break;
                }
                case 15: {
                    assignedMost();
                    mainMenu();
                    break;
                }
                case 0: {
                    break;
                }

                default:
                    System.out.println("invalid option !");
            }

        } catch (java.lang.Exception e){
            e.printStackTrace();
            System.out.println("Something went wrong");
        }
    }

    private void removeAssignment() {
        System.out.println("Assignment id:");
        Scanner in = new Scanner(System.in);
        Long id = in.nextLong();

        this.ctrl.removeAssignment(id);
    }

    private void listProblems() {
        List<Problem> problem = ctrl.getAllProblems();
        if(problem == null) {
            System.out.println("No problems to show!");
            return;
        }
        problem.stream().forEach(p -> System.out.println(p.getId() + " :: " + p.toString()));

    }

    private void addProblem() {
        Problem p = readProblem();
        this.ctrl.addProblem(p);

    }

    private Student readStudent() {
        System.out.println("Read student: attributes in the following order: id, serialNumber, group, first name, last name");
        Scanner in = new Scanner(System.in);

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());// ...
            String serialNumber = bufferRead.readLine();
            int group = in.nextInt();
            String firstName = bufferRead.readLine();
            String lastName = bufferRead.readLine();

            Student student = new Student(firstName, lastName, serialNumber, group);
            student.setId(id);

            return student;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Problem readProblem(){
        System.out.println("Read problem (id, description,number)");
        Scanner in = new Scanner(System.in);

        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        try{
            Long id = Long.valueOf(bufferReader.readLine());

            String description = bufferReader.readLine();

            int nr = in.nextInt();
            Problem problem = new Problem(nr, description);
            problem.setId(id);
            return problem;
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public void assignProblem(){
        System.out.println("Student id:");
        Scanner in = new Scanner(System.in);
        Long sId = in.nextLong();

        System.out.println("Problem id:");
        Long pId = in.nextLong();
        try{
            ctrl.assignProblem(sId, pId);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void assignGrade() {
        Scanner in = new Scanner(System.in);
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));

        try{
            System.out.println("Student id:");
            Long sId = in.nextLong();

            System.out.println("Problem id:");
            Long pId = in.nextLong();

            System.out.println("Grade:");
            Integer grade = in.nextInt();
            ctrl.assignGrade(sId, pId, grade);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void addStudent() throws Exception {

        Student student = readStudent();
        if (student == null || student.getId() < 0) {
            System.out.println("Invalid student");
        }
        try {
            ctrl.addStudent(student.getId(), student);
        } catch (IllegalArgumentException e) {
            System.out.println("Student ID already taken.");
        }

    }

    public void printAssignments(){
        System.out.println("Student id:");
        Scanner in = new Scanner(System.in);
        try {
            Long sId = in.nextLong();
            this.ctrl.getAllStudents().stream().filter(s -> s.getId() == sId).forEach(System.out::println);
            this.ctrl.getAssignments(sId).forEach(System.out::println);
        }catch (java.lang.Exception e){
            e.printStackTrace();
        }
    }

    public void printAllStudents(){
        List<Student> stud = ctrl.getAllStudents();
        if(stud == null) {
            System.out.println("No students to show!");
            return;
        }
        stud.stream().forEach(p -> System.out.println(p.getId() + " :: " + p.toString()));
    }

    public void updateStudent(){
        Student student = readStudent();
        if (student == null || student.getId() < 0) {
            System.out.println("Invalid student");
        }
        try {
            ctrl.updateStudent(student.getId(), student);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProblem(){
        Problem problem = readProblem();
        System.out.println("Problem ID");
        Scanner in = new Scanner(System.in);
        try{
            Long pId = in.nextLong();
            ctrl.updateProblem(pId, problem);
        } catch (IllegalArgumentException e){
            System.out.println("Invalid problem ID!");
        }
    }


    public void removeStudent(){
        System.out.println("Student ID:");
        Scanner in = new Scanner(System.in);
        try{
            Long id = in.nextLong();
            ctrl.removeStudent(id);
        } catch (IllegalArgumentException e){
            System.out.println("Invalid ID!");
        }
    }

    public void removeProblem(){
        Scanner in = new Scanner(System.in);
        try{
            System.out.println("Problem Id:");
            Long pId = in.nextLong();
            ctrl.removeProblem(pId);
        } catch (IllegalArgumentException e){
            System.out.println("Invalid serial number!");
        }
    }

    public void filterByGroup(){
        System.out.println("Group number:");
        Scanner in = new Scanner(System.in);
        int group = in.nextInt();
        List<Student> filtered = ctrl.filterByGroup(group);
        filtered.forEach(p -> System.out.println(p.getId() + " :: " + p.toString()));
    }

    public void filterByProblem(){
        System.out.println("Problem ID:");
        Long pId = new Scanner(System.in).nextLong();
        List<Student> filtered = ctrl.filterByProblem(pId);
        filtered.forEach(p -> System.out.println(p.getId() + " :: " + p.toString()));
    }


    public void assignedMost(){
        System.out.println(ctrl.assignedMost().toString());
    }

    public void run(){
        mainMenu();
    }




}
